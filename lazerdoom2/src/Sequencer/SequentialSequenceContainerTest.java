<<<<<<< .mine
package Sequencer;

import java.util.LinkedList;

import Control.TestingControlBus;
import Control.Types.DoubleType;
import junit.framework.TestCase;
import Sequencer.EventPointsSequence;
import Sequencer.SequenceEvent.SequenceEventSubtype;
import Sequencer.SequenceEvent.SequenceEventType;

import java.util.Random;

public class SequentialSequenceContainerTest extends TestCase {
	private SequentialSequenceContainer sequenceContainer;
	private TestingSequencer sequencer;
	private TestingControlBus<DoubleType> controlBus;
	
	private final int numberOfEventSequences = 100;
	private final int numberOfEventsPerSequence = 500;
	private final long lengthOfSequencesInTicks = 1000;
	
	
	//TODO: move to Testing.Util
	public static void fillEventSequenceWithRandomEvents(EventPointsSequence<DoubleType> eventSequence, int numberOfEvents, long maxTick) {
		Random randomSeed = new Random();
		
		for(int i = 0; i < numberOfEvents; i++) {
			long tick = Math.abs(randomSeed.nextLong()) % maxTick;
			double value = Math.abs(randomSeed.nextDouble());
			
			eventSequence.insert(new DoubleType(value), tick);
		}
	}
	
	private LinkedList<EventPointsSequence<DoubleType>> sequences;
	
	public void setUp() {
		sequencer = new TestingSequencer();
		controlBus = new TestingControlBus<DoubleType>();
		sequenceContainer = new SequentialSequenceContainer(sequencer);
		sequences = new LinkedList<EventPointsSequence<DoubleType>>();
		
		createNewSequences(numberOfEventSequences);
		fillSequencesWithRandomEvents(numberOfEventsPerSequence, lengthOfSequencesInTicks);
	}
	
	private void createNewSequences(int numberOfSequences) {
		sequences.clear();
		
		for(int i = 0; i < numberOfSequences; i++) {
			EventPointsSequence<DoubleType> eventSequence = new EventPointsSequence<DoubleType>(sequencer);
			eventSequence.addControlBus(controlBus);
			sequences.add(eventSequence);
		}
	}
	
	private void fillSequencesWithRandomEvents(int numberOfEvents, long maxTick) {
		for(EventPointsSequence<DoubleType> eventSequence: sequences) {
			fillEventSequenceWithRandomEvents(eventSequence, numberOfEvents, maxTick);
		}
	}
	
	public void runTest() {
		// Add the sequences and check whether the appropriate events have been posted
		for(EventSequenceInterface<DoubleType> eventSequence: sequences) {
			this.sequenceContainer.appendSequence(eventSequence);
		}
		
		LinkedList<SequenceEvent> sequenceEvents = sequencer.getSequenceEventList();
		
		// check for overall length of sequence container (ticks)
		// number of events
		
		assertTrue("Number of sequence-events after append sequences correct",sequenceEvents.size() == numberOfEventSequences*2);
		
		// last event ought to be the size of the container in ticks
		SequenceEvent se = sequenceEvents.getLast();
		
		assertTrue("Size of container equals sum of all sequence-objects", 
				se.getSequenceEventType() == SequenceEventType.SEQUENCE_SIZE_CHANGED
				&& se.getSequenceEventSubtype() == SequenceEventSubtype.SIZE_IN_TICKS
				&& (((Long)se.getArgument()) == numberOfEventSequences*lengthOfSequencesInTicks));
		
		
	}
}
=======
package Sequencer;

import junit.framework.TestCase;

public class SequentialSequenceContainerTest extends TestCase {
	public void setUp() {
		
	}
	
	public void runTest() {
		
	}
}
>>>>>>> .r41
