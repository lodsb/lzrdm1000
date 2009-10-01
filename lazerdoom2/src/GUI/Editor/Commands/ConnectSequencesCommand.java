package GUI.Editor.Commands;

import lazerdoom.Core;
import lazerdoom.LzrDmObjectInterface;

import com.trolltech.qt.gui.QGraphicsScene;

import GUI.Editor.BaseEditorCommand;
import GUI.Item.BaseSequenceViewItem;
import GUI.Item.ConnectableSequenceInterface;
import GUI.Item.SequenceConnection;
import GUI.Item.SequenceItem;
import GUI.Scene.Editor.EditorScene;

public class ConnectSequencesCommand extends BaseEditorCommand {
	private LzrDmObjectInterface scene;
	private LzrDmObjectInterface src;
	private LzrDmObjectInterface dst;
	
	public ConnectSequencesCommand(ConnectableSequenceInterface src, ConnectableSequenceInterface dst, EditorScene scene) {
		this.src = src;
		this.dst = dst;
		this.scene = scene;
	} 
	
	@Override
	public boolean execute() {
		boolean ret = Core.getInstance().getSequenceController().connectSequences(((ConnectableSequenceInterface)src).getBaseSequence(), ((ConnectableSequenceInterface)dst).getBaseSequence());
		
		if(ret) {
			((EditorScene)this.scene).addItem(new SequenceConnection(((ConnectableSequenceInterface)src).getSequenceOutConnector(), ((ConnectableSequenceInterface)dst).getSequenceInConnector()));
		}
		
		return ret;
	}

}
