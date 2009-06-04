package lazerdoom;

import Control.ControlServer;
import Sequencer.ParallelSequenceContainer;
import Sequencer.Sequencer;
import Sequencer.HighResolutionPollingClock;
import Sequencer.SequentialSequenceContainer;

public class LazerDoom {
	private HighResolutionPollingClock clock;
	private Sequencer sequencer;
	private ParallelSequenceContainer mainSequenceContainer;
	private ControlServer controlServer;
	
	public static void main(String[] args) {
		LazerDoom lazerdoom = new LazerDoom();
		lazerdoom.start();
	}
	
	public LazerDoom() {
		mainSequenceContainer = new ParallelSequenceContainer();
		controlServer = new ControlServer(57110, 50);
		sequencer = new Sequencer(mainSequenceContainer, controlServer);
		clock = new HighResolutionPollingClock(70000, sequencer);
	}
	
	public void start() {
		clock.start();
	}
}
