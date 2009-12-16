package gui.editor.commands;

import sequencer.EventPointsSequence;
import session.SessionHandler;
import gui.editor.BaseEditorCommand;
import gui.item.SequenceItem;
import gui.scene.editor.EditorScene;
import lazerdoom.Core;
import lazerdoom.LzrDmObjectInterface;

import com.thoughtworks.xstream.XStream;
import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.gui.QGraphicsScene;

import control.types.DoubleType;


public class CreateSequenceCommand extends BaseEditorCommand {

	private QPointF pos;
	private LzrDmObjectInterface scene;
	
	public CreateSequenceCommand(QPointF position, EditorScene scene) {
		System.out.println("create sequence");
		this.pos = position;
		this.scene = scene;
	}
	
	@Override
	public boolean execute() {
		Core core = Core.getInstance();
		
		/*EventPointsSequence<DoubleType> sequence = core.getSequenceController().createDoubleTypeEventPointsSequence();
		sequence.setLength(100);*/
		SequenceItem item = new SequenceItem();
		
		((EditorScene)this.scene).addItem(item);
		item.setPos(pos);
		
		SessionHandler.getInstance().registerObject(item);
		
		return true;
	}

}
