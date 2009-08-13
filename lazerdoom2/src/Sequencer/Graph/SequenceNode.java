package Sequencer.Graph;

import Sequencer.SequenceInterface;
import Sequencer.SequencerInterface;

public class SequenceNode {
	SequenceInterface si;
	
	public SequenceNode(SequenceInterface si) {
		this.si = si;
	}
	
	public SequenceInterface getSequence() {
		return this.si;
	}
}
