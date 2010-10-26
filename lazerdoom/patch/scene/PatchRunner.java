package patch.scene;

import java.util.ArrayList;
import java.util.LinkedList;

import com.trolltech.qt.QThread;
import com.trolltech.qt.core.QObject;
import patch.NodeInterface;

public class PatchRunner extends QObject implements Runnable {

	private volatile ArrayList<NodeInterface> processNodes;
	
	private QThread thread;
	
	PatchRunner() {
		thread = new QThread(this);
		this.moveToThread(thread);
		thread.start();
		
		System.out.println("PatchRunner-Thread started");
	}
	
	public void setProcessNodes(LinkedList<NodeInterface> list) {
		//FIXME: do the buffershit
		
		ArrayList<NodeInterface> currentProcessNodes = new ArrayList<NodeInterface>(); 
		
		for(NodeInterface node: list) {
			currentProcessNodes.add(node);
		}
		
		processNodes = currentProcessNodes;
		
	}
	
	public void run() {
		ArrayList<NodeInterface> currentProcessNodes = processNodes;
		
		while(true) {
			if(currentProcessNodes != null) {
				for(NodeInterface node: currentProcessNodes) {
					node.process();
				}
			} 
		}
		
	}

}
