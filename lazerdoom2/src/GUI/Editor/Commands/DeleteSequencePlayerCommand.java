package GUI.Editor.Commands;

import java.util.LinkedList;

import lazerdoom.Core;

import com.trolltech.qt.gui.QGraphicsScene;

import GUI.Editor.BaseEditorCommand;
import GUI.Item.SequencePlayerItem;
import GUI.Item.SequenceConnection;

public class DeleteSequencePlayerCommand extends BaseEditorCommand {

	private QGraphicsScene scene;
	private SequencePlayerItem sequencePlayer;
	
	public DeleteSequencePlayerCommand(SequencePlayerItem player, QGraphicsScene scene) {
		this.scene = scene;
		this.sequencePlayer = player;
	}
	
	@Override
	public boolean execute() {
		boolean ret = false;
		
		ret = Core.getInstance().getSequenceController().removeBaseSequence(this.sequencePlayer.getBaseSequence());
		
		if(ret) {
			this.scene.removeItem(this.sequencePlayer);
			
			LinkedList<SequenceConnection> seqConnections = new LinkedList<SequenceConnection>();
			
			for(SequenceConnection out: this.sequencePlayer.getSequenceOutConnector().getConnections()) {
				this.scene.removeItem(out);
				seqConnections.add(out);
			}
			
			for(SequenceConnection con: seqConnections) {
				con.remove();
			} 
		}
		
		return ret;
	}

}
