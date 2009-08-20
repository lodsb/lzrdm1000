package GUI.Item.Editor;

import java.util.LinkedList;
import java.util.List;

import sparshui.common.Event;
import GUI.Multitouch.TouchItemInterface;
import SceneItems.Util;

import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QGraphicsWidget;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;

public class TouchableGraphicsWidget extends QGraphicsWidget implements TouchItemInterface {

	private TouchItemInterface parent = null;
	private int id = Util.getGroupID();
	private LinkedList<Integer> allowedGestures = new LinkedList<Integer>();
	private boolean enableTouchEvents = true;
	
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
		if(parent != null && enableTouchEvents) {
			event.setSource(this);
			return parent.processEvent(event);
		} 
		
		return false;
	}

}
