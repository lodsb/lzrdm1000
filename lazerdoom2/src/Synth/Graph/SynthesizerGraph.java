package Synth.Graph;

import java.beans.EventSetDescriptor;
import java.util.HashMap;

import Sequencer.BaseSequence;
import Sequencer.SequenceInterface;
import Sequencer.EventSequenceInterface;
import Synth.SynthInstance;
import Control.ParameterControlBus;
import edu.uci.ics.jung.graph.DirectedSparseGraph;

public class SynthesizerGraph {
	private DirectedSparseGraph<SynthesizerNode, ParameterControlBusEdge> graph = new DirectedSparseGraph<SynthesizerNode, ParameterControlBusEdge>();
	private HashMap<Object, SynthesizerNode> synthesizerNodes = new HashMap<Object, SynthesizerNode>();
	
	public boolean connect(EventSequenceInterface seq, SynthInstance synth, ParameterControlBus controlBus) {
		SynthesizerNode src;
		SynthesizerNode dst;
		
		if((src = synthesizerNodes.get(seq)) == null) {
			src = new SynthesizerNode(seq, null);
		}
		
		if((dst = synthesizerNodes.get(synth)) == null) {
			dst = new SynthesizerNode(null, synth);
		}
		
		if(!graph.containsVertex(src)) {
			graph.addVertex(src);
		}
		
		if(!graph.containsVertex(dst)) {
			graph.addVertex(dst);
		}
		
		graph.addEdge(new ParameterControlBusEdge(controlBus), src, dst);
	
		seq.addControlBus(controlBus);
		
		return true;
	}
}
