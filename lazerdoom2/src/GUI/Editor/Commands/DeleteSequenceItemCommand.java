package GUI.Editor.Commands;

import java.util.LinkedList;

import lazerdoom.Core;

import com.trolltech.qt.gui.QGraphicsScene;

import GUI.Editor.BaseEditorCommand;
import GUI.Item.SequenceConnection;
import GUI.Item.SequenceItem;
import GUI.Item.SynthConnection;
import GUI.Item.SynthOutConnector;
import Sequencer.EventSequenceInterface;

public class DeleteSequenceItemCommand extends BaseEditorCommand {

	private QGraphicsScene scene;
	private SequenceItem sequenceItem;
	
	public DeleteSequenceItemCommand(SequenceItem item, QGraphicsScene scene) {
		this.scene = scene;
		this.sequenceItem = item;
	}
	
	@Override
	public boolean execute() {
		System.out.println("DELETE SEQ ITEM");
		boolean ret = false;
			ret = Core.getInstance().getSequenceController().removeBaseSequence(this.sequenceItem.getBaseSequence());
			if(ret) {
				this.scene.removeItem(this.sequenceItem);
				this.sequenceItem.undockAllCursors();
				
				LinkedList<SequenceConnection> seqConnections = new LinkedList<SequenceConnection>();
				
				for(SequenceConnection in: sequenceItem.getSequenceInConnector().getConnections()) {
					this.scene.removeItem(in);
					seqConnections.add(in);
				}
				
				for(SequenceConnection out: sequenceItem.getSequenceOutConnector().getConnections()) {
					this.scene.removeItem(out);
					seqConnections.add(out);
				}
				
				for(SequenceConnection con: seqConnections) {
					con.remove();
				} 
				
				if(this.sequenceItem.getBaseSequence() instanceof EventSequenceInterface) {
					Core.getInstance().getSynthController().remove((EventSequenceInterface) this.sequenceItem.getBaseSequence());
					
					LinkedList<SynthConnection> synConnections = new LinkedList<SynthConnection>();
					for(SynthConnection out: this.sequenceItem.getSynthOutConnectors().get(0).getConnections()) {
						this.scene.removeItem(out);
						synConnections.add(out);
					}
					
					for(SynthConnection con: synConnections) {
						con.remove();
					}
					
				}
			} else /*if(!this.sequenceItem.isInitialized()) */{
				this.scene.removeItem(this.sequenceItem);
				this.sequenceItem.undockAllCursors();
			} 
		return ret;
	}

}
