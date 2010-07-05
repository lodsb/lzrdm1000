package sparshui.common.messages.core;

import sparshui.common.Event;
import sparshui.common.messages.Message;

/**
 * 
 */
public class ProcessEvents extends Message<Void> {
	private int _groupID;
	private Event[] _events;
	
	/**
	 * 
	 */
	public ProcessEvents() {
		_groupID = 0;
		_events = null;
	}
	
	/**
	 * 
	 * @param groupID
	 * @param events
	 */
	public ProcessEvents(int groupID, Event[] events) {
		_groupID = groupID;
		_events = events;
	}
	
	/**
	 * 
	 * @return
	 * 		The group ID.
	 */
	public int getGroupID() {
		return _groupID;
	}
	
	/**
	 * 
	 * @return
	 * 		The events that were generated.
	 */
	public Event[] getEvents() {
		return _events;
	}
}
