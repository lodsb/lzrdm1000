package Sequencer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import Sequencer.SequenceEvent.SequenceEventSubtype;
import Sequencer.SequenceEvent.SequenceEventType;
import Sequencer.SequenceEvent.SequenceMetaEventType;

import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.Qt;

public class SequentialSequenceContainer extends BaseSequence implements SequenceContainerInterface, SequenceEventListenerInterface  {
	
	ArrayList<SequenceInterface> sequences;	
	
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

	SequenceInterface currentSequence;
	
	boolean isRunning = false;
	long oldTickCntr = -1;
	long sequenceTickCntr = 0;
	
	int currentSequenceIndex = 0;
	
	private long currentSize = 0;
	
	public SequentialSequenceContainer(SequencerInterface sequencer) {
		super(sequencer);
		this.sequences = new ArrayList<SequenceInterface>();
	}
	
	private SequentialSequenceContainer(SequencerInterface sequencer, ArrayList<SequenceInterface> sequenceList) {
		super(sequencer);
		this.sequences = sequenceList;
	}

	private boolean findAndSetCurrentSequence(long tick) {
		int sequenceIndex = 0;
		long currentTicks = 0;
		
		boolean foundSequence = false;

		for(SequenceInterface sequence: sequences) {
			if(tick > sequence.size()+currentTicks) {
				currentTicks += sequence.size();
				sequenceIndex++;
			} else {
				currentSequence = sequence;
				currentSequenceIndex = sequenceIndex;
				sequenceTickCntr = tick-currentTicks;

				foundSequence = true;
				break;
			}
		}

		if(!foundSequence) {
			sequenceTickCntr = 0;
			currentSequence = this.sequences.get(0);
			currentSequenceIndex = 0;
		}
		
		return foundSequence;
	}
	
	private boolean evaluateCurrentSequence() {
		if(currentSequence != null && currentSequence.eval(sequenceTickCntr)) {
			//System.out.println("EVAL: "+currentSequenceIndex+" tick "+sequenceTickCntr+"/"+currentSequence.size());
			sequenceTickCntr++;
		} else {
			sequenceTickCntr = 0;
			//System.out.println("CHG!");
			if(currentSequenceIndex+1 < sequences.size()) {
				currentSequenceIndex++;
				//System.out.println("SEQ INDX "+currentSequenceIndex);
				currentSequence = sequences.get(currentSequenceIndex);
				//System.out.println("EVAL: "+currentSequenceIndex+" tick "+sequenceTickCntr+"/"+currentSequence.size());
				boolean ret = currentSequence.eval(sequenceTickCntr);
				sequenceTickCntr++;
				
				return ret;
				
			} else {				
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public boolean eval(long tick) {
		
		if(tick == 0) {
			this.postSequenceEvent(SequenceEventType.STARTED, SequenceEventSubtype.NONE, null);
		}

		readLock.lock();
		
		if(sequences.size() > 0) {
			if(tick == 0) {
				currentSequence = sequences.get(0);
				currentSequenceIndex = 0;
				sequenceTickCntr = 0;
			}
			if(currentSequence == null) {
				findAndSetCurrentSequence(tick);
			}
			
			if(oldTickCntr == (tick-1)) {
				isRunning = evaluateCurrentSequence();
			} 
			else {
				if(findAndSetCurrentSequence(tick)) {
					isRunning = evaluateCurrentSequence();
				} else {
					isRunning = false;
				}
			}

			oldTickCntr = tick;


			if(!isRunning) {
				this.postSequenceEvent(SequenceEventType.STOPPED, SequenceEventSubtype.NONE, null);
			}
		}

		readLock.unlock();
		return isRunning;
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public void reset() {
		super.reset();
		readLock.lock();
			
			oldTickCntr = -1;
			sequenceTickCntr = 0;
			currentSequenceIndex = 0;
			
			isRunning = false;
			
			for(SequenceInterface sequence: sequences) {
				sequence.reset();
			}
			
		readLock.unlock();
	}

	private void updateSize() {
		long numberOfTicks = 0;
		
		for(SequenceInterface sequence: sequences) {
				numberOfTicks += sequence.size();
		}
		
		if(this.currentSize != numberOfTicks) {
			this.postSequenceEvent(SequenceEventType.SEQUENCE_SIZE_CHANGED, SequenceEventSubtype.SIZE_IN_TICKS, numberOfTicks);
		}
		
		this.currentSize = numberOfTicks;
	}
	
	@Override
	public long size() {
		return this.currentSize;
	}

	@Override
	public void appendSequence(SequenceInterface sequence) {
		writeLock.lock();
		// reset eval
		oldTickCntr = -1;
		
			pappendSequence(sequence);
			
		writeLock.unlock();
	}
	
	private void pappendSequence(SequenceInterface sequence) {
			sequences.add(sequence);		

			this.postSequenceEvent(SequenceEventType.APPEND_SEQUENCE, SequenceEventSubtype.SEQUENCE, sequence);
			this.updateSize();
			
			if(sequence instanceof BaseSequence) {
				((BaseSequence)sequence).registerSequenceEventListener(this);
			}
	}

	public void appendSequence(SequenceInterface predesessor, SequenceInterface sequence) {
		writeLock.lock();
		
		//reset eval
		oldTickCntr = -1;
		
			int index = 0;
			for(SequenceInterface currentSequence: sequences) {
				if(currentSequence == predesessor) {
					System.out.println(index);
					if(index < sequences.size()-1) {
						prependSequence(index+1, sequence);
					} else {
						pappendSequence(sequence);
					}

					break;
				}
				index++;
			}
			
		writeLock.unlock();
	}
	
	@Override
	public void prependSequence(SequenceInterface sequence) {
		writeLock.lock();
		
			this.prependSequence(0, sequence);
		
		writeLock.unlock();
	}
	
	public void prependSequence(SequenceInterface successor, SequenceInterface sequence) {
		writeLock.lock();
		
		// reset eval
		currentSequence = null;
		
			int index = 0;
			for(SequenceInterface currentSequence: sequences) {
				if(currentSequence == successor) {
					prependSequence(index, sequence);
					break;
				}

				index++;
			}
			
		writeLock.unlock();
	}
	
	private void prependSequence(int index, SequenceInterface sequence) {
		sequences.add(index, sequence);
		
		currentSequence = null;
		
		this.postSequenceEvent(SequenceEventType.PREPEND_SEQUENCE, SequenceEventSubtype.SEQUENCE, sequence);
		this.updateSize();
		
		if(sequence instanceof BaseSequence) {
			((BaseSequence)sequence).registerSequenceEventListener(this);
		}
	}

	@Override
	public SequenceInterface deepCopy() {
		SequentialSequenceContainer copy;
		ArrayList<SequenceInterface> sequenceList = new ArrayList<SequenceInterface>();
		
		readLock.lock();
			
			for(SequenceInterface sequence: sequences) {
				sequenceList.add(sequence.deepCopy());
			}
			
		readLock.unlock();
		
		copy = new SequentialSequenceContainer(this.getSequencer(), sequenceList);
		this.postSequenceEvent(SequenceEventType.CLONED_SEQUENCE, SequenceEventSubtype.SEQUENCE, copy);
		
		return copy;
	}

	@Override
	public void removeSequence(SequenceInterface sequence) {
		writeLock.lock();
		
			// reset eval
			oldTickCntr = -1;
			
			sequences.remove(sequence);
			this.oldTickCntr = -1;
		
			this.postSequenceEvent(SequenceEventType.REMOVE, SequenceEventSubtype.SEQUENCE, sequence);
			this.updateSize();
			
			if(sequence instanceof BaseSequence) {
				((BaseSequence)sequence).unregisterSequenceEventListener(this);
			}
			
		writeLock.unlock();
	}

	@Override
	public void dispatchSequenceEvent(SequenceEvent se) {
		if(se.getSequenceMetaEventType() == SequenceMetaEventType.SEQUENCE_DATA_CHANGED_EVENT) {
			readLock.lock();
			this.updateSize();
			readLock.unlock();
		}
		
	}
	
	ArrayList<SequenceInterface> _testingGetSequences() {
		return this.sequences;
	}

	@Override
	public void updateStructure(LinkedList<SequenceInterface> updatedSequences) {
		writeLock.lock();
		
		// check if the sequences have to be changed
		boolean changeNecessary = false;
		
		int i = 0;
		for(SequenceInterface si: updatedSequences) {
			if( (this.size()-1) < i || si != this.sequences.get(i)) {
				changeNecessary = true;
			} 
			i++;
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
		String ret = "\nsequential: [ ";
		
		for(SequenceInterface sequence: this.sequences) {
			ret = ret+sequence+" ";
		}
		
		return ret+" ]";
	}

}
