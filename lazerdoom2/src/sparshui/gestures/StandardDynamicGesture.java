package sparshui.gestures;

import java.util.HashMap;
import java.util.Vector;

import sparshui.common.Event;
import sparshui.common.Location;
import sparshui.server.TouchPoint;

/**
 * This is a class to ease implementation of standard dynamic
 * gestures.  Standard dynamic gestures can inherit from this class
 * and this class takes care of managing the list of touchpoints
 * that belong to this gesture.  Subclasses need only implement
 * the processBirth, processMove, and processDeath methods to
 * perform gesture processing.
 * 
 * @author Jay Roltgen
 */
public abstract class StandardDynamicGesture implements Gesture {
	
	protected HashMap<Integer, TouchData> _knownPoints;
	protected Location _oldCentroid;
	protected Location _newCentroid;
	protected float _sumX, _sumY;
	
	/**
	 * 
	 */
	protected StandardDynamicGesture() {
		_knownPoints = new HashMap<Integer, TouchData>();
		_oldCentroid = new Location(0, 0);
		_newCentroid = new Location(0, 0);
	}

	/**
	 * Get the name of this gesture.
	 * 
	 * @return
	 * 		The name of this gesture.
	 */
	@Override
	public abstract String getName();

	@Override
	public Vector<Event> processChange(Vector<TouchPoint> touchPoints,
			TouchPoint changedPoint) {
		Vector<Event> events = null;
		
		switch(changedPoint.getState()) {
			case BIRTH:
				events = handleBirth(changedPoint);
				break;
			case MOVE:
				events = handleMove(changedPoint);
				break;
			case DEATH:
				//System.out.println("calling handledeath");
				events = handleDeath(changedPoint);
				//System.out.println(events);
				break;
		}
		
		return (events != null) ? events : new Vector<Event>();
	}
	
	/**
	 * 
	 * @param touchPoint
	 * @return
	 */
	protected TouchData createTouchData(TouchPoint touchPoint) {
		return new TouchData(touchPoint.getLocation(), touchPoint.getID());
	}
	
	/**
	 * 
	 * @param touchData
	 * @return
	 */
	protected abstract Vector<Event> processBirth(TouchData touchData);
	
	/**
	 * 
	 * @param touchData
	 * @return
	 */
	protected abstract Vector<Event> processMove(TouchData touchData);
	
	/**
	 * 
	 * @param touchData
	 * @return
	 */
	protected abstract Vector<Event> processDeath(TouchData touchData);
	
	/**
	 * 
	 * @param touchPoint
	 * @return
	 */
	private Vector<Event> handleBirth(TouchPoint touchPoint) {
		TouchData touchData = createTouchData(touchPoint);
		_knownPoints.put(touchPoint.getID(), touchData);
		moveCentroid(touchData.getLocation().getX(), touchData.getLocation().getY());
		return processBirth(touchData);
	}
	
	/**
	 * 
	 * @param touchPoint
	 * @return
	 */
	private Vector<Event> handleMove(TouchPoint touchPoint) {
		TouchData touchData = _knownPoints.get(touchPoint.getID());
		if(touchData != null) {
			touchData.setLocation(touchPoint.getLocation());
			moveCentroid(
					touchData.getLocation().getX() - touchData.getOldLocation().getX(),
					touchData.getLocation().getY() - touchData.getOldLocation().getY()
			);
			return processMove(touchData);
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param touchPoint
	 * @return
	 */
	private Vector<Event> handleDeath(TouchPoint touchPoint) {
		TouchData touchData = _knownPoints.get(touchPoint.getID());
		if(touchData != null) {
			touchData.setLocation(touchPoint.getLocation());
			_knownPoints.remove(touchPoint.getID());
			moveCentroid(-touchData.getOldLocation().getX(), -touchData.getOldLocation().getY());
			return processDeath(touchData);
		} 
		return null;
	}
	
	/**
	 * 
	 * @param dx
	 * @param dy
	 */
	private void moveCentroid(float dx, float dy) {
		_sumX += dx;
		_sumY += dy;
		_oldCentroid = _newCentroid;
		_newCentroid = new Location(_sumX / _knownPoints.size(), _sumY / _knownPoints.size());
	}

	/**
	 * 
	 */
	protected class TouchData {
		private Location _location;
		private Location _oldLocation;
		private int uniqueID;
		
		public int getUniqueID () {
			return uniqueID;
		}
		
		public TouchData(Location location, int id) {
			_oldLocation = _location = location;
			this.uniqueID = id;
		}
		public Location getLocation() {
			return _location;
		}
		public Location getOldLocation() {
			return _oldLocation;
		}
		public void setLocation(Location location) {
			_oldLocation = _location;
			_location = location;
		}
	}
}
