package patch.scene;

import java.util.HashMap;

import patch.NodeInterface;

import com.trolltech.qt.QSignalEmitter.Signal0;
import com.trolltech.qt.core.QObject;

import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.impl.DirectedSparseVertex;

public class PatchGraphController extends QObject {
	private PatchGraph patchGraph;
	
	private HashMap<Integer, Vertex> moduleIdMap = new HashMap<Integer, Vertex>();
	
	private int moduleIdCntr = 0;
	
	public Signal0 graphChanged = new Signal0();
	
	private PatchRunner patchRunner = new PatchRunner();
	
	PatchGraphController(PatchGraph graph) {
		super();
		
		this.patchGraph = graph;
	}
	
	PatchGraphController() {
		super();
		
		this.patchGraph = new PatchGraph();
	}
	
	private void graphChanged() {
		graphChanged.emit();
		
		patchRunner.setProcessNodes(patchGraph.topologicalSortOfNodes());
	}
	
	public int addNode(NodeInterface node) {
		patchGraph.addVertex(new PatchVertex(node));
		
		node.setID(moduleIdCntr);
		
		graphChanged();
		
		return moduleIdCntr++;
	}
	
	public void connect(int srcModuleID, int dstModuleID) throws Exception {
		Vertex v1 = moduleIdMap.get(srcModuleID);
		Vertex v2 = moduleIdMap.get(dstModuleID);
		
		if(v1 == null || v2 == null) {
			throw new Exception("Connect src/dst unknown");
		} 
		
		if(patchGraph.edgeAddsCircle(v1, v2)) {
			throw new Exception("Connection is cyclic");
		}
		
		patchGraph.addEdge(v1, v2);
		
		graphChanged();
	}
	
}
