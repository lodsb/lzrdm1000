package sequencer.graph;

import sequencer.SequenceInterface;
import sequencer.SequencerInterface;

public class SequenceNode {
	SequenceInterface si;
	
	public SequenceNode(SequenceInterface si) {
		this.si = si;
	}
	
	public SequenceInterface getSequence() {
		return this.si;
	}
	
	public String toString() {
		return this.si.toString();
	}
}
