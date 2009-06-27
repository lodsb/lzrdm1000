package Sequencer;

import java.util.ArrayList;

import com.trolltech.qt.core.QObject;

public class SequentialSequenceContainer extends QObject implements SequenceContainerInterface {

	private Signal1<Long> evalSignal = new Signal1<Long>();
	@Override
	public Signal1<Long> getSequenceEvalUpdateSignal() {
		return evalSignal;
	}
	
	ArrayList<SequenceInterface> sequences = new ArrayList<SequenceInterface>();
	
	SequenceInterface currentSequence;
	
	boolean isRunning = false;
	long oldTickCntr = -1;
	long sequenceTickCntr = 0;
	
	int currentSequenceIndex = 0;
	
	private SequentialSequenceContainer(ArrayList<SequenceInterface> sequenceList) {
		this.sequences = sequenceList;
	}

	private boolean evaluateCurrentSequence() {
		if(!currentSequence.eval(sequenceTickCntr)) {
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

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void reset() {
		oldTickCntr = -1;
		sequenceTickCntr = 0;
		currentSequenceIndex = 0;
		
		isRunning = false;
	}

	@Override
	public long size() {
		long numberOfTicks = 0;
		
		for(SequenceInterface sequence: sequences) {
			numberOfTicks += sequence.size();
		}
			
		return numberOfTicks;
	}

	@Override
	public void appendSequence(SequenceInterface sequence) {
		sequences.add(sequence);		
	}

	public void appendSequence(SequenceInterface successor, SequenceInterface sequence) {
		int index = 0;
		for(SequenceInterface currentSequence: sequences) {
			if(currentSequence == successor) {
				if(index < sequences.size()-1) {
					prependSequence(index+1, sequence);
				} else {
					appendSequence(sequence);
				}
				
				break;
			}
			index++;
		}
	}
	
	@Override
	public void prependSequence(SequenceInterface sequence) {
		this.prependSequence(0, sequence);
	}
	
	public void prependSequence(SequenceInterface predecessor, SequenceInterface sequence) {
		int index = 0;
		for(SequenceInterface currentSequence: sequences) {
			if(currentSequence == predecessor) {
				prependSequence(index, sequence);
				break;
			}
			
			index++;
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
		ArrayList<SequenceInterface> sequenceList = new ArrayList<SequenceInterface>();
		
		for(SequenceInterface sequence: sequences) {
			sequenceList.add(sequence.deepCopy());
		}
		
		return new SequentialSequenceContainer(sequenceList);  
	}

}
