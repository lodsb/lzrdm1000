package lazerdoom;

import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;

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

import com.illposed.osc.OSCMessage;

import control.ControlServer;
import control.types.DoubleType;

import de.sciss.jcollider.Server;
import de.sciss.jcollider.Synth;
import de.sciss.jcollider.gui.ServerPanel;

public class Core {
	private HighResolutionLinuxClock clock;
	private Sequencer sequencer;
	private SequenceController sequenceController;
	private ControlServer controlServer;
	private SynthController synthController; 
	private Server superColliderServer;
	private Process jackProcess; 
	
	private double stdTempo = 120.0;
	private JFrame serverPanel;
	private SequenceGraph sequenceGraph;
	
	private static Core instance;
	public static Core getInstance() {
		return instance;
	}
	
	public static void main(String[] args) {
		Core core = new Core();
		core.start();
		//while(true);
		
		//System.out.println(core.getSynthManager().getAvailableSynths());
		//Synth synth = core.getSynthManager().createSynthInstance(core.getSynthManager().getAvailableSynths().get(0));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*if(LazerdoomConfiguration.getInstance().showSupercolliderConsole) {
			core.createAndShowServerPanel();
		}*/
		
		//for(int i = 0; i < 1000; i++) {
			//System.out.println(core.getSynthManager().createSynthInstance(core.getSynthManager().getAvailableSynths().get(0)));
		//}
		Random random = new Random();
		
		SequencePlayer sp = core.getSequenceController().createSequencePlayer();
		EventPointsSequence<DoubleType> e1 = core.getSequenceController().createDoubleTypeEventPointsSequence();
		EventPointsSequence<DoubleType> e2 = core.getSequenceController().createDoubleTypeEventPointsSequence();
		EventPointsSequence<DoubleType> e3 = core.getSequenceController().createDoubleTypeEventPointsSequence();
		EventPointsSequence<DoubleType> e4 = core.getSequenceController().createDoubleTypeEventPointsSequence();
		
		core.getSequenceController().connectSequences(sp, e1);
		core.getSequenceController().connectSequences(e1, e2);
		core.getSequenceController().connectSequences(sp, e3);
		core.getSequenceController().connectSequences(e3, e4);
		
		SynthInstance si = core.getSynthController().createSynthInstance((core.getSynthController().getAvailableSynths().get(0)));
		SynthInstance si2 = core.getSynthController().createSynthInstance((core.getSynthController().getAvailableSynths().get(1)));
		
		
		
		int j = 0;
		while(j < 12) {
			try {
				/*de.sciss.net.OSCMessage msg = synth.setMsg(core.getSynthManager().getAvailableSynths().get(0).getControlParameters()[0].getName(), 22000*random.nextFloat());
				System.out.println(msg.getName()+" "+msg.getArgCount()+" "+msg.getArg(0)+" "+msg.getArg(1)+" "+msg.getArg(2));
				core.getScServer().sendMsg(msg);
				*/
				//Thread.sleep(100);
				
				e1.insert(new DoubleType(1000+5000*random.nextDouble()), j*Sequencer.PPQ*4);
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			j++;
		}
		
		j = 0;
		while(j < 12) {
			try {
				/*de.sciss.net.OSCMessage msg = synth.setMsg(core.getSynthManager().getAvailableSynths().get(0).getControlParameters()[0].getName(), 22000*random.nextFloat());
				System.out.println(msg.getName()+" "+msg.getArgCount()+" "+msg.getArg(0)+" "+msg.getArg(1)+" "+msg.getArg(2));
				core.getScServer().sendMsg(msg);
				*/
				//Thread.sleep(100);
				
				e2.insert(new DoubleType(440*random.nextDouble()), j*Sequencer.PPQ*10);
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			j++;
		}
		
		j = 0;
		while(j < 12) {
			try {
				/*de.sciss.net.OSCMessage msg = synth.setMsg(core.getSynthManager().getAvailableSynths().get(0).getControlParameters()[0].getName(), 22000*random.nextFloat());
				System.out.println(msg.getName()+" "+msg.getArgCount()+" "+msg.getArg(0)+" "+msg.getArg(1)+" "+msg.getArg(2));
				core.getScServer().sendMsg(msg);
				*/
				//Thread.sleep(100);
				
				e3.insert(new DoubleType(880+220*random.nextDouble()), j*Sequencer.PPQ*3);
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			j++;
		}
		
		j = 0;
		while(j < 12) {
			try {
				/*de.sciss.net.OSCMessage msg = synth.setMsg(core.getSynthManager().getAvailableSynths().get(0).getControlParameters()[0].getName(), 22000*random.nextFloat());
				System.out.println(msg.getName()+" "+msg.getArgCount()+" "+msg.getArg(0)+" "+msg.getArg(1)+" "+msg.getArg(2));
				core.getScServer().sendMsg(msg);
				*/
				//Thread.sleep(100);
				
				e4.insert(new DoubleType(1000+880*random.nextDouble()), j*Sequencer.PPQ*4);
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			j++;
		}
		
		//core.start();
		System.out.println(core.sequenceController);
		
		e1.addControlBus(si.getControlBusses()[0]);
		e1.setLength(2000);
		
		e2.addControlBus(si.getControlBusses()[0]);
		e2.setLength(2000);

		e3.addControlBus(si2.getControlBusses()[0]);
		e3.setLength(2000);

		e4.addControlBus(si2.getControlBusses()[0]);
		e4.setLength(2000);

		
		sp.startSequenceImmidiately();
		//System.out.println(core.sequenceController);
		core.start();
		
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
			superColliderServer.start();
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
	
	public void createAndShowServerPanel() {
		serverPanel = ServerPanel.makeWindow( superColliderServer , ServerPanel.MIMIC | ServerPanel.CONSOLE | ServerPanel.DUMP );
		serverPanel.setVisible(true);
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
