package Sequencer;

import java.util.ArrayList;
import java.util.LinkedList;
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
	}

	@Override
	public boolean eval(long tick) {
		/*if(tick % 100 == 0) {
			System.out.println("BANG size "+this.sequences.size()+" length "+this.currentSize+ " current eval "+tick);
		}*/
		if(tick == 0) {
			this.postSequenceEvent(SequenceEventType.STARTED, SequenceEventSubtype.NONE, null);
		}
		
		boolean currentlyRunning = false;
		
		readLock.lock();
		for(SequenceInterface sequence: sequences) {
			//System.out.print(" pp "+sequence.size()+" ");
				if(tick <= sequence.size() || sequence instanceof SequencePlayer) {
					currentlyRunning = currentlyRunning | sequence.eval(tick);
				}
		}
		readLock.unlock();
		
		
		if(!currentlyRunning) {
			//System.out.println("STOPPED PARALLEL SHIT");
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
		
		if(size != currentSize) {
			this.currentSize = size;
			this.postSequenceEvent(SequenceEventType.SEQUENCE_SIZE_CHANGED, SequenceEventSubtype.SIZE_IN_TICKS, size);
		}
	}
	
	@Override
	public long size() {
		//this.updateSize();
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
			System.out.println("parallel size changed");
			System.out.println("size "+this.currentSize+" "+this);
			if(sequences.size() > 0) {
				System.out.println(this.sequences.get(0).size());
			}
			readLock.unlock();
		}
		
	}

	@Override
	public void updateStructure(LinkedList<SequenceInterface> updatedSequences) {
		writeLock.lock();

		// check if the sequences have to be changed
		boolean changeNecessary = false;
		
		int i = 0;
		if(this.sequences.size() == updatedSequences.size()) { 
			for(SequenceInterface si: updatedSequences) {
				if(!this.sequences.contains(si)) {
					changeNecessary = true;
				}
			}
		} else {
			changeNecessary = true;
		}
		
		if(changeNecessary) {
			// sequences that have to be removed
			for(SequenceInterface sequence: this.sequences) {
				if(!updatedSequences.contains(sequence)) {
					this.postSequenceEvent(SequenceEventType.REMOVE, SequenceEventSubtype.SEQUENCE, sequence);

					if(sequence instanceof BaseSequence) {
						((BaseSequence)sequence).unregisterSequenceEventListener(this);
					}
				}
			}

			// sequences that will be added
			for(SequenceInterface sequence: updatedSequences) {
				if(!this.sequences.contains(sequence)) {		
					this.postSequenceEvent(SequenceEventType.APPEND_SEQUENCE, SequenceEventSubtype.SEQUENCE, sequence);

					if(sequence instanceof BaseSequence) {
						((BaseSequence)sequence).registerSequenceEventListener(this);
					}
				}
			}

			this.sequences.clear();
			
			for(SequenceInterface si: updatedSequences) {
				this.sequences.add(si);
			}

			this.updateSize();
		}
		
		writeLock.unlock();
	}
	
	public String toString() {
		String ret = "\nparallel: (";
		
		for(SequenceInterface si: this.sequences) {
			ret = ret+si+" ";
		}
		
		return ret+" )";
	}
}
