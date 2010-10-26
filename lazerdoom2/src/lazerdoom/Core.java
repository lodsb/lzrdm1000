package lazerdoom;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Random;

import javax.swing.JFrame;

import message.Intercom;

import sequencer.EventPointsSequence;
import sequencer.HighResolutionLinuxClock;
import sequencer.HighResolutionPollingClock;
import sequencer.SequenceContainerInterface;
import sequencer.SequenceController;
import sequencer.SequencePlayer;
import sequencer.Sequencer;
import sequencer.graph.SequenceGraph;
import synth.SynthController;
import synth.SynthInfo;
import synth.SynthInstance;
import synth.event.SynthEventListenerInterface;
import synth.event.SynthEventServer;

import com.illposed.osc.OSCMessage;

import control.ControlServer;
import control.types.DoubleType;

import de.sciss.jcollider.OSCResponderNode;
import de.sciss.jcollider.Server;
import de.sciss.jcollider.ServerEvent;
import de.sciss.jcollider.ServerListener;
import de.sciss.jcollider.Synth;
import de.sciss.jcollider.gui.ServerPanel;
import de.sciss.net.OSCListener;
import de.sciss.net.OSCPacket;
import de.sciss.net.OSCPacketCodec;
import de.sciss.net.OSCServer;

public class Core {
	private HighResolutionLinuxClock clock;
	private Sequencer sequencer;
	private SequenceController sequenceController;
	private ControlServer controlServer;
	private SynthController synthController; 
	private Server superColliderServer;

	
	private double stdTempo = 120.0;
	private JFrame serverPanel;
	private SequenceGraph sequenceGraph;
	
	
	//private Intercom intercom = new Intercom();
	
	private static Core instance;
	public static Core getInstance() {
		return instance;
	}
	
	public void terminate() {
		clock.stopClock();
	}
	
	public Core() {
		
		//this.startJack();
		
		// Wait till jackd settled down
		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}*/
		
		try {
			superColliderServer = new Server("localhost");
			//superColliderServer.start();
		} catch (IOException e) {
			System.err.println("Error creating SC3-Server:\n"+e.getMessage());
			e.printStackTrace();
		}
		
		controlServer = new ControlServer(superColliderServer, 1000);
		sequencer = new Sequencer(controlServer);
		
		this.sequenceGraph = new SequenceGraph();
		this.sequenceController = new SequenceController(sequencer, sequenceGraph);
		
		clock = new HighResolutionLinuxClock(sequencer);
		clock.setInterval(Sequencer.bpmToPPQNanos(this.stdTempo));
		
		this.startSupercolliderServer();
		synthController = new SynthController(this.superColliderServer, this.controlServer);
		synthController.init();
		
		
		Core.instance = this;
		
		//this.createAndShowServerPanel();
	}

	public SequenceController getSequenceController() {
		return this.sequenceController;
	}
	
	public long beatMeasureToPPQ(int beat, int measure) {
		long PPQbar = this.sequencer.PPQ*4*4;
		return (PPQbar*beat)/measure;
	}
	
	public long oneBarInPPQ() {
		return beatMeasureToPPQ(1,1);
	}
	
	public SynthController getSynthController() {
		return this.synthController;
	}
	
	public Server getScServer() {
		return this.superColliderServer;
	}
	
	public void start() {
		clock.start();
	} 
	
	public void quit() {
		try {
			superColliderServer.quit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createAndShowServerPanel() {
		serverPanel = ServerPanel.makeWindow( superColliderServer , ServerPanel.MIMIC | ServerPanel.CONSOLE | ServerPanel.DUMP );
		serverPanel.setVisible(true);
	}
	
/*	private void startJack() {
		ProcessBuilder pb = new ProcessBuilder("jackd", "-d", "dummy");
		pb.redirectErrorStream(true);
		try {
			jackProcess = pb.start();
			System.out.println("Started JACK");
		} catch (IOException e) {
			System.err.println("Error starting JACK");
			e.printStackTrace();
		}
	}
	*/
	
	private OSCResponderNode responder;
	
	private void startSupercolliderServer() {
		try {
			superColliderServer.start();
			superColliderServer.startAliveThread();
			superColliderServer.boot();
			superColliderServer.sync(4f);
			
			responder = new OSCResponderNode(superColliderServer, "/tr", SynthEventServer.getInstance());
			responder.add();
			
			System.err.println("OSCResponder.isListening() "+responder.isListening());
			
			
			superColliderServer.notify(true);
		} catch (IOException e) {
			System.err.println("Error starting SC3-Server:\n"+e.getMessage());
			e.printStackTrace();
		}
	}
}
