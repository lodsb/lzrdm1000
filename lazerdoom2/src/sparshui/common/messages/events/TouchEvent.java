package sparshui.common.messages.events;

import GUI.Multitouch.*;

import com.trolltech.qt.core.QPointF;

import sparshui.common.Event;
import sparshui.common.TouchState;
import sparshui.common.utils.Converter;

/**
 * 
 */
public class TouchEvent implements Event {
	private static final long serialVersionUID = 370824346017492361L;
	
	private int _id;
	private float _x;
	private float _y;
	private TouchState _state;
	
	private boolean isFocused = false;
	
	@Override
	public boolean isFocused() {
		return this.isFocused;
	}
	
	@Override
	public void setFocused() {
		this.isFocused = true;
	}
	
	private QPointF _pos = null;
	@Override
	public void setSceneLocation(QPointF pos) {
		_pos = pos;
	}
	@Override
	public QPointF getSceneLocation() {
		return _pos;
	}
		
	
	public TouchEvent() {
		_id = 0;
		_x = 0;
		_y = 0;
		_state = TouchState.BIRTH;
	}
	
	/**
	 * 
	 * @param id
	 * @param x
	 * @param y
	 * @param state
	 */
	public TouchEvent(int id, float x, float y, TouchState state) {
		_id = id;
		_x = x;
		_y = y;
		_state = state;
	}
	
	public int getTouchID() {
		return _id;
	}
	
	public float getX() {
		return _x;
	}
	
	public float getY() {
		return _y;
	}
	
	public void setX(float x) {
		_x = x;
	}
	
	public void setY(float y) {
		_y = y;
	}
	
	public TouchState getState() {
		return _state;
	} 
	
	public boolean isOngoing() {
		return (this._state != TouchState.DEATH);
	}

	/**
	 *  Constructs a new TouchEvent from a serialized version
	 * 		- 4 bytes	: id
	 * 		- 4 bytes	: x
	 * 		- 4 bytes	: y
	 * 		- 4 bytes	: state
	 *		- 16 bytes total
	 *
	 * @param data the serialized version of touchEvent
	 */
	public TouchEvent(byte[] data) {
		if (data.length < 16) {
			System.err.println("An error occurred while deserializing a TouchEvent.");
		} else {
			byte temp[] = new byte[4];
			
			// id
			System.arraycopy(data, 0, temp, 0, 4);
			_id = Converter.byteArrayToInt(temp);

			// x
			System.arraycopy(data, 4, temp, 0, 4);
			_x = Float.intBitsToFloat(Converter.byteArrayToInt(temp));
			
			// y
			System.arraycopy(data, 8, temp, 0, 4);
			_y = Float.intBitsToFloat(Converter.byteArrayToInt(temp));
			
			// state			
			System.arraycopy(data, 12, temp, 0, 4);
			int state = Converter.byteArrayToInt(temp);
			
			_state = TouchState.values()[state];
		}
	}

	@Override
	public int getEventType() {
		return EventType.TOUCH_EVENT.ordinal();
	}

	/**
	 * Constructs the data packet with this event data. Message format for this
	 * event:
	 * 		- 4 bytes	: event type
	 * 		- 4 bytes	: id
	 * 		- 4 bytes	: x
	 * 		- 4 bytes	: y
	 * 		- 4 bytes	: state
	 * 		- 20 bytes total
	 */
	public byte[] serialize() {
		byte[] data = new byte[20];
		// name
		byte[] tempFloat = new byte[4];
		tempFloat = Converter.intToByteArray(getEventType());
		System.arraycopy(tempFloat, 0, data, 0, 4);
		// id		
		System.arraycopy(Converter.intToByteArray(_id), 0, data, 4, 4);
		// x
		System.arraycopy(Converter.intToByteArray(Float.floatToIntBits(_x)), 0, data, 8, 4);
		// y
		System.arraycopy(Converter.intToByteArray(Float.floatToIntBits(_y)), 0, data, 12, 4);
		// state
		System.arraycopy(Converter.intToByteArray(_state.ordinal()), 0, data, 16, 4);

		//System.out.print("TouchEvent.serialize(): data: ");
		for(byte d : data){
			//System.out.print(d+",");
		}
		//System.out.println();
		
		return data;
	}
	
	@Override
	public String toString() {
		return ("Touch Event: ID: " + _id + ", X: " + _x + ", Y: " + _y + "State: " + _state);
	}
	
	private TouchItemInterface item = null;
	
	@Override
	public TouchItemInterface getSource() {
		return item;
	}
	@Override
	public void setSource(TouchItemInterface it) {
		item = it;
	}	
}
