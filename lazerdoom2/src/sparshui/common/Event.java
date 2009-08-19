package sparshui.common;

import java.io.Serializable;

import GUI.Multitouch.*;

import com.trolltech.qt.core.QPointF;

/**
 * This interface must be implemented by all user-defined events.
 * 
 * @author Jay Roltgen
 *
 */
public interface Event extends Serializable {

	/**
	 * Returns the integer value of this event type.  Event type
	 * values are defined in the enumeration 
	 * sparshui.common.messages.events.EventType.java
	 * 
	 * @return
	 * 		The event type
	 */
	public abstract int getEventType();
	
	/**
	 * Serializes this event for transmission over the network.
	 * The user-defined event shall implement this method, as well
	 * as a constructor that takes the serialized byte array as an
	 * input argument.  This method will serialize the event, and
	 * the constructor will "unserialize" it.
	 * 
	 * @return
	 * 		The serialized event, ready for transmission over
	 * 		the network.
	 */
	public abstract byte[] serialize();
	public void setSource(TouchItemInterface it);
	public TouchItemInterface getSource();
	
	public void setSceneLocation(QPointF pos);
	public QPointF getSceneLocation();
		
}
