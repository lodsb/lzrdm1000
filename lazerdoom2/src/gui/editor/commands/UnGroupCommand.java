package gui.editor.commands;

import gui.editor.BaseEditorCommand;
import gui.item.Editor.TouchableItemGroupItem;
import gui.multitouch.TouchableGraphicsItem;
import lazerdoom.LzrDmObjectInterface;

import com.trolltech.qt.gui.QGraphicsItem;
import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QGraphicsScene;


public class UnGroupCommand extends BaseEditorCommand {
	private LzrDmObjectInterface groupItem;
	
	public UnGroupCommand(TouchableItemGroupItem item) {
		this.groupItem = item;
	}
	@Override
	public boolean execute() {

		((TouchableItemGroupItem)this.groupItem).ungroupItems();
		((TouchableItemGroupItem)groupItem).scene().removeItem((TouchableItemGroupItem)groupItem);
		
		return true;
	}

}
