package GUI.Editor.Commands;

import lazerdoom.Core;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.gui.QGraphicsScene;

import GUI.Editor.BaseEditorCommand;
import GUI.Item.SynthesizerItem;
import Synth.SynthInstance;

public class CreateSynthInstanceCommand extends BaseEditorCommand {
	
	private QGraphicsScene scene;
	private QPointF pos; 
	
	public CreateSynthInstanceCommand(QPointF pos, QGraphicsScene scene) {
		this.scene = scene;
		this.pos = pos;
	}

	@Override
	public boolean execute() {
		SynthesizerItem item = new SynthesizerItem();
		this.scene.addItem(item);
		item.setPos(this.pos);
		
		return false;
	}

}
