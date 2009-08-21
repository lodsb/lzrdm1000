package GUI.Item.Editor;

import java.util.LinkedList;
import java.util.List;

import sparshui.common.Event;
import GUI.Multitouch.TouchItemInterface;
import SceneItems.Util;

import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsItemGroup;
import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QGraphicsItem.GraphicsItemFlag;

public class TouchableItemGroupItem extends QGraphicsItemGroup implements TouchItemInterface {
	private int id = Util.getGroupID();
	private LinkedList<Integer> allowedGestures = new LinkedList<Integer>();
	private static QBrush brush = new QBrush(new QColor(255,255,0,120));
	private static QPen pen = new QPen(new QColor(130,130,130));

	
	private LinkedList<QGraphicsItemInterface> items = new LinkedList<QGraphicsItemInterface>();
	private QRectF boundingRect;
	
	public TouchableItemGroupItem(QRectF area) {
		this.boundingRect = area;
		this.setZValue(-10.0);
		this.setFlag(GraphicsItemFlag.ItemIsMovable, true);
		this.setFlag(GraphicsItemFlag.ItemIsSelectable, true);
		
	}
	
	public void addItemToGroup(QGraphicsItemInterface item) {
		this.items.add(item);
		super.addToGroup(item);
	}
	
	public List<QGraphicsItemInterface> getItemGroup() {
		return this.items;
	}
	
	@Override
	public QRectF boundingRect() {
		return this.boundingRect;
	}

	@Override
	public void paint(QPainter painter, QStyleOptionGraphicsItem option,QWidget widget) {
		painter.setPen(pen);
		painter.setBrush(brush);
		
		painter.drawRoundedRect(boundingRect, 25,25);
	}

	@Override
	public List<Integer> getAllowedGestures() {

		return allowedGestures;
	}

	@Override
	public int getGroupID() {
		return id;
	}

	@Override
	public boolean processEvent(Event event) {

		return false;
	}
}
