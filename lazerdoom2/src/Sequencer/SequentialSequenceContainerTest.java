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
	}
	
	private void createNewSequences(int numberOfSequences) {
		sequences.clear();
		
		for(int i = 0; i < numberOfSequences; i++) {
			EventPointsSequence<DoubleType> eventSequence = new EventPointsSequence<DoubleType>(sequencer);
			eventSequence.addControlBus(controlBus);
			eventSequence.setLength(numberOfEventsPerSequence);
			sequences.add(eventSequence);
		}
	}
	
	private void fillSequencesWithRandomEvents(int numberOfEvents, long maxTick) {
		for(EventPointsSequence<DoubleType> eventSequence: sequences) {
			fillEventSequenceWithRandomEvents(eventSequence, numberOfEvents, maxTick);
		}
	}
	
	public void runTest() {
		createNewSequences(numberOfEventSequences);
		
		// Add the sequences and check whether the appropriate events have been posted
		for(EventSequenceInterface<DoubleType> eventSequence: sequences) {
			this.sequenceContainer.appendSequence(eventSequence);
		}
		
		LinkedList<SequenceEvent> sequenceEvents = sequencer.getSequenceEventList();
		
		// number of events (ctrl-bus-add (in create sequences), add, sizechange per added sequence)
		assertTrue("Number of sequence-events after append sequences incorrect",sequenceEvents.size() == numberOfEventSequences*3);
		
		// check for overall length of sequence container (ticks)
		fillSequencesWithRandomEvents(numberOfEventsPerSequence, lengthOfSequencesInTicks);
		
		// last event ought to be the size of the container in ticks
		SequenceEvent se = sequenceEvents.getLast();
		System.out.println(se.getSequenceEventSubtype());
		assertTrue("Last event is not SEQUENCE_SIZE_CHANGED", se.getSequenceEventType() == SequenceEventType.SEQUENCE_SIZE_CHANGED && se.getSequenceEventSubtype() == SequenceEventSubtype.SIZE_IN_TICKS);
		/*assertTrue("Size of container does not equal sum of all sequence-objects", 
				se.getSequenceEventType() == SequenceEventType.SEQUENCE_SIZE_CHANGED
				&& se.getSequenceEventSubtype() == SequenceEventSubtype.SIZE_IN_TICKS
				&& (((Long)se.getArgument()) == numberOfEventSequences*lengthOfSequencesInTicks));*/
		
		
	}
}

