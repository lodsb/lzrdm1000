package gui.editor.commands;

import gui.editor.BaseEditorCommand;
import gui.item.SequenceConnection;
import gui.item.SequencePlayerItem;
import gui.scene.editor.EditorScene;

import java.util.LinkedList;

import lazerdoom.Core;
import lazerdoom.LzrDmObjectInterface;

import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QGraphicsScene;


public class DeleteSequencePlayerCommand extends BaseEditorCommand {

	private LzrDmObjectInterface scene;
	private LzrDmObjectInterface sequencePlayer;
	
	public DeleteSequencePlayerCommand(SequencePlayerItem player, EditorScene scene) {
		this.scene = scene;
		this.sequencePlayer = player;
	}
	
	@Override
	public boolean execute() {
		boolean ret = false;
		
		ret = Core.getInstance().getSequenceController().removeBaseSequence(((SequencePlayerItem)this.sequencePlayer).getBaseSequence());
		
		if(ret) {
			((EditorScene)this.scene).removeItem((QGraphicsItemInterface) this.sequencePlayer);
			
			LinkedList<SequenceConnection> seqConnections = new LinkedList<SequenceConnection>();
			
			for(SequenceConnection out: ((SequencePlayerItem)this.sequencePlayer).getSequenceOutConnector().getConnections()) {
				((EditorScene)this.scene).removeItem(out);
				seqConnections.add(out);
			}
			
			for(SequenceConnection con: seqConnections) {
				con.remove();
			} 
		} else {
			((EditorScene)this.scene).removeItem((QGraphicsItemInterface) this.sequencePlayer);
			ret = true;
		}
		
		return ret;
	}

}
