package GUI.Editor.Commands;

import java.util.LinkedList;
import java.util.List;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.Qt.ItemSelectionMode;
import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.gui.QPainterPath;

import GUI.Editor.BaseEditorCommand;
import GUI.Item.EditorCursor;
import GUI.Item.SequenceConnection;
import GUI.Item.SequenceItem;
import GUI.Item.SynthConnection;
import GUI.Item.SynthesizerItem;
import GUI.Item.Editor.TouchableItemGroupItem;
import GUI.Multitouch.TouchItemInterface;
import GUI.Multitouch.TouchableGraphicsItem;

public class GroupCommand extends BaseEditorCommand {

	List<TouchableGraphicsItem> items;
	QGraphicsScene scene;
	QPainterPath path;
	TouchableItemGroupItem group = null;

	TouchableItemGroupItem getGroup() {
		return this.group;
	}
	
	public GroupCommand(QPainterPath path, QGraphicsScene scene) {
		this.path = path;
		this.scene = scene;
	}
	
	

	@Override
	public boolean execute() {
		List<QGraphicsItemInterface> groupedItems = scene.items(this.path, ItemSelectionMode.ContainsItemBoundingRect);
		if(groupedItems.size() > 1) {

			LinkedList<TouchableGraphicsItem> itemsToAdd = new LinkedList<TouchableGraphicsItem>();

			for(QGraphicsItemInterface item: groupedItems) {
				// filter everything that does not belong to the group. maybe something like this should be handled in a more general way
				/*if(!(item instanceof TouchItemInterface) || 
						item instanceof GUI.Item.TouchPointCursor ||
						item instanceof EditorCursor || 
						item instanceof SequenceConnection || 
						item instanceof SynthConnection || 
						(item.parentItem() != null && !items.contains(item.parentItem())))
					continue;

				for(QGraphicsItemInterface child :item.childItems()) {
					itemsToAdd.add(child);
				}*/
				
				if(item instanceof SequenceItem || item instanceof SynthesizerItem) {
					itemsToAdd.add((TouchableGraphicsItem) item);
				}
			}

			if(itemsToAdd.size() > 1) {
				this.group = new TouchableItemGroupItem(itemsToAdd);

				System.out.println("To group:");
				for(QGraphicsItemInterface item: itemsToAdd) {
					System.out.println(item);
				}
				this.scene.addItem(group);
				this.scene.update();

				this.items = itemsToAdd;
				
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}
