package lazerdoom;

import sparshui.client.Client;
import sparshui.common.Event;
import sparshui.common.Location;
import sparshui.common.TouchState;
import sparshui.common.messages.events.DblClkEvent;
import sparshui.common.messages.events.DragEvent;
import sparshui.common.messages.events.RotateEvent;
import sparshui.common.messages.events.SpinEvent;
import sparshui.common.messages.events.TouchEvent;
import sparshui.common.messages.events.ZoomEvent;
import sparshui.server.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class TouchHandler {
	private Client mainClient;
	
	private HashMap<Integer, TouchPoint> activeTouchPoints = new HashMap<Integer, TouchPoint>();
	private HashMap<Integer, GestureGroup> activeGroups = new HashMap<Integer, GestureGroup>();
	
	public TouchHandler(Client mc) {
		this.mainClient = mc;
	}
	
	public void setMainClient(Client mc) {
		this.mainClient = mc;
	}
	
	public void addTouchPoint(int id, float x, float y) {
		Location loc = new Location(x,y);
		TouchPoint tp = new TouchPoint(loc);
		
		int groupID = mainClient.getGroupID(loc);
		GestureGroup group = this.getGroup(groupID);
		tp.setGroup(group);
		
		activeTouchPoints.put(id, tp);
	}
	
	public void removeTouchPoint(int id, float x, float y) {
		TouchPoint tp;
		if((tp = activeTouchPoints.get(id))!=null) {
			tp.update(new Location(x,y), TouchState.DEATH);
			//activeTouchPoints.remove(id);
		}
	}
	
	public void updateTouchPoint(int id, float x, float y) {
		TouchPoint tp;
		if((tp = activeTouchPoints.get(id))!=null) {
			tp.update(new Location(x,y), TouchState.MOVE);
		}		
	}
	
	private GestureGroup getGroup(int groupID) {
		GestureGroup group;
		
		if((group = activeGroups.get(groupID)) == null) {
			List<Integer> allowedGestures = mainClient.getAllowedGestures(groupID);
			group = new GestureGroup(groupID, allowedGestures, this);
			activeGroups.put(groupID, group);
		}
		
		return group;
	}
	
	public void processEvents(int groupID, Vector<Event> events) {
		for(Event e: events) {
				mainClient.processEvent(groupID, e);
		}
	}
	
	
}
