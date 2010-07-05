package gui.editor.commands;

import gui.editor.BaseEditorCommand;
import gui.item.BaseSequenceViewItem;
import gui.item.ConnectableSequenceInterface;
import gui.item.SequenceConnection;
import gui.item.SequenceItem;
import gui.scene.editor.EditorScene;
import lazerdoom.Core;
import lazerdoom.LzrDmObjectInterface;

import com.trolltech.qt.gui.QGraphicsScene;


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
