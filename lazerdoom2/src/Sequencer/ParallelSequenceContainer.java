package Sequencer;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import Sequencer.SequenceEvent.SequenceEventSubtype;
import Sequencer.SequenceEvent.SequenceEventType;
import Sequencer.SequenceEvent.SequenceMetaEventType;

import com.trolltech.qt.core.QObject;

public class ParallelSequenceContainer extends BaseSequence implements SequenceContainerInterface, SequenceEventListenerInterface {

	ArrayList<SequenceInterface> sequences;	
	
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();
	boolean isRunning = false;
	
	private long currentSize;
	
	@Override
	public void appendSequence(SequenceInterface sequence) {
		writeLock.lock();
		sequences.add(sequence);
		this.updateSize();
		writeLock.unlock();
		
		this.postSequenceEvent(SequenceEventType.APPEND_SEQUENCE, SequenceEventSubtype.SEQUENCE, sequence);
		this.postSequenceEvent(SequenceEventType.SEQUENCE_SIZE_CHANGED, SequenceEventSubtype.SIZE_IN_TICKS, this.size());
	}
	
	private ParallelSequenceContainer(SequencerInterface sequencer, ArrayList<SequenceInterface> sequences) {
		super(sequencer);
		this.sequences = sequences;
	}
	
	public ParallelSequenceContainer(SequencerInterface sequencer) {
		super(sequencer);
		this.sequences = new ArrayList<SequenceInterface>();
	}

	@Override
	public void prependSequence(SequenceInterface sequence) {
		writeLock.lock();
		sequences.add(sequence);
		this.updateSize();
		writeLock.unlock();
		
		this.postSequenceEvent(SequenceEventType.PREPEND_SEQUENCE, SequenceEventSubtype.SEQUENCE, sequence);
		this.postSequenceEvent(SequenceEventType.SEQUENCE_SIZE_CHANGED, SequenceEventSubtype.SIZE_IN_TICKS, this.size());
	}

	@Override
	public boolean eval(long tick) {
		if(tick == 0) {
			this.postSequenceEvent(SequenceEventType.STARTED, SequenceEventSubtype.NONE, null);
		}
		
		boolean currentlyRunning = false;
		
		readLock.lock();
		for(SequenceInterface sequence: sequences) {
				if(tick < sequence.size()) {
					currentlyRunning = currentlyRunning | sequence.eval(tick);
				}
		}
		readLock.unlock();
		
		
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
		
		readLock.lock();
		//reset all children
		for(SequenceInterface sequence: sequences) {
			sequence.reset();
		}
		readLock.unlock();
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
		
		readLock.lock();
		for(SequenceInterface sequence: sequences) {
				sequenceList.add(sequence.deepCopy());
		}
		readLock.unlock();
		
		
		copy = new ParallelSequenceContainer(this.getSequencer(), sequenceList);
		this.postSequenceEvent(SequenceEventType.CLONED_SEQUENCE, SequenceEventSubtype.SEQUENCE, copy);
		return copy;  
	}

	@Override
	public void removeSequence(SequenceInterface sequence) {
		writeLock.lock();
		this.sequences.remove(sequence);
		writeLock.unlock();
		
		this.postSequenceEvent(SequenceEventType.REMOVE, SequenceEventSubtype.SEQUENCE, sequence);
	}

	@Override
	public void dispatchSequenceEvent(SequenceEvent se) {
		if(se.getSequenceMetaEventType() == SequenceMetaEventType.SEQUENCE_DATA_CHANGED_EVENT) {
			readLock.lock();
			this.updateSize();
			readLock.unlock();
		}
		
	}

}
