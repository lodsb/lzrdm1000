package gui.editor.commands;

import gui.editor.BaseEditorCommand;
import gui.item.BaseSequenceViewItem;
import gui.item.SequenceConnection;
import gui.scene.editor.EditorScene;
import lazerdoom.Core;
import lazerdoom.LzrDmObjectInterface;

import com.trolltech.qt.gui.QGraphicsScene;


public class DeleteSequenceConnectionCommand extends BaseEditorCommand {

	private LzrDmObjectInterface scene;
	private LzrDmObjectInterface connection;
	
	public DeleteSequenceConnectionCommand(SequenceConnection item, EditorScene scene) {
		this.scene = scene;
		this.connection = item;
	}

	@Override
	public boolean execute() {
		boolean ret = false;
		
		ret = Core.getInstance().getSequenceController().disconnectSequences(((BaseSequenceViewItem)((SequenceConnection)this.connection).getSource().getParent()).getBaseSequence(), ((BaseSequenceViewItem)((SequenceConnection)this.connection).getDestination().getParent()).getBaseSequence());
		
		if(ret) {
			((EditorScene)this.scene).removeItem((SequenceConnection)this.connection);
			((SequenceConnection)this.connection).remove();
		}
			
		return ret;
	}

}
