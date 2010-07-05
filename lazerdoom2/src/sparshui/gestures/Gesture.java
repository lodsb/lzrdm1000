package sparshui.gestures;

import java.util.Vector;

import sparshui.common.Event;
import sparshui.server.TouchPoint;

/**
 * Defines the interface that all user-defined gestures must implement.
 *
 * @author Tony Ross
 *
 */
public interface Gesture {

	/**
	 * Process a touch point change in the gesture.
	 * 
	 * @param touchPoints
	 * 		The list of touch points that currently belong to this gesture.
	 * @param changedTouchPoint
	 * 		The touch point that has changed.
	 * @return
	 * 		A vector of events that will be delivered to the client.
	 * 		
	 */
	public abstract Vector<Event> processChange(Vector<TouchPoint> touchPoints,
			TouchPoint changedTouchPoint);

	/**
	 * Get the name of this gesture.
	 * @return
	 * 		The name of this gesture.
	 */
	public abstract String getName();
	
	/**
	 * Get the integer value of this gesture type.  Gesture values
	 * are defined in GestureType.java.
	 * @return
	 * 		The gesture type.
	 */
	public GestureType getGestureType();
}
