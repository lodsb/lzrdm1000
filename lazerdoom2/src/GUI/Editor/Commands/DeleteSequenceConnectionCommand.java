package GUI.Editor.Commands;

import lazerdoom.Core;

import com.trolltech.qt.gui.QGraphicsScene;

import GUI.Editor.BaseEditorCommand;
import GUI.Item.BaseSequenceViewItem;
import GUI.Item.SequenceConnection;
import GUI.Scene.Editor.EditorScene;

public class DeleteSequenceConnectionCommand extends BaseEditorCommand {

	private QGraphicsScene scene;
	private SequenceConnection connection;
	
	public DeleteSequenceConnectionCommand(SequenceConnection item, QGraphicsScene scene) {
		this.scene = scene;
		this.connection = item;
	}

	@Override
	public boolean execute() {
		boolean ret = false;
		
		ret = Core.getInstance().getSequenceController().disconnectSequences(((BaseSequenceViewItem)this.connection.getSource().getParent()).getBaseSequence(), ((BaseSequenceViewItem)this.connection.getDestination().getParent()).getBaseSequence());
		
		if(ret) {
			this.scene.removeItem(this.connection);
			this.connection.remove();
		}
			
		return ret;
	}

}
