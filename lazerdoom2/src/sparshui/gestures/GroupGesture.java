package sparshui.gestures;

import java.util.HashMap;
import java.util.Vector;

import sparshui.common.Event;
import sparshui.common.Location;

import java.util.Vector;

import sparshui.common.Event;
import sparshui.common.messages.events.DragEvent;
import sparshui.common.messages.events.GroupEvent;
import sparshui.common.messages.events.TouchEvent;
import sparshui.gestures.StandardDynamicGesture.TouchData;
import sparshui.server.TouchPoint;

/**
 * 
 */
public class GroupGesture implements Gesture {

	private static HashMap<Integer, Location> startPoints = new HashMap<Integer, Location>();
	double maxDeviance = 0.1;
	@Override
	public String getName() {
		return "GroupGesture";
	}
	
	@Override
	public Vector<Event> processChange(Vector<TouchPoint> touchPoints, TouchPoint changedTouchPoint) {
		Vector<Event> events = new Vector<Event>();
		
		switch (changedTouchPoint.getState()){
		case BIRTH:
			startPoints.put(changedTouchPoint.getID(), changedTouchPoint.getLocation());
			events.add(new GroupEvent(changedTouchPoint.getLocation(), true, false, changedTouchPoint.getID()));
			break;
		case MOVE:
			events.add(new GroupEvent(changedTouchPoint.getLocation(), true, false, changedTouchPoint.getID()));
			break;
		case DEATH:
			Location startLocation = startPoints.get(changedTouchPoint.getID());
			double x = changedTouchPoint.getLocation().getX();
			double y = changedTouchPoint.getLocation().getY();
			double distance = Math.sqrt(x-startLocation.getX())*(x-startLocation.getX())+(y-startLocation.getY())*(y-startLocation.getY());
			events.add(new GroupEvent(changedTouchPoint.getLocation(), false,(distance <= maxDeviance) , changedTouchPoint.getID()));
			break;
			
		}
		System.out.println(events);
		return events;
	}

	@Override
	public GestureType getGestureType() {
		return GestureType.GROUP_GESTURE;
	}
	
}