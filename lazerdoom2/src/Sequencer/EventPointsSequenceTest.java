package Sequencer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import Control.TestingControlBus;
import Control.Types.DoubleType;
import org.junit.*;
import static org.junit.Assert.*;

import Testing.Util;

public class EventPointsSequenceTest {
	// TESTING
	private TestingControlBus<DoubleType> testBus;
	private TestingSequencer testSequencer;
	private EventPointsSequence<DoubleType> eventPointsSequence;
	
	private final int numberOfEvents = 1000;
	
	private float[] events; 
	
	public EventPointsSequenceTest() {
		super();
		testBus = new TestingControlBus<DoubleType>();
		testSequencer = new TestingSequencer();
		eventPointsSequence = new EventPointsSequence<DoubleType>(testSequencer);
		eventPointsSequence.addControlBus(testBus);
		eventPointsSequence.setLength(1000);
		eventPointsSequence.setStartOffset(0);
		
		events = new float[numberOfEvents];
		resetEventEntries();
	}

	private void resetEventEntries() {
		for(int i = 0; i < numberOfEvents; i++) {
			events[i] = (float)-1.0;
		}
	}
	
	private void insertRandomValues(int numberOfValues) {
		System.out.println("Inserting "+numberOfValues +" events randomly");
		Random generator = new Random();
		
		for(int i = 0; i < numberOfValues; i++) {
			int index = (Math.abs(generator.nextInt())%numberOfEvents);
			
			while((float)-1.0 != events[index]) {
				index = (index+1)%numberOfEvents;
			}
			
			double value = Math.abs(generator.nextDouble());
			DoubleType entry = new DoubleType();
			entry.setValue(value);
			
			events[index] = entry.getFloatValue();
			eventPointsSequence.insert(entry, index);
		}
	}
	
	private void removeRandomValues(int numberOfValues) {
		System.out.println("Removing "+numberOfValues +" events randomly");
		Random generator = new Random();
		
		for(int i = 0; i < numberOfValues; i++) {
			int index = (Math.abs(generator.nextInt())%numberOfEvents);
			
			while(-1.0 == events[index]) {
				index = (index+1)%numberOfEvents;
			}
			
			events[index] = (float) -1.0;
			eventPointsSequence.remove(index);
		}
	}
	
	@Test
	public void testSequence() {
		testSequencer.simulateEvalsThreaded(eventPointsSequence, numberOfEvents, 10000);
		
		System.out.println("Modifying sequence while sequencer is running...");
		this.insertRandomValues(numberOfEvents/2);
		System.out.println("SequenceLength: "+eventPointsSequence.sequenceLength);
		this.removeRandomValues(numberOfEvents/4);
		System.out.println("Waiting for sequencer to stop...");
		
		while(!testSequencer.threadFinished());
		LinkedList<TestingControlBus<DoubleType>.ControlBusEntry> recordedEntries2 = testBus.getControlBusEntryList();
		System.out.println("rec entr "+recordedEntries2.size());
		
		testBus.clearEntriesAndReset();
		testSequencer.simulateEvals(eventPointsSequence, numberOfEvents, 10000);
		
		System.out.println(eventPointsSequence.events.size());
		System.out.print("Checking recorded events ...");
		LinkedList<TestingControlBus<DoubleType>.ControlBusEntry> recordedEntries = testBus.getControlBusEntryList();
		
		
		boolean allClear = true;
		
		for(TestingControlBus<DoubleType>.ControlBusEntry cbEntry: recordedEntries) {
			if(events[(int)cbEntry.tick] != cbEntry.value) {
				allClear = false;
			}
		}
		
		assertEquals(true, allClear);
		
		System.out.println("done!");
		
	}	
}
