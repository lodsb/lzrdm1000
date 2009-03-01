package patch.scene;

import patch.NodeInterface;
import edu.uci.ics.jung.graph.impl.DirectedSparseVertex;;

public class PatchVertex extends DirectedSparseVertex {
	private NodeInterface node;
	
	public int _currentInDegree = 0;
	
	public PatchVertex(NodeInterface node) {
		this.node = node;
	}
	
	public NodeInterface getNode() {
		return node;
	}
}
