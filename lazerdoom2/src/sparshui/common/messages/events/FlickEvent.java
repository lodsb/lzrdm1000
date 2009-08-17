package sparshui.common.messages.events;

import SceneItems.TouchItemInterface;

import com.trolltech.qt.core.QPointF;

import sparshui.common.Event;
import sparshui.common.utils.Converter;
import sparshui.gestures.Flick.Velocity;

public class FlickEvent implements Event {
	
	
	
	
	private static final long serialVersionUID = -2305607021385835330L;		
//	private float _absx;
//	private float _absy;	
	
	private Velocity[] runningVelocities;
	private float xDirection;
	private float yDirection;
	private float speedLevel;
	
	private QPointF _pos = null;
	private int id;
	public void setTouchID(int id) {
		this.id = id;
	}
	public int getTouchID() {
		return this.id;
	}
	@Override
	public void setSceneLocation(QPointF pos) {
		_pos = pos;
	}
	@Override
	public QPointF getSceneLocation() {
		return _pos;
	}

	
	public FlickEvent() {
//		_absx = 0;
//		_absy = 0;
		runningVelocities = null;
		xDirection = 0;
		yDirection = 0;
		speedLevel = 0;
	}
	
	public FlickEvent(float absx, float absy) {
//		_absx = absx;
//		_absy = absy;
	}
	
	public FlickEvent(int _speedLevel, int _xDirection, int _yDirection){
		speedLevel =(float) _speedLevel;
		xDirection =(float) _xDirection;
		yDirection =(float) _yDirection;
	}
	

	/**
	 * Constructs a flickEvent from a complete serialized version of the drag
	 * event.
	 *  - 4 bytes : dx 
	 *  - 4 bytes : dy 
	 *  - 8 bytes total
	 * 
	 * @param serializedDragEvent
	 *            The byte array that represents a serialized Drag Event.
	 */
	public FlickEvent(byte[] serializedFlickEvent) {
		if (serializedFlickEvent.length < 12) {
			// TODO add error handling
			System.err.println("Error constructing Flick Event.");
		} else {
			byte[] tempFloat = new byte[4];
			// Get the speed level
			System.arraycopy(serializedFlickEvent, 0, tempFloat, 0, 4);
			int speedint = Converter.byteArrayToInt(tempFloat);
			speedLevel = Float.intBitsToFloat(speedint);
			
			// Get the x and y direction
			System.arraycopy(serializedFlickEvent, 4, tempFloat, 0, 4);
			int xDirec = Converter.byteArrayToInt(tempFloat);
			xDirection = Float.intBitsToFloat(xDirec);
			System.arraycopy(serializedFlickEvent, 4, tempFloat, 0, 4);
			int yDirec = Converter.byteArrayToInt(tempFloat);
			yDirection = Float.intBitsToFloat(yDirec);
		}
	}
	
	
	public float getSpeedLevel() {
		return speedLevel;
	}

	public float getXdirection() {
		return xDirection;
	}
	public float getYdirection() {
		return yDirection;
	}


	@Override
	public int getEventType() {
		return EventType.FLICK_EVENT.ordinal();
	}

	@Override
	public String toString() {
		String ret = "Flick Event";
		return ret;
	}

	/**
	 * Constructs the data packet with this event data. Message format for this
	 * event: 
	 * 
	 * - 4 bytes : EventType 
	 * - 4 bytes : SpeedLevel 
	 * - 4 bytes : X Direction
	 * - 4 bytes : Y Direction
	 * - 16 bytes total
	 */
	public byte[] serialize() {
		byte[] data = new byte[16];
		byte[] tempFloat = new byte[4];
		
		// Event Type
		tempFloat = Converter.intToByteArray(getEventType());
		System.arraycopy(tempFloat, 0, data, 0, 4);
		
		// SpeedLevel
		int intBits = Float.floatToIntBits(speedLevel);
		tempFloat = Converter.intToByteArray(intBits);
		System.arraycopy(tempFloat, 0, data, 4, 4);
		
		// X Direction
		intBits = Float.floatToIntBits(xDirection);
		tempFloat = Converter.intToByteArray(intBits);
		System.arraycopy(tempFloat, 0, data, 8, 4);
		
		// Y Direction
		intBits = Float.floatToIntBits(yDirection);
		tempFloat = Converter.intToByteArray(intBits);
		System.arraycopy(tempFloat, 0, data, 12, 4);
		
		return data;
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
