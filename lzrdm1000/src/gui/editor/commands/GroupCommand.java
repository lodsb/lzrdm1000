package gui.editor.commands;

import gui.editor.BaseEditorCommand;
import gui.item.EditorCursor;
import gui.item.SequenceConnection;
import gui.item.SequenceItem;
import gui.item.SynthConnection;
import gui.item.SynthesizerItem;
import gui.item.editor.TouchableItemGroupItem;
import gui.multitouch.TouchItemInterface;
import gui.multitouch.TouchableGraphicsItem;
import gui.scene.editor.EditorScene;

import java.util.LinkedList;
import java.util.List;

import session.SessionHandler;

import lazerdoom.LzrDmObjectInterface;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.Qt.ItemSelectionMode;
import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.gui.QPainterPath;


public class GroupCommand extends BaseEditorCommand {

	//List<TouchableGraphicsItem> items;
	private LinkedList<LzrDmObjectInterface> addedGroupItems; // for serialization
	private LzrDmObjectInterface scn;
	private QPainterPath path;
	
	/*TouchableItemGroupItem group = null;

	TouchableItemGroupItem getGroup() {
		return this.group;
	}*/
	
	public GroupCommand(QPainterPath path, EditorScene scene) {
		this.path = path;
		this.scn = scene;
		//this.addedGroupItems = null;
	}
	
	

	@Override
	public boolean execute() {
		QGraphicsScene scene = (QGraphicsScene) scn;

		//List<QGraphicsItemInterface> groupedItems = scene.items(this.path, ItemSelectionMode.ContainsItemBoundingRect);
		/*		if(groupedItems.size() > 1 || addedGroupItems != null) {
		 */
		LinkedList<LzrDmObjectInterface> itemsToAdd = null;
		if(addedGroupItems == null) {
			itemsToAdd = new LinkedList<LzrDmObjectInterface>();
			List<QGraphicsItemInterface> groupedItems = scene.items(this.path, ItemSelectionMode.ContainsItemBoundingRect);
			if(groupedItems.size() > 1) {

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

					if(item instanceof TouchableGraphicsItem) {
						if(((TouchableGraphicsItem)item).belongsToGroup() != null) {
							return false;
						}
						
						if(item instanceof SequenceItem || item instanceof SynthesizerItem) {
							itemsToAdd.add((TouchableGraphicsItem) item);
						} else if(item instanceof TouchableItemGroupItem) {
							return false;
						}
					}
					
				}
				this.addedGroupItems = itemsToAdd;
			} else {
				return false;
			}
		} else {
			itemsToAdd = addedGroupItems;
		}

		if(itemsToAdd.size() > 1) {
			LinkedList<TouchableGraphicsItem> groupItems = new LinkedList<TouchableGraphicsItem>();
			for(LzrDmObjectInterface item: itemsToAdd) {
				groupItems.add((TouchableGraphicsItem)item);
			}
			TouchableItemGroupItem group = new TouchableItemGroupItem(groupItems);

			//System.out.println("To group:");
			/*for(QGraphicsItemInterface item: itemsToAdd) {
					System.out.println(item);
				}*/
			scene.addItem(group);
			scene.update();

			SessionHandler.getInstance().registerObject(group);
			//this.items = itemsToAdd;

			return true;
		} else {
			return false;
		}
	} 


}
