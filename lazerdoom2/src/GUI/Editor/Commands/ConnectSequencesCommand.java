package GUI.Editor.Commands;

import lazerdoom.Core;

import com.trolltech.qt.gui.QGraphicsScene;

import GUI.Editor.BaseEditorCommand;
import GUI.Item.BaseSequenceViewItem;
import GUI.Item.ConnectableSequenceInterface;
import GUI.Item.SequenceConnection;
import GUI.Item.SequenceItem;

public class ConnectSequencesCommand extends BaseEditorCommand {
	private QGraphicsScene scene;
	private ConnectableSequenceInterface src;
	private ConnectableSequenceInterface dst;
	
	public ConnectSequencesCommand(ConnectableSequenceInterface src, ConnectableSequenceInterface dst, QGraphicsScene scene) {
		this.src = src;
		this.dst = dst;
		this.scene = scene;
	} 
	
	@Override
	public boolean execute() {
		boolean ret = Core.getInstance().getSequenceController().connectSequences(src.getBaseSequence(), dst.getBaseSequence());
		
		if(ret) {
			this.scene.addItem(new SequenceConnection(src.getSequenceOutConnector(), dst.getSequenceInConnector()));
		}
		
		return ret;
	}

}
