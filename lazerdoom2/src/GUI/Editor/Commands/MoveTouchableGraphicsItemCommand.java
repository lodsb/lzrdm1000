package GUI.Editor.Commands;

import com.trolltech.qt.core.QPointF;

import lazerdoom.LzrDmObjectInterface;
import GUI.Editor.BaseEditorCommand;
import GUI.Multitouch.TouchableGraphicsItem;


public class MoveTouchableGraphicsItemCommand extends BaseEditorCommand {

	private LzrDmObjectInterface item;
	private QPointF pos; 
	
	public MoveTouchableGraphicsItemCommand(QPointF pos, TouchableGraphicsItem item) {
		this.item = item;
		this.pos = pos;
	}

	@Override
	public boolean execute() {
		((TouchableGraphicsItem)this.item).setPosition(pos);
		
		return true;
	}
}
