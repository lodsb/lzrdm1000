package gui.editor.commands;

import gui.editor.BaseEditorCommand;
import gui.item.SequenceConnection;
import gui.item.SequenceItem;
import gui.item.SynthConnection;
import gui.item.SynthOutConnector;
import gui.scene.editor.EditorScene;

import java.util.LinkedList;

import sequencer.EventSequenceInterface;

import lazerdoom.Core;
import lazerdoom.LzrDmObjectInterface;

import com.trolltech.qt.gui.QGraphicsScene;


public class DeleteSequenceItemCommand extends BaseEditorCommand {

	private LzrDmObjectInterface scn;
	private LzrDmObjectInterface sqi;
	
	public DeleteSequenceItemCommand(SequenceItem item, EditorScene scene) {
		this.scn = scene;
		this.sqi = item;
	}
	
	@Override
	public boolean execute() {
		QGraphicsScene scene = (EditorScene)this.scn;
		SequenceItem sequenceItem = (SequenceItem) sqi;
		System.out.println(sqi+" OOOO");
		System.out.println(this.scn+" OOOO");
		
		System.out.println("DELETE SEQ ITEM");
		boolean ret = false;
			ret = Core.getInstance().getSequenceController().removeBaseSequence(sequenceItem.getBaseSequence());
			if(ret) {
				scene.removeItem(sequenceItem);
				sequenceItem.undockAllCursors();
				
				LinkedList<SequenceConnection> seqConnections = new LinkedList<SequenceConnection>();
				
				for(SequenceConnection in: sequenceItem.getSequenceInConnector().getConnections()) {
					scene.removeItem(in);
					seqConnections.add(in);
				}
				
				for(SequenceConnection out: sequenceItem.getSequenceOutConnector().getConnections()) {
					scene.removeItem(out);
					seqConnections.add(out);
				}
				
				for(SequenceConnection con: seqConnections) {
					con.remove();
				} 
				
				if(sequenceItem.getBaseSequence() instanceof EventSequenceInterface) {
					Core.getInstance().getSynthController().remove((EventSequenceInterface) sequenceItem.getBaseSequence());
					
					LinkedList<SynthConnection> synConnections = new LinkedList<SynthConnection>();
					for(SynthConnection out: sequenceItem.getSynthOutConnectors().get(0).getConnections()) {
						scene.removeItem(out);
						synConnections.add(out);
					}
					
					for(SynthConnection con: synConnections) {
						con.remove();
					}
					
				}
			} else /*if(!this.sequenceItem.isInitialized()) */{
				scene.removeItem(sequenceItem);
				sequenceItem.undockAllCursors();
				
				ret = true;
			} 
		return ret;
	}

}
