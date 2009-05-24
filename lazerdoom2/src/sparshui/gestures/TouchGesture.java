package sparshui.gestures;

import java.util.Vector;

import sparshui.common.Event;
import sparshui.common.messages.events.TouchEvent;
import sparshui.server.TouchPoint;

public class TouchGesture implements Gesture {

	@Override
	public Vector<Event> processChange(Vector<TouchPoint> touchPoints, TouchPoint changedTouchPoint) {
		Vector<Event> retEvents = new Vector<Event>();
		
		retEvents.add(new TouchEvent(changedTouchPoint.getID(),
									changedTouchPoint.getLocation().getX(),
									changedTouchPoint.getLocation().getY(),
									changedTouchPoint.getState()));
		return retEvents;
	}

	@Override
	public String getName() {
		return "TouchGesture";
	}
	
	@Override
	public GestureType getGestureType() {
		return GestureType.TOUCH_GESTURE;
	}

}
