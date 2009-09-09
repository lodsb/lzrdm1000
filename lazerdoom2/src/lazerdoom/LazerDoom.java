package lazerdoom;

import java.io.IOException;

import javax.swing.JFrame;

import sparshui.client.ServerConnection;
import sparshui.server.GestureServer;

import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QGraphicsView.ViewportUpdateMode;

import Control.ControlServer;
import Sequencer.ParallelSequenceContainer;
import Sequencer.SequenceContainerInterface;
import Sequencer.Sequencer;
import Sequencer.HighResolutionPollingClock;
import Sequencer.SequencerInterface;
import Synth.SynthController;
import de.sciss.jcollider.*;
import de.sciss.jcollider.gui.ServerPanel;

public class LazerDoom extends QWidget {
	private HighResolutionPollingClock clock;
	private Sequencer sequencer;
	private SequenceContainerInterface mainSequenceContainer;
	private ControlServer controlServer;
	private SynthController synthManager; 
	private Server superColliderServer;
	private JFrame serverPanel;
	
	private Process jackProcess;
	
	private Scene scene = new Scene();
	private View view = new View();
	private QHBoxLayout layout = new QHBoxLayout();
    TUIOTouchHandler handler = new TUIOTouchHandler(view);

	
	public static void main(String[] args) {
		QApplication.initialize(args);
		
		LazerDoom lazerdoom = new LazerDoom();
		System.out.println("Started LazerDoom");
		lazerdoom.createAndShowServerPanel();
			
	}
	
	public void quit() {
		jackProcess.destroy();
	}
	
	public void createAndShowServerPanel() {
		serverPanel = ServerPanel.makeWindow( superColliderServer , ServerPanel.MIMIC | ServerPanel.CONSOLE | ServerPanel.DUMP );
		serverPanel.setVisible(true);
	}
	
	public LazerDoom() {
		// For the clock working as a jack-client
		//System.setProperty("jjack.ports.input", "0");
		
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
		
		controlServer = new ControlServer(superColliderServer, 50);
		sequencer = new Sequencer(controlServer);
		
		clock = new HighResolutionPollingClock(100000, (SequencerInterface) sequencer);
		
		this.startSupercolliderServer();
		synthManager = new SynthController(this.superColliderServer);
		synthManager.init();
		clock.start();
		
		
		// GUI
		this.setLayout(layout);
		layout.addWidget(view);
		view.setScene(scene);
		view.setViewportUpdateMode(ViewportUpdateMode.MinimalViewportUpdate);
		this.setWindowFlags(Qt.WindowType.Window);
		
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
