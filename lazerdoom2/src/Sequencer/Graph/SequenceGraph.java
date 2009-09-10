package Sequencer.Graph;

import java.awt.Dimension;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import javax.sound.midi.Sequencer;
import javax.swing.JFrame;

import Control.Types.DoubleType;
import Sequencer.EventPointsSequence;
import Sequencer.ParallelSequenceContainer;
import Sequencer.SequenceContainerInterface;
import Sequencer.SequenceInterface;
import Sequencer.SequencePlayer;
import Sequencer.SequencePlayerInterface;
import Sequencer.SequencerInterface;
import Sequencer.SequentialSequenceContainer;
import Sequencer.TestingSequencer;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.shortestpath.*;

public class SequenceGraph {
	private static int testID = 0;
	
	private static EventPointsSequence<DoubleType> makeEPS(SequencerInterface ts) {
		EventPointsSequence<DoubleType> e1 = new EventPointsSequence<DoubleType>(ts);
		e1.insert(new DoubleType(testID), 0);
		testID++;
		
		return e1;
	}
	
	//private HashMap<SequenceStructureNode, SequencePlayerInterface> sequencePlayerContainerList = new HashMap<SequenceStructureNode, SequencePlayerInterface>();
	
	public static void main(String[] argv) {
		SequenceGraph sg = new SequenceGraph();
		
		
		
		TestingSequencer ts = new TestingSequencer();
		
		ParallelSequenceContainer psc = new ParallelSequenceContainer(ts);
		
		EventPointsSequence<DoubleType> e1 = makeEPS(ts);
		EventPointsSequence<DoubleType> e2 = makeEPS(ts);
		EventPointsSequence<DoubleType> e3 = makeEPS(ts);
		EventPointsSequence<DoubleType> e4 = makeEPS(ts);
		
		EventPointsSequence<DoubleType> e5 = makeEPS(ts);
		EventPointsSequence<DoubleType> e6 = makeEPS(ts);
		EventPointsSequence<DoubleType> e7 = makeEPS(ts);
		EventPointsSequence<DoubleType> e8 = makeEPS(ts);
		EventPointsSequence<DoubleType> e9 = e7;
		
		SequencePlayer sp = new SequencePlayer(ts);
		
		sg.updateStructure(psc, ts);
		sg.connect(e1, e2);
		sg.updateStructure(psc, ts);
		sg.connect(e2, e3);
		sg.updateStructure(psc, ts);
		sg.connect(sp, e4);
		sg.connect(e4, e1);
		//sg.connect(sp, e4);
		sg.connect(e4, e5);
		sg.connect(e5, e6);
		sg.connect(e5, e7);
		sg.connect(e7, e8);
		//sg.remove(e5);
		//sg.disconnect(sp, e1);
		
		e1 = makeEPS(ts);
		e2 = makeEPS(ts);
		e3 = makeEPS(ts);
		e4 = makeEPS(ts);

		e5 = makeEPS(ts);
		e6 = makeEPS(ts);
		e7 = makeEPS(ts);
		e8 = makeEPS(ts);

		sg.updateStructure(psc, ts);
		
		sp = new SequencePlayer(ts);
		
		sg.connect(sp, e1);
		sg.updateStructure(psc, ts);
		sg.connect(e1, e2);
		sg.updateStructure(psc, ts);
		sg.connect(e2, e3);
		sg.updateStructure(psc, ts);
		sg.connect(e3, e4);		
		sg.connect(sp, e5);
		sg.connect(e5, e6);
		sg.connect(e5, e7);
		sg.connect(e7, e8);
		
		Layout<SequenceNode, Integer> layout = new ISOMLayout(sg.graph);
		layout.setSize(new Dimension(800,800)); // sets the initial size of the space
		BasicVisualizationServer<SequenceNode, Integer> vv =
			new BasicVisualizationServer<SequenceNode, Integer>(layout);
		vv.setPreferredSize(new Dimension(350,350)); //Sets the viewing area size
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());

		JFrame frame = new JFrame("Simple Graph View");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(vv);
		frame.pack();
		frame.setVisible(true);
		
		sg.updateStructure(psc, ts);
		
		System.out.println(sg.currentStructure);
		System.out.println(psc);
		System.exit(0);
	}

	private DirectedSparseGraph<SequenceNode, Integer> graph = new DirectedSparseGraph<SequenceNode, Integer>();
	private UnweightedShortestPath<SequenceNode, Integer> shortestPathAlg = new UnweightedShortestPath<SequenceNode, Integer>(graph);;
	private HashMap<SequenceInterface, SequenceNode> sequenceNodes = new HashMap<SequenceInterface, SequenceNode>();
	
	private int currentEdge = 0; 
	
	
	/*public SequenceGraph() {
		Layout<SequenceNode, Integer> layout = new ISOMLayout(this.graph);
		layout.setSize(new Dimension(800,800)); // sets the initial size of the space
		BasicVisualizationServer<SequenceNode, Integer> vv =
			new BasicVisualizationServer<SequenceNode, Integer>(layout);
		vv.setPreferredSize(new Dimension(350,350)); //Sets the viewing area size
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());

		JFrame frame = new JFrame("Simple Graph View");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(vv);
		frame.pack();
		frame.setVisible(true);
	}*/
	
	private LinkedList<SequenceStructureNode> currentStructure = null;
	
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
	
	public void clear() {
		this.graph = new DirectedSparseGraph<SequenceNode, Integer>();
		currentStructure = null;
	}
	
	public LinkedList<Pair<SequenceInterface>> remove(SequenceInterface si) {
		LinkedList<Pair<SequenceInterface>> connections = null;
		SequenceNode rm;
		
		
		if((rm = sequenceNodes.get(si)) != null) {
			connections = new LinkedList<Pair<SequenceInterface>>();
			LinkedList<Integer> edges = new LinkedList<Integer>();
			
			Collection<Integer> inEdges = graph.getInEdges(rm);
			
			if(inEdges != null) {
				for(Integer edge : inEdges) {
					SequenceNode sourceNode = graph.getSource(edge);
					if(sourceNode.getSequence() instanceof SequencePlayerInterface) {
						((SequencePlayerInterface)sourceNode.getSequence()).setSequence(null);
					}
					connections.add(new Pair<SequenceInterface>(sourceNode.getSequence(), rm.getSequence()));
					//graph.removeEdge(edge);
					edges.add(edge);
				}

			}
			Collection<Integer> outEdges = graph.getOutEdges(rm);
			
			if(outEdges != null) {
				for(Integer edge : outEdges) {
					connections.add(new Pair<SequenceInterface>(rm.getSequence(), graph.getSource(edge).getSequence()));
					//graph.removeEdge(edge);
					edges.add(edge);
				}
			}
			for(Integer edge: edges) {
				graph.removeEdge(edge);
			}
			
			
			graph.removeVertex(rm);
		}
		
		return connections;
	}
	
	public boolean connect(SequenceInterface source, SequenceInterface target) {
		SequenceNode src;
		SequenceNode tgt;
		
		if((src = sequenceNodes.get(source)) == null) {
			src = new SequenceNode(source);
			sequenceNodes.put(source, src);
		}
		
		if((tgt = sequenceNodes.get(target)) == null) {
			tgt = new SequenceNode(target);
			sequenceNodes.put(target, tgt);
		}
		
		return this.connect(src, tgt);
	}

	public boolean disconnect(SequenceInterface source, SequenceInterface target) {
		SequenceNode src;
		SequenceNode tgt;
		
		boolean ret = false;
		System.out.println(sequenceNodes.size()+" src "+sequenceNodes.get(source)+" tgt "+(sequenceNodes.get(target)));
		if((src = sequenceNodes.get(source)) != null && (tgt = sequenceNodes.get(target)) != null) { 
			ret = this.disconnect(src, tgt);
			System.out.println("DISC");
		}
		
		return ret;
	}
	
	private boolean connect(SequenceNode source, SequenceNode target) {
		boolean ret;
		
		if(source == target) {
			ret = false;
		} else {
			if(!graph.containsVertex(source)) {
				graph.addVertex(source);
			}
			
			if(!graph.containsVertex(target)) {
				graph.addVertex(target);
			}

			shortestPathAlg.reset(); 

			if(graph.inDegree(target) != 0) {
				ret = false;
			} else if(shortestPathAlg.getDistance(source, target) != null) {
				ret = false;
			} else {
				graph.addEdge(currentEdge++, source, target);
				ret = true;
			}
			
			// for sequence-players set the target to the sequence that has to be played
			/*SequenceInterface si;
			if((si = source.getSequence()) instanceof SequencePlayerInterface) {
				((SequencePlayerInterface)si).setSequence(target.getSequence());
			}*/
		}
		
		return ret;
	}
	
	private boolean disconnect(SequenceNode source, SequenceNode target) {
		boolean ret = false;
		Integer edge = graph.findEdge(source, target);
		
		if(edge != null) {
			if(source.getSequence() instanceof SequencePlayer) {
				((SequencePlayer) source.getSequence()).setSequence(null);
			}
			graph.removeEdge(edge);
			ret = true;
		}
		
		return ret;
	}
	
	public void updateStructure(SequenceContainerInterface rootContainer, SequencerInterface sequencer) {
		LinkedList<SequenceStructureNode> newStructure = sequenceStructureFromGraph();
		System.out.println(newStructure);
		buildSequenceContainers(rootContainer, newStructure, currentStructure, sequencer);
		System.out.println("GRAPH STRUCTURE: "+rootContainer);
		System.out.println("#######");
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
		
		System.out.println("list "+list+" size "+list.size());
		list = new LinkedList<SequenceInterface>();
		Collection<SequenceNode> nodes = graph.getVertices();
		for(SequenceNode node: nodes) {
			if(graph.inDegree(node) == 0 && (node.getSequence() instanceof SequencePlayerInterface)) {
				list.add(node.getSequence());
			
			}
		}
		rootContainer.updateStructure(list);
		currentStructure = newStructure;
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
			SequencePlayer player = null;
			LinkedList<SequenceInterface> sequences = new LinkedList<SequenceInterface>();
			
			for(SequenceStructureNode ssn: newStructure.list) {
				SequenceInterface s = buildSequenceContainersHelper(ssn, findEqualChildStructure(ssn, oldStructure), sequencer);
				if(!(s instanceof SequencePlayerInterface)) {
					sequences.add(s);
				} else {
					player = (SequencePlayer) s;
				}
			}
			
			if(oldStructure != null && oldStructure.container != null) {
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
			
			if(player != null) {
				player.setSequence(newStructure.container);
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
				
				SequenceStructureNode seqStruct = new SequenceStructureNode(node.getSequence(), false, true, sequentialNodes);
				// !!!
				sequentialNodes.add(new SequenceStructureNode(node.getSequence(), true, false, null));
					
				// always one successor
				for(SequenceNode currentNode: graph.getSuccessors(node)) {
					sequenceStructureFromGraphHelper(currentNode, false, sequentialNodes);
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
				currentNodes.add(new SequenceStructureNode(node.getSequence(), true, false, null));
				currentNodes.add(new SequenceStructureNode(node.getSequence(), false, false, parallelNodes));
			}
		}
	}
}
