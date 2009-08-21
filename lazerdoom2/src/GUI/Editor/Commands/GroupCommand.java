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
import GUI.Item.SynthConnection;
import GUI.Item.Editor.TouchableItemGroupItem;
import GUI.Multitouch.TouchItemInterface;

public class GroupCommand extends BaseEditorCommand {
	private double frameSize = 20.0;

	List<QGraphicsItemInterface> items;
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
		this.items = scene.items(this.path, ItemSelectionMode.ContainsItemBoundingRect);
		if(items.size() > 1) {
			QRectF boundingRect;

			LinkedList<QGraphicsItemInterface> itemsToAdd = new LinkedList<QGraphicsItemInterface>();

			double minX = Double.MAX_VALUE;
			double minY = Double.MAX_VALUE;

			double maxX = Double.MIN_VALUE;
			double maxY = Double.MIN_VALUE;

			for(QGraphicsItemInterface item: items) {
				// filter everything that does not belong to the group. maybe something like this should be handled in a more general way
				if(!(item instanceof TouchItemInterface) || 
						item instanceof GUI.Item.TouchPointCursor ||
						item instanceof EditorCursor || 
						item instanceof SequenceConnection || 
						item instanceof SynthConnection || 
						(item.parentItem() != null && !items.contains(item.parentItem())))
					continue;

				for(QGraphicsItemInterface child :item.childItems()) {
					itemsToAdd.add(child);
				}
				itemsToAdd.add(item);
			}

			if(itemsToAdd.size() > 1) {
				for(QGraphicsItemInterface item: itemsToAdd) {				
					QPointF topLeft = item.mapToScene(item.boundingRect()).boundingRect().topLeft();
					QPointF bottomRight = item.mapToScene(item.boundingRect()).boundingRect().bottomRight();
					if(minX > topLeft.x()) {
						minX = topLeft.x();
					}
					if(minY > topLeft.y()) {
						minY = topLeft.y();
					}

					if(maxX < bottomRight.x()) {
						maxX = bottomRight.x();
					}
					if(maxY < bottomRight.y()) {
						maxY = bottomRight.y();
					}
				}

				double width = maxX - minX + 2*frameSize;
				double height = maxY - minY + 2*frameSize;

				boundingRect = new QRectF(minX-this.frameSize,minY-this.frameSize, width, height);

				group = new TouchableItemGroupItem(boundingRect);

				System.out.println("To group:");
				for(QGraphicsItemInterface item: itemsToAdd) {
					group.addToGroup(item);
					System.out.println(item);
				}
				System.out.println(boundingRect);
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
