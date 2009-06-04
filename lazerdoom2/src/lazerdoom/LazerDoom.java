package lazerdoom;

import java.io.IOException;

import javax.swing.JFrame;

import Control.ControlServer;
import Sequencer.ParallelSequenceContainer;
import Sequencer.Sequencer;
import Sequencer.HighResolutionPollingClock;
import de.sciss.jcollider.*;
import de.sciss.jcollider.gui.ServerPanel;

public class LazerDoom {
	private HighResolutionPollingClock clock;
	private Sequencer sequencer;
	private ParallelSequenceContainer mainSequenceContainer;
	private ControlServer controlServer;
	private Server superColliderServer;
	private JFrame serverPanel;
	
	public static void main(String[] args) {
		LazerDoom lazerdoom = new LazerDoom();
		lazerdoom.start();
		System.out.println("Started LazerDoom");
		lazerdoom.createAndShowServerPanel();
			
	}
	
	public void createAndShowServerPanel() {
		serverPanel = ServerPanel.makeWindow( superColliderServer , ServerPanel.MIMIC | ServerPanel.CONSOLE | ServerPanel.DUMP );
		serverPanel.setVisible(true);
	}
	
	public LazerDoom() {
		mainSequenceContainer = new ParallelSequenceContainer();
		try {
			superColliderServer = new Server("localhost");
		} catch (IOException e) {
			System.err.println("Error creating SC3-Server:\n"+e.getMessage());
			e.printStackTrace();
		}
		controlServer = new ControlServer(superColliderServer, 50);
		sequencer = new Sequencer(mainSequenceContainer, controlServer);
		clock = new HighResolutionPollingClock(100000, sequencer);
	}
	
	public void start() {
		try {
			superColliderServer.start();
			superColliderServer.startAliveThread();
		} catch (IOException e) {
			System.err.println("Error starting SC3-Server:\n"+e.getMessage());
			e.printStackTrace();
		}
		
		clock.start();
	}
}
