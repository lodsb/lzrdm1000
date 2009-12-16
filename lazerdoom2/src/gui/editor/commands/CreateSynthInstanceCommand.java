package gui.editor.commands;

import session.SessionHandler;
import synth.SynthInstance;
import gui.editor.BaseEditorCommand;
import gui.item.SynthesizerItem;
import gui.scene.editor.EditorScene;
import lazerdoom.Core;
import lazerdoom.LzrDmObjectInterface;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.gui.QGraphicsScene;


public class CreateSynthInstanceCommand extends BaseEditorCommand {
	
	private LzrDmObjectInterface scene;
	private QPointF pos; 
	
	public CreateSynthInstanceCommand(QPointF pos, EditorScene scene) {
		this.scene = scene;
		this.pos = pos;
	}

	@Override
	public boolean execute() {
		SynthesizerItem item = new SynthesizerItem();
		((EditorScene)this.scene).addItem(item);
		item.setPos(this.pos);
		
		SessionHandler.getInstance().registerObject(item);
		
		return true;
	}

}
