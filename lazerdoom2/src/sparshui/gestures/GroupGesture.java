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
	private static HashMap<Integer, Boolean> movedOutOfStartRegion = new HashMap<Integer, Boolean>();
	
	double maxDeviance = 0.025;
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
			this.updateStartRegion(changedTouchPoint);
			break;
		case DEATH:
			Location startLocation = startPoints.get(changedTouchPoint.getID());
			if(startLocation == null) {
				events.add(new GroupEvent(changedTouchPoint.getLocation(), false, false, changedTouchPoint.getID()));
			} else {
				double x = changedTouchPoint.getLocation().getX();
				double y = changedTouchPoint.getLocation().getY();
				this.updateStartRegion(changedTouchPoint);
				double distance = Math.sqrt((double)(x-startLocation.getX())*(x-startLocation.getX())+(y-startLocation.getY())*(y-startLocation.getY()));
				//System.out.println("################## "+((distance <= maxDeviance) && (movedOutOfStartRegion.get(changedTouchPoint.getID())))+ " distance "+distance + " startloc "+startLocation+ " "+(x-startLocation.getX())*(x-startLocation.getX())+(y-startLocation.getY())*(y-startLocation.getY())+" region "+movedOutOfStartRegion.get(changedTouchPoint.getID()));
				events.add(new GroupEvent(changedTouchPoint.getLocation(), false,((distance <= maxDeviance) && movedOutOfStartRegion.get(changedTouchPoint.getID())) , changedTouchPoint.getID()));
				startPoints.remove(changedTouchPoint.getID());
				movedOutOfStartRegion.remove(changedTouchPoint.getID());
			}
			break;
			
		}
		return events;
	}

	private void updateStartRegion(TouchPoint changedTouchPoint) {
		if(!movedOutOfStartRegion.containsKey(changedTouchPoint.getID())) {
			Location startLocation = startPoints.get(changedTouchPoint.getID());
			if(startLocation == null) return;
			double x = changedTouchPoint.getLocation().getX();
			double y = changedTouchPoint.getLocation().getY();
			double distance = Math.sqrt((double)(x-startLocation.getX())*(x-startLocation.getX())+(y-startLocation.getY())*(y-startLocation.getY()));
			movedOutOfStartRegion.put(changedTouchPoint.getID(), distance > maxDeviance);
		} else if(!movedOutOfStartRegion.get(changedTouchPoint.getID())) {
			Location startLocation = startPoints.get(changedTouchPoint.getID());
			double x = changedTouchPoint.getLocation().getX();
			double y = changedTouchPoint.getLocation().getY();
			double distance = Math.sqrt((double)(x-startLocation.getX())*(x-startLocation.getX())+(y-startLocation.getY())*(y-startLocation.getY()));
			movedOutOfStartRegion.put(changedTouchPoint.getID(), distance > maxDeviance);
		}
	}
	@Override
	public GestureType getGestureType() {
		return GestureType.GROUP_GESTURE;
	}
	
}