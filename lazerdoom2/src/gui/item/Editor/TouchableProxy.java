package gui.item.Editor;

import gui.multitouch.TouchItemInterface;

import java.util.LinkedList;
import java.util.List;

import sceneitems.Util;
import sparshui.common.Event;

import com.trolltech.qt.gui.QGraphicsProxyWidget;

public class TouchableProxy extends QGraphicsProxyWidget implements TouchItemInterface {
	private TouchItemInterface parent = null;
	private int id = Util.getGroupID();
	private LinkedList<Integer> allowedGestures = new LinkedList<Integer>();
	private boolean enableTouchEvents = true;
	
	public TouchableProxy() {
		allowedGestures.add(sparshui.gestures.GestureType.TOUCH_GESTURE.ordinal());
		System.out.println("proxy " +this);
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
		System.out.println("!!!!!!!!!!!!!!KSDFSDLFLSDFKSDKLFSDFLKÖ");
		return id;
	}

	@Override
	public boolean processEvent(Event event) {
		System.out.println("KSDFSDLFLSDFKSDKLFSDFLKÖ");
		if(parent != null && enableTouchEvents) {
			event.setSource(this);
			return parent.processEvent(event);
		} 
		
		return false;
	}
}

