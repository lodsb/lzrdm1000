package lazerdoom;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import sparshui.common.Event;
import sparshui.common.TouchState;
import sparshui.gestures.Gesture;
import sparshui.server.GestureFactory;
import sparshui.server.Group;
import sparshui.server.ServerToClientProtocol;
import sparshui.server.TouchPoint;

public class GestureGroup extends Group {

	private int _id;
	private List<Integer> _gestureIDs;
	private Vector<Gesture> _gestures;
	private Vector<TouchPoint> _touchPoints;
	private TouchHandler _touchHandler;

	/**
	 * Construct a new group with the given gesture IDs and
	 * the given connection to the client.  Groups are associate
	 * with one client only.
	 * 
	 * @param gestureIDs
	 * 		The list of gesture IDs that this group should process.
	 * @param clientProtocol
	 * 		Represents the connection to the client.
	 */
	
	public GestureGroup(int id, List<Integer> gestureIDs, TouchHandler touchHandler) {
		_id = id;
		_gestureIDs = gestureIDs;
		_gestures = new Vector<Gesture>();
		_touchPoints = new Vector<TouchPoint>();
		_touchHandler = touchHandler;
		
		for (Integer gID : _gestureIDs) {
			_gestures.add(GestureFactory.getInstance().createGesture(gID));
		}
	}

	/**
	 * 
	 * @return
	 * 		The group ID
	 */
	public int getID() {
		return _id;
	}

	/**
	 * Update the given touch point that belongs to this group.
	 * 
	 * @param changedPoint
	 * 		The changed touch point.
	 */
	public void update(TouchPoint changedPoint) {
		Vector<Event> events = new Vector<Event>();

		if (changedPoint.getState() == TouchState.BIRTH) {
			_touchPoints.add(changedPoint);
		}

		Vector<TouchPoint> clonedPoints = new Vector<TouchPoint>();
		for (TouchPoint touchPoint : _touchPoints) {
			synchronized (touchPoint) {
				TouchPoint clonedPoint = touchPoint.clone();
				clonedPoints.add(clonedPoint);
			}
		}

		if (changedPoint.getState() == TouchState.DEATH) {
			_touchPoints.remove(changedPoint);
		}

		for (Gesture gesture : _gestures) {
			//System.out.println(_gestures.size());
			//System.out.println("Gesture allowed: " + gesture.getName());
			events.addAll(gesture.processChange(clonedPoints, changedPoint));
			//System.out.println("Got some events - size: " + events.size());
		}

		_touchHandler.processEvents(_id, events);
	}

}
