package Sequencer;

import java.util.LinkedList;

public interface SequenceContainerInterface extends SequenceInterface {
	public void appendSequence(SequenceInterface sequence);
	public void prependSequence(SequenceInterface sequence);
	public void removeSequence(SequenceInterface sequence);
	
	void updateStructure(LinkedList<SequenceInterface> sequences);
}
