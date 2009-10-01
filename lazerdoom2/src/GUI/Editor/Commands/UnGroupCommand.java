package GUI.Editor.Commands;

import lazerdoom.LzrDmObjectInterface;

import com.trolltech.qt.gui.QGraphicsItem;
import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QGraphicsScene;

import GUI.Editor.BaseEditorCommand;
import GUI.Item.Editor.TouchableItemGroupItem;
import GUI.Multitouch.TouchableGraphicsItem;

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
