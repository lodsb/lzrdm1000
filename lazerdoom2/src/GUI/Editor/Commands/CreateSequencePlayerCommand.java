package GUI.Editor.Commands;

import lazerdoom.Core;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.gui.QGraphicsScene;

import Control.Types.DoubleType;
import GUI.Editor.BaseEditorCommand;
import GUI.Item.SequenceItem;
import GUI.Item.SequencePlayerItem;
import Sequencer.EventPointsSequence;
import Sequencer.SequencePlayer;

public class CreateSequencePlayerCommand extends BaseEditorCommand {

	private QPointF pos;
	private QGraphicsScene scene;
	
	public CreateSequencePlayerCommand(QPointF position, QGraphicsScene scene) {
		System.out.println("create sequence player");
		this.pos = position;
		this.scene = scene;
	}
	
	@Override
	public boolean execute() {
		Core core = Core.getInstance();
		
		SequencePlayer sequencePlayer = core.getSequenceController().createSequencePlayer();
		SequencePlayerItem item = new SequencePlayerItem(sequencePlayer);
		
		this.scene.addItem(item);
		item.setPos(pos);
		
		return true;
	}

}
