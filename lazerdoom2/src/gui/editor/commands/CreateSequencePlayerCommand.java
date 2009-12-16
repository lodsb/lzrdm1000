package gui.editor.commands;

import sequencer.EventPointsSequence;
import sequencer.SequencePlayer;
import session.SessionHandler;
import gui.editor.BaseEditorCommand;
import gui.item.SequenceItem;
import gui.item.SequencePlayerItem;
import gui.scene.editor.EditorScene;
import lazerdoom.Core;
import lazerdoom.LzrDmObjectInterface;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.gui.QGraphicsScene;

import control.types.DoubleType;


public class CreateSequencePlayerCommand extends BaseEditorCommand {

	private QPointF pos;
	private LzrDmObjectInterface scene;
	
	public CreateSequencePlayerCommand(QPointF position, EditorScene scene) {
		System.out.println("create sequence player");
		this.pos = position;
		this.scene = scene;
	}
	
	@Override
	public boolean execute() {
		Core core = Core.getInstance();
		
		SequencePlayer sequencePlayer = core.getSequenceController().createSequencePlayer();
		SequencePlayerItem item = new SequencePlayerItem(sequencePlayer);
		
		((EditorScene)this.scene).addItem(item);
		item.setPos(pos);
		
		SessionHandler.getInstance().registerObject(item);
		
		return true;
	}

}
