package gui.editor.commands;

import gui.editor.BaseEditorCommand;
import gui.multitouch.TouchableGraphicsItem;

import com.trolltech.qt.core.QPointF;

import lazerdoom.LzrDmObjectInterface;


public class MoveTouchableGraphicsItemCommand extends BaseEditorCommand {

	private LzrDmObjectInterface item;
	private QPointF pos; 
	
	public MoveTouchableGraphicsItemCommand(QPointF pos, TouchableGraphicsItem item) {
		this.item = item;
		this.pos = pos;
	}

	@Override
	public boolean execute() {
		if(item != null) {
			((TouchableGraphicsItem)this.item).setPosition(pos);
		
			return true;
		} 
		
		return false;
	}
}
