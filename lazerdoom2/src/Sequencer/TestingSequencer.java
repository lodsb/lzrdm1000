package Sequencer;

import java.util.LinkedList;

public class TestingSequencer implements SequencerInterface {

	private LinkedList<SequenceEvent> sequenceEvents = new LinkedList<SequenceEvent>();
	
	public LinkedList<SequenceEvent> getSequenceEventList() {
		return sequenceEvents;
	}
	
	@Override
	public void postSequenceEvent(SequenceEvent sequenceEvent) {
		sequenceEvents.add(sequenceEvent);
	}
	
	
	public void simulateEvals(SequenceInterface sequence, long ticks) {
		System.out.println("Simulating "+ticks+" ticks with sequence "+sequence);
		for(long i = 0; i < ticks; i++) {
			sequence.eval(i);
		}
	}

	@Override
	public boolean processTick(long tick) {
		return true;
	}

}
