package Sequencer;

import java.util.ArrayList;

public class ParallelSequenceContainer implements SequenceContainerInterface {

	ArrayList<SequenceInterface> sequences;
	
	boolean isRunning = false;
	
	@Override
	public void appendSequence(SequenceInterface sequence) {
		sequences.add(sequence);
	}
	
	private ParallelSequenceContainer(ArrayList<SequenceInterface> sequences) {
		this.sequences = sequences;
	}
	
	public ParallelSequenceContainer() {
		this.sequences = new ArrayList<SequenceInterface>();
	}

	@Override
	public void prependSequence(SequenceInterface sequence) {
		sequences.add(sequence);
	}

	@Override
	public boolean eval(long tick) {
		isRunning = false;
		
		for(SequenceInterface sequence: sequences) {
			if(tick < sequence.size()) {
				isRunning = isRunning | sequence.eval(tick);
			}
		}
		
		return isRunning;
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public void reset() {
		isRunning = false;
	}

	@Override
	public long size() {
		long size = 0;
		
		for(SequenceInterface sequence: sequences) {
			if(size < sequence.size()) {
				size = sequence.size();
			}
		}
		
		return size;
	}

	@Override
	public SequenceInterface deepCopy() {
		ArrayList<SequenceInterface> sequenceList = new ArrayList<SequenceInterface>();
		
		for(SequenceInterface sequence: sequences) {
			sequenceList.add(sequence.deepCopy());
		}
		
		return new ParallelSequenceContainer(sequenceList);  
	}

}
