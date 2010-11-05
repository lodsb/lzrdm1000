package sparshui.server;

import java.io.IOException;
import java.util.Vector;

import sparshui.common.Event;
import sparshui.common.TouchState;
import sparshui.gestures.Gesture;

/**
 * Represents a group of touch points 
 * for the gesture server.
 * 
 * @author Tony Ross
 * 
 */
public class Group {

	private int _id;
	private Vector<Integer> _gestureIDs;
	private Vector<Gesture> _gestures;
	private Vector<TouchPoint> _touchPoints;
	private ServerToClientProtocol _clientProtocol;

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
	
	public Group(){}
	
	public Group(int id, Vector<Integer> gestureIDs,
			ServerToClientProtocol clientProtocol) {
		_id = id;
		_gestureIDs = gestureIDs;
		_gestures = new Vector<Gesture>();
		_touchPoints = new Vector<TouchPoint>();
		_clientProtocol = clientProtocol;
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
	public synchronized void update(TouchPoint changedPoint) {
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

		try {
			_clientProtocol.processEvents(_id, events);
		} catch (IOException e) {
			/*
			 * Do nothing here.  We're ignoring the error because
			 * the client will get killed on the next touch point
		  	 * birth and we do not have a reference to the client
			 * or the server from group to avoid circular references.
			 */
		}
	}

}
