package Sequencer.Graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import javax.sound.midi.Sequencer;

import Sequencer.ParallelSequenceContainer;
import Sequencer.SequenceContainerInterface;
import Sequencer.SequenceInterface;
import Sequencer.SequencePlayerInterface;
import Sequencer.SequencerInterface;
import Sequencer.SequentialSequenceContainer;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.algorithms.shortestpath.*;

public class SequenceGraph {

	DirectedSparseGraph<SequenceNode, Integer> graph = new DirectedSparseGraph<SequenceNode, Integer>();
	UnweightedShortestPath<SequenceNode, Integer> shortestPathAlg = new UnweightedShortestPath<SequenceNode, Integer>(graph);
	
	HashMap<SequenceInterface, SequenceContainerInterface> currentSequenceStructure = new HashMap<SequenceInterface, SequenceContainerInterface>();
	
	int currentEdge = 0; 
	
	LinkedList<SequenceStructureNode> currentStructure = null;
	
	private class SequenceStructureNode {
		SequenceInterface si;
		boolean isNode;
		boolean isSequential;
		LinkedList<SequenceStructureNode> list;
		
		SequenceContainerInterface container; 
		
		SequenceStructureNode(SequenceInterface si, boolean isNode, boolean isSequential, LinkedList<SequenceStructureNode> list) {
			this.si = si;
			this.isNode = isNode;
			this.isSequential = isSequential;
			this.list = list;
		}
	}
	
	public boolean connect(SequenceNode source, SequenceNode target) {
		boolean ret;
		
		if(shortestPathAlg.getDistance(source, target) != null) {
			ret = false;
		} else {
			if(!graph.containsVertex(source)) {
				graph.addVertex(source);
			}
			
			if(!graph.containsVertex(target)) {
				graph.addVertex(target);
			}
			
			if(graph.inDegree(target) != 0) {
				ret = false;
			} else {
				graph.addEdge(currentEdge++, source, target);
				ret = true;
			}
			
			// for sequence-players set the target to the sequence that has to be played
			SequenceInterface si;
			if((si = source.getSequence()) instanceof SequencePlayerInterface) {
				((SequencePlayerInterface)si).setSequence(target.getSequence());
			}
		}
		
		return ret;
	}
	
	public void disconnect(SequenceNode source, SequenceNode target) {
		Integer edge = graph.findEdge(source, target);
		
		if(edge != null) {
			graph.removeEdge(edge);
		}
	}
	
	public void updateStructure(SequenceContainerInterface rootContainer, SequencerInterface sequencer) {
		LinkedList<SequenceStructureNode> newStructure = sequenceStructureFromGraph();
		buildSequenceContainers(rootContainer, newStructure, currentStructure, sequencer);
	}
	
	private void buildSequenceContainers(SequenceContainerInterface rootContainer, LinkedList<SequenceStructureNode> newStructure, LinkedList<SequenceStructureNode> oldStructure, SequencerInterface sequencer) {
		
		LinkedList<SequenceInterface> list = new LinkedList<SequenceInterface>();
		
		for(SequenceStructureNode newNode: newStructure) {
			SequenceStructureNode oldNode = null;
			if(oldStructure != null) {
				for(SequenceStructureNode node: oldStructure) {
					if(node.si == newNode.si) {
						oldNode = node;
					}
				}
			}
			
			list.add(buildSequenceContainersHelper(newNode, oldNode, sequencer));
		}
		
		rootContainer.updateStructure(list);
	}
	
	private SequenceStructureNode findEqualChildStructure(SequenceStructureNode child, SequenceStructureNode oldStructure) {
		
		
		
		if(oldStructure == null) {
			return null;
		}
		
		SequenceStructureNode ret = null;
		
		if(child.isNode) {
			if(!oldStructure.isNode) {
				for(SequenceStructureNode node: oldStructure.list) {
					if(child.si == node.si) {
						ret = node;
						break;
					}
				}
			}
		} else if(child.isSequential) {
			// search for a sequence with a common child
			if(!oldStructure.isNode) {
				outer: for(SequenceStructureNode node: oldStructure.list) {
					if(!node.isNode && node.isSequential) {
						for(SequenceStructureNode nodeChild: node.list) {
							for(SequenceStructureNode newNodeChild: child.list ) {
								if(newNodeChild.si == nodeChild.si) {
									ret = node;
									break outer;
								}
							}
						}
					}
				}
	
			}
		} else {
			if(!oldStructure.isNode) {
				outer: for(SequenceStructureNode node: oldStructure.list) {
					if(!node.isNode && !node.isSequential) {
						for(SequenceStructureNode nodeChild: node.list) {
							for(SequenceStructureNode newNodeChild: child.list ) {
								if(newNodeChild.si == nodeChild.si) {
									ret = node;
									break outer;
								}
							}
						}
					}
				}
	
			}
			
		}
		
		return ret;
	}
	
	private SequenceInterface buildSequenceContainersHelper(SequenceStructureNode newStructure, SequenceStructureNode oldStructure, SequencerInterface sequencer) {
		SequenceInterface ret;
		
		if(!newStructure.isNode) {
			LinkedList<SequenceInterface> sequences = new LinkedList<SequenceInterface>();
			
			for(SequenceStructureNode ssn: newStructure.list) {
				sequences.add(buildSequenceContainersHelper(ssn, findEqualChildStructure(ssn, oldStructure), sequencer));
			}
			
			if(oldStructure != null) {
				oldStructure.container.updateStructure(sequences);
				newStructure.container = oldStructure.container;
			} else {
				if(newStructure.isSequential) {
					SequentialSequenceContainer container = new SequentialSequenceContainer(sequencer);
					
					for(SequenceInterface si: sequences) {
						container.appendSequence(si);
					}
					
					newStructure.container = container;
				
				} else {
					ParallelSequenceContainer container = new ParallelSequenceContainer(sequencer);
					
					for(SequenceInterface si: sequences) {
						container.appendSequence(si);
					}
					
					newStructure.container = container;
				}
			}
			
			ret = newStructure.container;
		} else {
			ret = newStructure.si;
		}
		
		return ret;
	}
	
	private LinkedList<SequenceStructureNode> sequenceStructureFromGraph() {
		LinkedList<SequenceStructureNode> list = new LinkedList<SequenceStructureNode>();
		
		Collection<SequenceNode> nodes = graph.getVertices();
		
		//find root-nodes
		for(SequenceNode node: nodes) {
			if(graph.inDegree(node) == 0 && (node.getSequence() instanceof SequencePlayerInterface)) {
				sequenceStructureFromGraphHelper(node, true, list);
			}
		}
		
		return list;
	}
	
	private void sequenceStructureFromGraphHelper(SequenceNode node, boolean isRoot, LinkedList<SequenceStructureNode> currentNodes) {
		if(graph.outDegree(node) == 0) {
			currentNodes.add(new SequenceStructureNode(node.getSequence(), true, false, null));
		
		} else if(graph.outDegree(node) == 1) {
			if(isRoot) {
				LinkedList<SequenceStructureNode> sequentialNodes = new LinkedList<SequenceStructureNode>();
				
				// !!!
				sequentialNodes.add(new SequenceStructureNode(node.getSequence(), true, false, null));
				
				// always one successor
				for(SequenceNode currentNode: graph.getSuccessors(node)) {
					sequenceStructureFromGraphHelper(currentNode, false, currentNodes);
				}
				
				currentNodes.add(new SequenceStructureNode(node.getSequence(), false, true, sequentialNodes));
				
			} else {
				currentNodes.add(new SequenceStructureNode(node.getSequence(), true, false, null));
				
				// always one successor
				for(SequenceNode currentNode: graph.getSuccessors(node)) {
					sequenceStructureFromGraphHelper(currentNode, false, currentNodes);
				}
			}
		} else {
			LinkedList<SequenceStructureNode> parallelNodes = new LinkedList<SequenceStructureNode>();
			
			for(SequenceNode currentNode: graph.getSuccessors(node)) {
				sequenceStructureFromGraphHelper(currentNode, true, parallelNodes);
			}
			
			if(isRoot) {
				// sequential container
				LinkedList<SequenceStructureNode> sequentialNodes = new LinkedList<SequenceStructureNode>();
				sequentialNodes.add(new SequenceStructureNode(node.getSequence(), true, false, null));
				sequentialNodes.add(new SequenceStructureNode(node.getSequence(), false, false, parallelNodes));
				
				currentNodes.add(new SequenceStructureNode(node.getSequence(), false, true, sequentialNodes));
			} else {
				currentNodes.add(new SequenceStructureNode(node.getSequence(), false, false, parallelNodes));
			}
		}
	}
}
