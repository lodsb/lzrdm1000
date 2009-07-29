package Sequencer;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import Sequencer.SequenceEvent.SequenceEventSubtype;
import Sequencer.SequenceEvent.SequenceEventType;

import com.trolltech.qt.core.QObject;

public class SequentialSequenceContainer extends BaseSequence implements SequenceContainerInterface  {
	
	ArrayList<SequenceInterface> sequences;
	
	private ReentrantLock eventQueueLock = new ReentrantLock();
	
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

	private boolean evaluateCurrentSequence() {
		if(currentSequence != null && !currentSequence.eval(sequenceTickCntr)) {
			sequenceTickCntr++;
		} else {
			sequenceTickCntr = 0;
			if(currentSequenceIndex < sequences.size()) {
				currentSequenceIndex++;
				currentSequence = sequences.get(currentSequenceIndex);
			} else {				
				currentSequenceIndex = 0;
				if(sequences.size() >= 1) {
					currentSequence = sequences.get(0);
				}
				
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

		eventQueueLock.lock();
		
		if(sequences.size() > 0) {
			// subsequent tick, no random access
			if(oldTickCntr == (tick-1)) {
				isRunning = evaluateCurrentSequence();
			} 
			// random access
			else {
				long currentTicks = 0;
				int sequenceIndex = 0;

				for(SequenceInterface sequence: sequences) {
					if(tick > sequence.size()+currentTicks) {
						currentTicks += sequence.size();
						sequenceIndex++;
					} else {
						break;
					}
				}

				currentSequenceIndex = sequenceIndex;
				sequenceTickCntr = tick-currentTicks;

				isRunning = evaluateCurrentSequence();
			}

			oldTickCntr = tick;


			if(!isRunning) {
				this.postSequenceEvent(SequenceEventType.STOPPED, SequenceEventSubtype.NONE, null);
			}
		}

		eventQueueLock.unlock();


		return isRunning;
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public void reset() {
		super.reset();
		eventQueueLock.lock();
			
			oldTickCntr = -1;
			sequenceTickCntr = 0;
			currentSequenceIndex = 0;
			
			isRunning = false;
			
			for(SequenceInterface sequence: sequences) {
				sequence.reset();
			}
			
		eventQueueLock.unlock();
	}

	private void updateSize() {
		long numberOfTicks = 0;
		
		for(SequenceInterface sequence: sequences) {
			numberOfTicks += sequence.size();
		}
			
		this.currentSize = numberOfTicks;
	}
	
	@Override
	public long size() {
		return this.currentSize;
	}

	@Override
	public void appendSequence(SequenceInterface sequence) {
		eventQueueLock.lock();
			
			pappendSequence(sequence);
			
		eventQueueLock.lock();
	}
	
	private void pappendSequence(SequenceInterface sequence) {
			sequences.add(sequence);		

			this.updateSize();
			this.postSequenceEvent(SequenceEventType.APPEND_SEQUENCE, SequenceEventSubtype.NONE, sequence);
			this.postSequenceEvent(SequenceEventType.SEQUENCE_SIZE_CHANGED, SequenceEventSubtype.SIZE_IN_TICKS, this.size());
	}

	public void appendSequence(SequenceInterface successor, SequenceInterface sequence) {
		eventQueueLock.lock();
		
			int index = 0;
			for(SequenceInterface currentSequence: sequences) {
				if(currentSequence == successor) {
					if(index < sequences.size()-1) {
						prependSequence(index+1, sequence);
					} else {
						pappendSequence(sequence);
					}

					break;
				}
				index++;
			}
			
		eventQueueLock.unlock();
	}
	
	@Override
	public void prependSequence(SequenceInterface sequence) {
		eventQueueLock.lock();
		
			this.prependSequence(0, sequence);
		
		eventQueueLock.unlock();
	}
	
	public void prependSequence(SequenceInterface predecessor, SequenceInterface sequence) {
		eventQueueLock.lock();
			
			int index = 0;
			for(SequenceInterface currentSequence: sequences) {
				if(currentSequence == predecessor) {
					prependSequence(index, sequence);
					break;
				}

				index++;
			}
			
		eventQueueLock.unlock();
	}
	
	private void prependSequence(int index, SequenceInterface sequence) {
		sequences.add(index, sequence);
		
		long currentTicks = 0;
		int sequenceIndex = 0;
		 
		
		for(SequenceInterface currentSequence: sequences) {
			if(oldTickCntr > currentSequence.size()+currentTicks) {
				currentTicks += currentSequence.size();
				sequenceIndex++;
			} else {
				break;
			}
		}
		
		currentSequenceIndex = sequenceIndex;
		sequenceTickCntr = oldTickCntr-currentTicks;
		
		isRunning = evaluateCurrentSequence();
		
		this.updateSize();
		this.postSequenceEvent(SequenceEventType.PREPEND_SEQUENCE, SequenceEventSubtype.NONE, sequence);
		this.postSequenceEvent(SequenceEventType.SEQUENCE_SIZE_CHANGED, SequenceEventSubtype.SIZE_IN_TICKS, this.size());
	}

	@Override
	public SequenceInterface deepCopy() {
		SequentialSequenceContainer copy;
		ArrayList<SequenceInterface> sequenceList = new ArrayList<SequenceInterface>();
		
		eventQueueLock.lock();
			
			for(SequenceInterface sequence: sequences) {
				sequenceList.add(sequence.deepCopy());
			}
			
		eventQueueLock.unlock();
		
		copy = new SequentialSequenceContainer(this.getSequencer(), sequenceList);
		this.postSequenceEvent(SequenceEventType.CLONED_SEQUENCE, SequenceEventSubtype.NONE, copy);
		
		return copy;
	}

	@Override
	public void removeSequence(SequenceInterface sequence) {
		eventQueueLock.lock();
		
			sequences.remove(sequence);
			this.oldTickCntr = -1;
			
		eventQueueLock.unlock();
	}

}
