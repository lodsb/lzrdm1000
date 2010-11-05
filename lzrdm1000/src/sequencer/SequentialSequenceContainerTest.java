package sequencer;

import java.util.ArrayList;
import java.util.LinkedList;

import junit.framework.TestCase;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

import sequencer.EventPointsSequence;
import sequencer.SequenceEvent.SequenceEventSubtype;
import sequencer.SequenceEvent.SequenceEventType;

import control.TestingControlBus;
import control.types.DoubleType;

public class SequentialSequenceContainerTest extends TestCase {
	private SequentialSequenceContainer sequenceContainer;
	private TestingSequencer sequencer;
	private TestingControlBus<DoubleType> controlBus;
	
	private final int numberOfEventSequences = 500;
	private final int numberOfEventsPerSequence = 1000;
	private final long lengthOfSequencesInTicks = 1000;
	
	private int numberOfSequenceReordersAndVerifies = 100;
	
	private ReentrantLock lock = new ReentrantLock();
	
	private LinkedList<EventPointsSequence<DoubleType>> sequences;
	
	private void changeSequenceOrderAppend() {
		Random random = new Random();
		int sequenceIndex = Math.abs(random.nextInt()) % numberOfEventSequences;
		
		System.out.print("Exchanging (Append) : "+sequenceIndex);
		 
		EventPointsSequence<DoubleType> sequence = sequences.get(sequenceIndex);
		sequences.remove(sequenceIndex);
		
		sequences.add(sequence);
		
		sequenceContainer.removeSequence(sequence);
		sequenceContainer.appendSequence(sequence);
		System.out.println("...done");
	}
	
	private void changeSequenceOrderPrepend() {
		Random random = new Random();
		int sequenceIndex = Math.abs(random.nextInt()) % numberOfEventSequences;
		
		System.out.print("Exchanging (Prepend) : "+sequenceIndex);
		
		EventPointsSequence<DoubleType> sequence = sequences.get(sequenceIndex);
		sequences.remove(sequenceIndex);
		
		sequences.add(0, sequence);
		
		sequenceContainer.removeSequence(sequence);
		sequenceContainer.prependSequence(sequence);
		System.out.println("...done");
	}

	private void changeSequenceOrderAppendSuccessor() {
		Random random = new Random();
		int sequenceIndex = Math.abs(random.nextInt()) % numberOfEventSequences;
		
		System.out.print("Exchanging (AppendSuccessor) : "+sequenceIndex);
		
		//System.out.println(">>>>"+sequences);
		//System.out.println(sequenceContainer._testingGetSequences());
		EventPointsSequence<DoubleType> sequence = sequences.get(sequenceIndex);
		sequences.remove(sequenceIndex);
		
		int successorIndex = Math.abs(random.nextInt()) % (sequences.size()-1);
		EventPointsSequence<DoubleType> successor = sequences.get(successorIndex);
		
		System.out.println(" predecessor "+successorIndex);
		//System.out.println("PRED "+successor);
		//System.out.println("SEQ "+sequence);
		
		if(successorIndex == sequences.size()) {
			sequences.add(sequence);
		} else {
			sequences.add(successorIndex+1, sequence);
		}
		
		//System.out.println("<<<<"+sequences);
		
		sequenceContainer.removeSequence(sequence);
		sequenceContainer.appendSequence(successor, sequence);
		//System.out.println(sequenceContainer._testingGetSequences());
		//System.out.println("...done");
	}
	
	private void changeSequenceOrderPrependPredecessor() {
		Random random = new Random();
		int sequenceIndex = Math.abs(random.nextInt()) % numberOfEventSequences;
		
		System.out.print("Exchanging (PrependPredecessor) : "+sequenceIndex);
		
		//System.out.println(">>>>"+sequences);
		//System.out.println(sequenceContainer._testingGetSequences());
		EventPointsSequence<DoubleType> sequence = sequences.get(sequenceIndex);
		sequences.remove(sequenceIndex);
		
		int successorIndex = Math.abs(random.nextInt()) % (sequences.size()-1);
		EventPointsSequence<DoubleType> successor = sequences.get(successorIndex);
		
		System.out.println(" successor "+successorIndex);
		//System.out.println("PRED "+successor);
		//System.out.println("SEQ "+sequence);
		
		if(successorIndex == 0) {
			sequences.add(0,sequence);
		} else {
			sequences.add(successorIndex, sequence);
		}
		
		//System.out.println("<<<<"+sequences);
		
		sequenceContainer.removeSequence(sequence);
		sequenceContainer.prependSequence(successor, sequence);
		//System.out.println(sequenceContainer._testingGetSequences());
		//System.out.println("...done");
	}
	
	private boolean verifySequenceOrder() {
		boolean allClear = true;
		
		ArrayList<SequenceInterface> containerSequences = this.sequenceContainer._testingGetSequences();
		
		int i = 0;
		
		for(EventPointsSequence<DoubleType> currentSequence: sequences) {
			if(containerSequences.get(i) != currentSequence) {
				allClear = false;
			}
			i++;
		}
		
		return allClear;
	}
	
	private boolean verifySequence() {
		controlBus.clearEntriesAndReset();
		
		sequencer.simulateEvals(sequenceContainer, numberOfEventSequences*numberOfEventsPerSequence, 1);
		
		LinkedList<TestingControlBus<DoubleType>.ControlBusEntry> recordedEntries = controlBus.getControlBusEntryList();
		
		boolean allClear = true;
		
		//System.out.println(recordedEntries);
		for(TestingControlBus<DoubleType>.ControlBusEntry cbEntry: recordedEntries) {
			if(((EventPointsSequence<DoubleType>) cbEntry.sequence)._testingGetValueOfTick(cbEntry.tick).getFloatValue() != cbEntry.value) {
				allClear = false;
			}
		}
		
		return allClear;
	}
	
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
			//eventSequence.insert(new DoubleType(0.123), 23);
			Testing.Util.fillEventSequenceWithRandomEvents(eventSequence, numberOfEvents, maxTick);
		}
	}
	
	public void runTest() {
		createNewSequences(numberOfEventSequences);
		
		// Add the sequences and check whether the appropriate events have been posted
		for(EventSequenceInterface<DoubleType> eventSequence: sequences) {
			this.sequenceContainer.appendSequence(eventSequence);
		}

		// Wait for event-thread to finish pumping events
		try {
			Thread.sleep(1000,0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LinkedList<SequenceEvent> sequenceEvents = sequencer.getSequenceEventList();
		
		// number of events (ctrl-bus-add (in create sequences), add, set-length, sizechange per added sequence, container-size changed and appended sequence)
		assertTrue("Number of sequence-events after append sequences incorrect",sequenceEvents.size() == numberOfEventSequences*5);
		
		// check for overall length of sequence container (ticks)
		fillSequencesWithRandomEvents(numberOfEventsPerSequence, lengthOfSequencesInTicks);
		
		// Wait for event-thread to finish pumping events
		try {
			Thread.sleep(1000,0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int runs = 0;
		
		System.out.println("***APPEND***");
		
		runs = 0;
		do {
			System.out.println("RUN: "+runs);
			assertTrue("APPEND Order of sequences in container does not match internal records", this.verifySequenceOrder());
			assertTrue("APPEND Order of simulated events does not match with event data in all sequences", this.verifySequence());
			this.changeSequenceOrderAppend();
			runs++;
		} while(runs < numberOfSequenceReordersAndVerifies);
		
		sequencer.simulateEvalsThreaded(sequenceContainer, numberOfEventSequences*numberOfEventsPerSequence, 10);
		runs = 0;
		do {
			System.out.println("RUN (threaded): "+runs);
			this.changeSequenceOrderAppend();
			runs++;
		} while(runs < numberOfSequenceReordersAndVerifies);
		
		while(!sequencer.threadFinished());
		
		assertTrue("(THREADED) APPEND Order of sequences in container does not match internal records", this.verifySequenceOrder());
		assertTrue("(THREADED) APPEND Order of simulated events does not match with event data in all sequences", this.verifySequence());

		System.out.println("***PREPEND***");

		runs = 0;
		do {
			System.out.println("RUN: "+runs);
			assertTrue("PREPEND Order of sequences in container does not match internal records", this.verifySequenceOrder());
			assertTrue("PREPEND Order of simulated events does not match with event data in all sequences", this.verifySequence());
			this.changeSequenceOrderPrepend();
			runs++;
		} while(runs < numberOfSequenceReordersAndVerifies);
		
		sequencer.simulateEvalsThreaded(sequenceContainer, numberOfEventSequences*numberOfEventsPerSequence, 10);
		runs = 0;
		do {
			System.out.println("RUN (threaded): "+runs);
			this.changeSequenceOrderPrepend();
			runs++;
		} while(runs < numberOfSequenceReordersAndVerifies);
		
		while(!sequencer.threadFinished());
		
		assertTrue("(THREADED) PREPEND Order of sequences in container does not match internal records", this.verifySequenceOrder());
		assertTrue("(THREADED) PREPEND Order of simulated events does not match with event data in all sequences", this.verifySequence());
		
		
		System.out.println("***AppendSuccessor***");

		runs = 0;
		do {
			System.out.println("RUN: "+runs);
			assertTrue("AppendSuccessor Order of sequences in container does not match internal records", this.verifySequenceOrder());
			assertTrue("AppendSuccessor Order of simulated events does not match with event data in all sequences", this.verifySequence());
			this.changeSequenceOrderAppendSuccessor();
			runs++;
		} while(runs < numberOfSequenceReordersAndVerifies);
		
		sequencer.simulateEvalsThreaded(sequenceContainer, numberOfEventSequences*numberOfEventsPerSequence, 10);
		runs = 0;
		do {
			System.out.println("RUN (threaded): "+runs);
			this.changeSequenceOrderAppendSuccessor();
			runs++;
		} while(runs < numberOfSequenceReordersAndVerifies);
		
		while(!sequencer.threadFinished());
		
		assertTrue("(THREADED) AppendSuccessor Order of sequences in container does not match internal records", this.verifySequenceOrder());
		assertTrue("(THREADED) AppendSuccessor Order of simulated events does not match with event data in all sequences", this.verifySequence());

		
		System.out.println("***PrependPredecessor***");

		runs = 0;
		do {
			System.out.println("RUN: "+runs);
			assertTrue("PrependPredecessorr Order of sequences in container does not match internal records", this.verifySequenceOrder());
			assertTrue("PrependPredecessor Order of simulated events does not match with event data in all sequences", this.verifySequence());
			this.changeSequenceOrderPrependPredecessor();
			runs++;
		} while(runs < numberOfSequenceReordersAndVerifies);
		
		sequencer.simulateEvalsThreaded(sequenceContainer, numberOfEventSequences*numberOfEventsPerSequence, 10);
		runs = 0;
		do {
			System.out.println("RUN (threaded): "+runs);
			this.changeSequenceOrderPrependPredecessor();
			runs++;
		} while(runs < numberOfSequenceReordersAndVerifies);
		
		while(!sequencer.threadFinished());
		
		assertTrue("(THREADED) PrependPredecessor Order of sequences in container does not match internal records", this.verifySequenceOrder());
		assertTrue("(THREADED) PrependPredecessor Order of simulated events does not match with event data in all sequences", this.verifySequence());
	}
}

