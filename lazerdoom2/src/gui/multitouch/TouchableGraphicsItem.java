package gui.multitouch;

import gui.item.editor.TouchableItemGroupItem;
import gui.multitouch.*;

import java.util.LinkedList;
import java.util.List;

import lazerdoom.LzrDmObjectInterface;

import sceneitems.Util;
import session.SessionHandler;
import sparshui.common.Event;


import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSizeF;
import com.trolltech.qt.gui.QGraphicsItem;
import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;


public abstract class TouchableGraphicsItem extends QGraphicsItem implements TouchItemInterface , GeometryInterface, LzrDmObjectInterface {

	private TouchItemInterface parent = null;
	private int id = Util.getGroupID();
	private LinkedList<Integer> allowedGestures = new LinkedList<Integer>();
	private boolean enableTouchEvents = true;
	
	//public final long sessionID = SessionHandler.getInstance().registerObject(this);
	
	public void setParent(TouchItemInterface parent) {
		this.parent = parent;
		
		if(parent instanceof QGraphicsItemInterface) {
			this.setParentItem((QGraphicsItemInterface)parent);
		}
	}
	private TouchableItemGroupItem group = null;
	public TouchableItemGroupItem belongsToGroup() {
		return group;
	}
	
	public void setBelongsToGroup(TouchableItemGroupItem group) {
		this.group = group;
	}
	
	public TouchItemInterface getParent() {
		return parent;
	}
	
	public void enableTouchEvents(boolean enableTouchEvents) {
		this.enableTouchEvents = enableTouchEvents;
	}
	
	@Override
	public abstract QRectF boundingRect();

	@Override
	public abstract void paint(QPainter painter, QStyleOptionGraphicsItem option,QWidget widget);
	
	@Override
	public List<Integer> getAllowedGestures() {
		// TODO Auto-generated method stub
		if(parent != null) {
			return parent.getAllowedGestures();
		} 
		return allowedGestures;
	}

	@Override
	public int getGroupID() {
		return id;
	}

	@Override
	public boolean processEvent(Event event) {
		if(parent != null && enableTouchEvents) {
			if(event.getSource() == null) {
				event.setSource(this);
			}
			return parent.processEvent(event);
		} 
		//this.update();
		
		return false;
	}
	
	public boolean setPosition(QPointF pos) {
		this.setPos(pos);
		return true;
	}

}
