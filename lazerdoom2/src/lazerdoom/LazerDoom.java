package lazerdoom;

import java.io.IOException;

import javax.swing.JFrame;

import Control.ControlServer;
import Sequencer.ParallelSequenceContainer;
import Sequencer.Sequencer;
import Sequencer.HighResolutionPollingClock;
import Synth.SynthManager;
import de.sciss.jcollider.*;
import de.sciss.jcollider.gui.ServerPanel;

public class LazerDoom {
	private HighResolutionPollingClock clock;
	private Sequencer sequencer;
	private ParallelSequenceContainer mainSequenceContainer;
	private ControlServer controlServer;
	private SynthManager synthManager; 
	private Server superColliderServer;
	private JFrame serverPanel;
	
	private Process jackProcess;
	
	
	public static void main(String[] args) {
		/*{
			System.out.println("Started LazerDoom");
	          try {
	                UGenInfo.readBinaryDefinitions();
	                Server s = new Server( "default" );
	                s.start();
	               
	                Control ck = Control.kr( new String[] { "freq", "out" }, new float
	[] { 440f, 0f });
	                new SynthDef( "tutorial-args", UGen.ar( "Out", ck.getChannel
	( "out" ), UGen.ar( "*", UGen.ar( "SinOsc", ck.getChannel( "freq" )),  
	UGen.ir( 0.2f )))).send( s );
	                s.sync( 4f );

	                Group g = s.asTarget();
	                Synth x, y, z;
	                x = Synth.head( g, "tutorial-args" ); // no args, so default values
	                Thread.sleep( 1000 );
	                x.free();
	                
	                System.out.println("wah");
	                y = new Synth( "tutorial-args", new String[] { "freq" }, new float
	[] { 660 }, g ); // change freq
	                Thread.sleep( 1000 );
	                y.free();
	                z = new Synth( "tutorial-args", new String[] { "freq", "out" }, new  
	float[] { 880, 1 }, g );
	                Thread.sleep( 1000 );
	                z.free();
	                System.exit( 0 );
	          } catch( Exception e1 ) {
	        	  System.out.println("mehhhh");
	                e1.printStackTrace();
	                System.exit( 1 );
	          }
	        } 
		*/
		
		LazerDoom lazerdoom = new LazerDoom();
		System.out.println("Started LazerDoom");
		lazerdoom.createAndShowServerPanel();
			
	}
	
	public void createAndShowServerPanel() {
		serverPanel = ServerPanel.makeWindow( superColliderServer , ServerPanel.MIMIC | ServerPanel.CONSOLE | ServerPanel.DUMP );
		serverPanel.setVisible(true);
	}
	
	public LazerDoom() {
		// For the clock working as a jack-client
		System.setProperty("jjack.ports.input", "0");
		
		this.startJack();
		
		// Wait till jackd settled down
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		try {
			superColliderServer = new Server("localhost");
		} catch (IOException e) {
			System.err.println("Error creating SC3-Server:\n"+e.getMessage());
			e.printStackTrace();
		}
		
		mainSequenceContainer = new ParallelSequenceContainer();
		controlServer = new ControlServer(superColliderServer, 50);
		sequencer = new Sequencer(mainSequenceContainer, controlServer);
		clock = new HighResolutionPollingClock(100000, sequencer);
		
		this.startSupercolliderServer();
		synthManager = new SynthManager(this.superColliderServer);
		synthManager.init();
		clock.start();
		
	}
	
	private void startJack() {
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
	
	private void startSupercolliderServer() {
		try {
			superColliderServer.start();
			superColliderServer.startAliveThread();
			superColliderServer.boot();
			superColliderServer.sync(4f);
		} catch (IOException e) {
			System.err.println("Error starting SC3-Server:\n"+e.getMessage());
			e.printStackTrace();
		}
	}
}
