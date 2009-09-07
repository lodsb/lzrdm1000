package GUI.Editor.Commands;

import lazerdoom.Core;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.gui.QGraphicsScene;

import Control.Types.DoubleType;
import GUI.Editor.BaseEditorCommand;
import GUI.Item.SequenceItem;
import Sequencer.EventPointsSequence;

public class CreateSequenceCommand extends BaseEditorCommand {

	private QPointF pos;
	private QGraphicsScene scene;
	
	public CreateSequenceCommand(QPointF position, QGraphicsScene scene) {
		System.out.println("create sequence");
		this.pos = position;
		this.scene = scene;
	}
	
	@Override
	public boolean execute() {
		Core core = Core.getInstance();
		
		EventPointsSequence<DoubleType> sequence = core.getSequenceController().createDoubleTypeEventPointsSequence();
		SequenceItem item = new SequenceItem(sequence);
		
		this.scene.addItem(item);
		item.setPos(pos);
		
		return true;
	}

}
