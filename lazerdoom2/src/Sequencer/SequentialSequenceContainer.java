package Sequencer;

import java.util.ArrayList;

import Sequencer.SequenceEvent.SequenceEventSubtype;
import Sequencer.SequenceEvent.SequenceEventType;

import com.trolltech.qt.core.QObject;

public class SequentialSequenceContainer extends BaseSequence implements SequenceContainerInterface {
	
	ArrayList<SequenceInterface> sequences = new ArrayList<SequenceInterface>();
	
	SequenceInterface currentSequence;
	
	boolean isRunning = false;
	long oldTickCntr = -1;
	long sequenceTickCntr = 0;
	
	int currentSequenceIndex = 0;
	
	private long currentSize = 0;
	
	public SequentialSequenceContainer(Sequencer sequencer) {
		super(sequencer);
	}
	
	private SequentialSequenceContainer(Sequencer sequencer, ArrayList<SequenceInterface> sequenceList) {
		super(sequencer);
		this.sequences = sequenceList;
	}

	private boolean evaluateCurrentSequence() {
		if(currentSequence != null && !currentSequence.eval(sequenceTickCntr)) {
			sequenceTickCntr++;
		} else {
			sequenceTickCntr = 0;
			if(currentSequenceIndex < sequences.size()) {
				// stop current sequence event
				this.postSequenceEvent(SequenceEventType.STOPPED, SequenceEventSubtype.NONE, currentSequence);
				
				// start next sequence event				
				currentSequenceIndex++;
				currentSequence = sequences.get(currentSequenceIndex);
				this.postSequenceEvent(SequenceEventType.STARTED, SequenceEventSubtype.NONE, currentSequence);
				
			} else {
				// stop last sequence event
				this.postSequenceEvent(SequenceEventType.STOPPED, SequenceEventSubtype.NONE, currentSequence);
				
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
		synchronized(sequences) {
				// subsequent tick, no random access
			if(sequences.size() > 0) {
				if(oldTickCntr == (tick-1)) {
					isRunning = evaluateCurrentSequence();
				} else {
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
			}
			
			return isRunning;
		}
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public void reset() {
		super.reset();
		oldTickCntr = -1;
		sequenceTickCntr = 0;
		currentSequenceIndex = 0;
		
		isRunning = false;
		
		for(SequenceInterface sequence: sequences) {
			sequence.reset();
			if(sequence.isRunning()) {
				this.postSequenceEvent(SequenceEventType.STOPPED, SequenceEventSubtype.NONE, sequence);
			}
		}
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
		synchronized(sequences) {
			pappendSequence(sequence);
		}
	}
	
	private void pappendSequence(SequenceInterface sequence) {
			sequences.add(sequence);		

			this.updateSize();
			this.postSequenceEvent(SequenceEventType.APPEND_SEQUENCE, SequenceEventSubtype.NONE, sequence);
			this.postSequenceEvent(SequenceEventType.SEQUENCE_SIZE_CHANGED, SequenceEventSubtype.SIZE_IN_TICKS, this.size());
	}

	public void appendSequence(SequenceInterface successor, SequenceInterface sequence) {
		synchronized(sequences) {
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
		}
	}
	
	@Override
	public void prependSequence(SequenceInterface sequence) {
		synchronized(sequences) {
			this.pprependSequence(sequence);
		}
	}
	
	private void pprependSequence(SequenceInterface sequence) {
		this.prependSequence(0, sequence);
		this.updateSize();
		this.postSequenceEvent(SequenceEventType.PREPEND_SEQUENCE, SequenceEventSubtype.NONE, sequence);
		this.postSequenceEvent(SequenceEventType.SEQUENCE_SIZE_CHANGED, SequenceEventSubtype.SIZE_IN_TICKS, this.size());
	}
	
	public void prependSequence(SequenceInterface predecessor, SequenceInterface sequence) {
		synchronized(sequences) {
			int index = 0;
			for(SequenceInterface currentSequence: sequences) {
				if(currentSequence == predecessor) {
					prependSequence(index, sequence);
					break;
				}

				index++;
			}
		}
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
	
	}

	@Override
	public SequenceInterface deepCopy() {
		SequentialSequenceContainer copy;
		ArrayList<SequenceInterface> sequenceList = new ArrayList<SequenceInterface>();
		
		synchronized(sequences) {
			for(SequenceInterface sequence: sequences) {
				sequenceList.add(sequence.deepCopy());
			}
		}
		
		copy = new SequentialSequenceContainer(this.sequencer, sequenceList);
		this.postSequenceEvent(SequenceEventType.CLONED_SEQUENCE, SequenceEventSubtype.NONE, copy);
		
		return copy;
	}

	@Override
	public void removeSequence(SequenceInterface sequence) {
		synchronized(sequences) {
			sequences.remove(sequence);
			this.oldTickCntr = -1;
		}
	}

}
