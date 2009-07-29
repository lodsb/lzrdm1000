package Sequencer;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import Sequencer.SequenceEvent.SequenceEventSubtype;
import Sequencer.SequenceEvent.SequenceEventType;

import com.trolltech.qt.core.QObject;

public class ParallelSequenceContainer extends BaseSequence implements SequenceContainerInterface {

	CopyOnWriteArrayList<SequenceInterface> sequences;
	
	boolean isRunning = false;
	
	private long currentSize;
	
	@Override
	public void appendSequence(SequenceInterface sequence) {
		sequences.add(sequence);
		this.updateSize();
		
		this.postSequenceEvent(SequenceEventType.APPEND_SEQUENCE, SequenceEventSubtype.SEQUENCE, sequence);
		this.postSequenceEvent(SequenceEventType.SEQUENCE_SIZE_CHANGED, SequenceEventSubtype.SIZE_IN_TICKS, this.size());
	}
	
	private ParallelSequenceContainer(SequencerInterface sequencer, CopyOnWriteArrayList<SequenceInterface> sequences) {
		super(sequencer);
		this.sequences = sequences;
	}
	
	public ParallelSequenceContainer(SequencerInterface sequencer) {
		super(sequencer);
		this.sequences = new CopyOnWriteArrayList<SequenceInterface>();
	}

	@Override
	public void prependSequence(SequenceInterface sequence) {
		sequences.add(sequence);
		this.updateSize();
		
		this.postSequenceEvent(SequenceEventType.PREPEND_SEQUENCE, SequenceEventSubtype.SEQUENCE, sequence);
		this.postSequenceEvent(SequenceEventType.SEQUENCE_SIZE_CHANGED, SequenceEventSubtype.SIZE_IN_TICKS, this.size());
	}

	@Override
	public boolean eval(long tick) {
		if(tick == 0) {
			this.postSequenceEvent(SequenceEventType.STARTED, SequenceEventSubtype.NONE, null);
		}
		
		boolean currentlyRunning = false;
		
		for(SequenceInterface sequence: sequences) {
				if(tick < sequence.size()) {
					currentlyRunning = currentlyRunning | sequence.eval(tick);
				}
		}
		
		
		if(!currentlyRunning) {
			this.postSequenceEvent(SequenceEventType.STOPPED, SequenceEventSubtype.NONE, null);
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
		this.reset();
		isRunning = false;
		
		//reset all children
		for(SequenceInterface sequence: sequences) {
			sequence.reset();
		}
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
		
		CopyOnWriteArrayList<SequenceInterface> sequenceList = new CopyOnWriteArrayList<SequenceInterface>();
		
		for(SequenceInterface sequence: sequences) {
				sequenceList.add(sequence.deepCopy());
		}
		
		
		copy = new ParallelSequenceContainer(this.getSequencer(), sequenceList);
		this.postSequenceEvent(SequenceEventType.CLONED_SEQUENCE, SequenceEventSubtype.SEQUENCE, copy);
		return copy;  
	}

	@Override
	public void removeSequence(SequenceInterface sequence) {
		this.postSequenceEvent(SequenceEventType.REMOVE, SequenceEventSubtype.SEQUENCE, sequence);
		this.sequences.remove(sequence);
	}

}
