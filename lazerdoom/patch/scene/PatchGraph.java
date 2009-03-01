package patch.scene;

import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;

import patch.NodeInterface;

import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
import edu.uci.ics.jung.graph.impl.DirectedSparseGraph;
import edu.uci.ics.jung.utils.UserDataContainer;

public class PatchGraph extends DirectedSparseGraph {
	
	private Stack<Vertex> vertexStack = new Stack<Vertex>();
	
	private int currentInDegreeKey = 0xDEADBEEF;
	
	public boolean edgeAddsCircle(Vertex v1, Vertex v2) {
		Set<Edge> set = v2.findEdgeSet(v1);
		return set.isEmpty();
	}

	public void addEdge(Vertex v1, Vertex v2) {
		DirectedSparseEdge e = new DirectedSparseEdge(v1,v2);
		
		this.addEdge(e);
	}
	
	public LinkedList<NodeInterface> topologicalSortOfNodes() {
		LinkedList<NodeInterface> ret = new LinkedList<NodeInterface>();
		
		Set<PatchVertex> vertices = this.getVertices();
		Stack<PatchVertex> workingSet = new Stack<PatchVertex>();
		
		for(PatchVertex vertex: vertices) {
			if(vertex.inDegree() == 0) {
				workingSet.add(vertex);
			} 
			
			vertex._currentInDegree = vertex.inDegree();
		}
		
		PatchVertex currentVertex;
		while(!workingSet.isEmpty()) {
			currentVertex = workingSet.pop();
			
			NodeInterface node = currentVertex.getNode();
			
			if(node.isReadyForProcessing()) {
				ret.add(currentVertex.getNode());

				vertices = currentVertex.getSuccessors();

				for(PatchVertex vertex: vertices) {
					vertex._currentInDegree--;

					if(vertex._currentInDegree == 0) {
						workingSet.push(vertex);
					}
				}
			}
			
		}
		
		return ret;
		
	}
}
