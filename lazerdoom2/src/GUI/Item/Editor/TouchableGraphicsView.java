package GUI.Item.Editor;

import java.util.LinkedList;
import java.util.List;

import sparshui.common.Event;
import sparshui.common.messages.events.DragEvent;
import sparshui.common.messages.events.TouchEvent;
import GUI.Item.TouchPointCursor;
import GUI.Multitouch.TouchItemInterface;
import SceneItems.Util;

import com.trolltech.qt.core.QPoint;
import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRect;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;

public class TouchableGraphicsView extends QGraphicsView implements TouchItemInterface {
	private TouchItemInterface parent = null;
	private int id = Util.getGroupID();
	private LinkedList<Integer> allowedGestures = new LinkedList<Integer>();
	private boolean enableTouchEvents = true;
	
	
	public TouchableGraphicsView() {
		allowedGestures.add(sparshui.gestures.GestureType.TOUCH_GESTURE.ordinal());
		System.out.println("gview " +this);
	}
	
	public int getGroupIDViewCoordinates(QPointF pos) {
		return -1;
	}
	
	public void enableTouchEvents(boolean enableTouchEvents) {
		this.enableTouchEvents = enableTouchEvents;
	}
	
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
		System.out.println("NNNNNNAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHH");
		if(event instanceof TouchEvent) {
			TouchEvent e = (TouchEvent) event;
			e.setSceneLocation(this.mapToScene((int)e.getSceneLocation().x(), (int)e.getSceneLocation().y()));
			TouchPointCursor tc = new TouchPointCursor();
			this.scene().addItem(tc);
			tc.setPos(e.getSceneLocation());
		} else if (event instanceof DragEvent) {
			DragEvent e = (DragEvent) event;
			e.setSceneLocation(this.mapToScene((int)e.getSceneLocation().x(), (int)e.getSceneLocation().y()));
			TouchPointCursor tc = new TouchPointCursor();
			this.scene().addItem(tc);
			tc.setPos(e.getSceneLocation());
		}
		
		return true;
	}
}
