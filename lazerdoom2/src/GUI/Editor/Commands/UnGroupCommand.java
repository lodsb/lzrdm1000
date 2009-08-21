package GUI.Editor.Commands;

import com.trolltech.qt.gui.QGraphicsItem;
import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QGraphicsScene;

import GUI.Editor.BaseEditorCommand;
import GUI.Item.Editor.TouchableItemGroupItem;
import GUI.Multitouch.TouchableGraphicsItem;

public class UnGroupCommand extends BaseEditorCommand {
	TouchableItemGroupItem groupItem;
	
	public UnGroupCommand(TouchableItemGroupItem item) {
		this.groupItem = item;
	}
	@Override
	public boolean execute() {
		for(QGraphicsItemInterface item: this.groupItem.getItemGroup()) {
			// fix the update of the group in the item, for now leave it as it to circumvent cloning the list 
			// so the original one won't be modified concurrently
			this.groupItem.removeFromGroup(item);
		}
		
		QGraphicsScene scene = this.groupItem.scene();
		if(scene != null) {
			scene.removeItem(this.groupItem);
		}
		
		return true;
	}

}
