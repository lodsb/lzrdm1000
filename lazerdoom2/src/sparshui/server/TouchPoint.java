package sparshui.server;

import sparshui.common.Location;
import sparshui.common.TouchState;

/**
 * Represents a touch point.
 * 
 * @author Tony Ross
 *
 */
public class TouchPoint {
	/**
	 * Used to assign a globally unique id to a new touch point.
	 */
	private static int nextID = 0;
	
	/**
	 * 
	 */
	private static Object idLock = new Object();
	
	/**
	 * 
	 */
	private int _id;
	
	/**
	 * 
	 */
	private Location _location;
	
	/**
	 * 
	 */
	private TouchState _state;
	
	/**
	 * 
	 */
	private boolean _changed;
	
	/**
	 * 
	 */
	private Group _group;

	/**
	 * 
	 * @param location
	 */
	public TouchPoint(Location location) {
		synchronized(idLock) {
			_id = nextID++;
		}
		_location = location;
		_state = TouchState.BIRTH;
	}
	
	/**
	 * Copy constructor
	 * @param tp
	 */
	public TouchPoint(TouchPoint tp) {
		_id = tp._id;
		_location = tp._location;
		_state = tp._state;
	}
	
	/**
	 * Get the touch point ID.
	 * @return
	 * 		The touch point ID.
	 */
	public int getID() {
		return _id;
	}
	
	/**
	 * Get the touch point location.
	 * @return
	 * 		The location of this touch point.
	 */
	public Location getLocation() {
		return _location;
	}
	
	/**
	 * Get the touch point state.
	 * @return
	 * 		The state of this touch point.
	 */
	public TouchState getState() {
		return _state;
	}
	
	/**
	 * Set the group for this touch point.
	 * 
	 * @param group
	 * 		The group the touch point should belong to.
	 */
	public void setGroup(Group group) {
		_group = group;
		//System.out.println("Group set, group = " + _group.toString());
		_group.update(this);
	}
	
	/**
	 * Update this touch point with a new location and state.
	 * 
	 * @param location
	 * 		The new location.
	 * @param state
	 * 		The new state.
	 */
	public void update(Location location, TouchState state) {
		_location = location;
		_state = state;
		_changed = true;
		if(_group != null) _group.update(this);
	}
	
	/**
	 * Reset the changed flag.
	 */
	public void resetChanged() {
		_changed = false;
	}
	
	/**
	 * Get the value of the changed flag.
	 * @return
	 * 		True if this touchpoint has changed since the
	 * 		last time resetChanged() was called.
	 */
	public boolean isChanged() {
		return _changed;
	}
	
	@Override
	public TouchPoint clone() {
		return new TouchPoint(this);
	}
	
}
