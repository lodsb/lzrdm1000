package Synth.Graph;

import java.beans.EventSetDescriptor;
import java.util.Collection;
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
			synthesizerNodes.put(seq, src);
		}
		
		if((dst = synthesizerNodes.get(synth)) == null) {
			dst = new SynthesizerNode(null, synth);
			synthesizerNodes.put(synth, dst);
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
	
	public boolean remove(SynthInstance synth) {
		boolean ret = true;
		
		SynthesizerNode node = this.synthesizerNodes.get(synth);
		
		if(node != null) {
			Collection<ParameterControlBusEdge> edges = graph.getInEdges(node);
			
			for(ParameterControlBusEdge edge: edges) {
				SynthesizerNode source = graph.getSource(edge);
				if(source.isSequenceNode()) {
					source.getEventSequence().removeControlBus(edge.getParameterControlBus());
				}
			}
			
			graph.removeVertex(node);
		}
		
		return ret;
	}
	
	public boolean remove(EventSequenceInterface sequence) {
		boolean ret = false;
		
		SynthesizerNode node = this.synthesizerNodes.get(sequence);
		
		if(node != null) {
			if(node.isSequenceNode()) {
				node.getEventSequence().removeAllControlBusses();
			}
			
			graph.removeVertex(node);
		}
		
		return ret;
	}

	public boolean disconnect(EventSequenceInterface sequence, SynthInstance synth) {
		boolean ret = false;
		SynthesizerNode synNode = this.synthesizerNodes.get(synth);
		SynthesizerNode seqNode = this.synthesizerNodes.get(sequence);
		if(synNode != null && seqNode != null) {
			ParameterControlBusEdge edge = graph.findEdge(seqNode, synNode);
			if(edge != null) {
				graph.removeEdge(edge);
				seqNode.getEventSequence().removeControlBus(edge.getParameterControlBus());
				
				ret = true;
			}
		}
		
		return ret; 
	}
}
