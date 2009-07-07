package Sequencer;

import Control.TestingControlBus;
import Control.Types.DoubleType;
import org.junit.*;

public class EventPointsSequenceTest {
	// TESTING
	private TestingControlBus<DoubleType> testBus;
	private TestingSequencer testSequencer;
	private EventPointsSequence<DoubleType> eventPointsSequence;
	
	public EventPointsSequenceTest() {
		super();
		testBus = new TestingControlBus<DoubleType>();
		testSequencer = new TestingSequencer();
		eventPointsSequence = new EventPointsSequence<DoubleType>(testSequencer);
		eventPointsSequence.addControlBus(testBus);
	}

	@Test
	public void testSequence() {
		System.out.println("SHSHSHSH");
		testSequencer.simulateEvals(eventPointsSequence, 1000);
		assert true;
	}	
}
