package Synth.Graph;

import Sequencer.EventSequenceInterface;
import Synth.SynthInstance;

public class SynthesizerNode {
	private EventSequenceInterface sequence = null;
	private SynthInstance synthInstance = null;
	private boolean isSequenceNode;
	
	public SynthesizerNode(EventSequenceInterface seq, SynthInstance synth) {
		this.sequence = seq;
		this.synthInstance = synth;
		
		if(this.sequence != null) {
			this.isSequenceNode = true;
		}
	}
	
	public boolean isSequenceNode() {
		return this.isSequenceNode;
	}
	
	public EventSequenceInterface getEventSequence() {
		return this.sequence;
	}
	
	public SynthInstance getSynthInstance() {
		return this.synthInstance;
	}
}
