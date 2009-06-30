package Sequencer;

import java.util.ArrayList;

import Sequencer.SequenceEvent.SequenceEventSubtype;
import Sequencer.SequenceEvent.SequenceEventType;

import com.trolltech.qt.core.QObject;

public class ParallelSequenceContainer extends BaseSequence implements SequenceContainerInterface {

	ArrayList<SequenceInterface> sequences;
	
	boolean isRunning = false;
	
	private long currentSize;
	
	@Override
	public void appendSequence(SequenceInterface sequence) {
		sequences.add(sequence);
		this.updateSize();
		
		this.postSequenceEvent(SequenceEventType.APPEND_SEQUENCE, SequenceEventSubtype.NONE, sequence);
		this.postSequenceEvent(SequenceEventType.SEQUENCE_SIZE_CHANGED, SequenceEventSubtype.SIZE_IN_TICKS, this.size());
	}
	
	private ParallelSequenceContainer(Sequencer sequencer, ArrayList<SequenceInterface> sequences) {
		super(sequencer);
		this.sequences = sequences;
	}
	
	public ParallelSequenceContainer(Sequencer sequencer) {
		super(sequencer);
		this.sequences = new ArrayList<SequenceInterface>();
	}

	@Override
	public void prependSequence(SequenceInterface sequence) {
		sequences.add(sequence);
		
		this.updateSize();
		this.postSequenceEvent(SequenceEventType.PREPEND_SEQUENCE, SequenceEventSubtype.NONE, sequence);
		this.postSequenceEvent(SequenceEventType.SEQUENCE_SIZE_CHANGED, SequenceEventSubtype.SIZE_IN_TICKS, this.size());
	}

	@Override
	public boolean eval(long tick) {
		
		boolean currentlyRunning = false;
		
		for(SequenceInterface sequence: sequences) {
			if(tick < sequence.size()) {
				boolean sequenceWasRunning = sequence.isRunning();
				boolean sequenceRunning = sequence.eval(tick);
				
				if(sequenceRunning && !sequenceWasRunning) {
					this.postSequenceEvent(SequenceEventType.STARTED, SequenceEventSubtype.NONE, sequence);
				} else if(!sequenceRunning && sequenceWasRunning) {
					this.postSequenceEvent(SequenceEventType.STOPPED, SequenceEventSubtype.NONE, sequence);
				}
				currentlyRunning = currentlyRunning | sequence.eval(tick);
			}
		}
		
		this.isRunning = currentlyRunning;
		return this.isRunning;
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public void reset() {
		isRunning = false;
	}

	private void updateSize() {
		long size = 0;
		
		for(SequenceInterface sequence: sequences) {
			if(size < sequence.size()) {
				size = sequence.size();
			}
		}
		
		this.currentSize = size;
	}
	
	@Override
	public long size() {
		return this.currentSize;
	}

	@Override
	public SequenceInterface deepCopy() {
		ParallelSequenceContainer copy;
		
		ArrayList<SequenceInterface> sequenceList = new ArrayList<SequenceInterface>();
		
		for(SequenceInterface sequence: sequences) {
			sequenceList.add(sequence.deepCopy());
		}
		
		copy = new ParallelSequenceContainer(this.sequencer, sequenceList);
		this.postSequenceEvent(SequenceEventType.CLONED_SEQUENCE, SequenceEventSubtype.NONE, copy);
		return copy;  
	}

	@Override
	public void removeSequence(SequenceInterface sequence) {
		this.sequences.remove(sequence);
	}

}
