package sparshui.common.messages.events;


import gui.multitouch.TouchItemInterface;

import com.trolltech.qt.core.QPointF;

import sparshui.common.Event;
import sparshui.common.Location;
import sparshui.common.utils.Converter;

public class RotateEvent implements Event {
	private static final long serialVersionUID = -5467788080845086125L;
	
	/**
	 * Rotation of this component in radians
	 */
	private float _rotation;
	private Location _center;
	private int id;
	public void setTouchID(int id) {
		this.id = id;
	}
	public int getTouchID() {
		return this.id;
	}
	public RotateEvent() {
		_rotation = 0;
		_center = new Location();
	}
	
	public RotateEvent(float rotation, Location center, int id, boolean isOngoing) {
		_rotation = rotation;
		_center = center;
		this.setTouchID(id);
		this.isOngoing = isOngoing;
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

	
	/**
	 * Constructs a new Rotate Event based on a serial representation
	 * of a rotate event.
	 *  - 4 bytes : rotation
	 *  - 4 bytes : center - x coordinate
	 *  - 4 bytes : center - y coordinate
	 *  - 12 bytes total
	 *  
	 * @param serializedRotateEvent the data
	 */
	public RotateEvent(byte[] serializedRotateEvent) {
		if (serializedRotateEvent.length < 12) {
			// TODO add error handling
			System.err.println("Error constructing Rotate Event.");
			_rotation = 0;
			_center = new Location(0, 0);
		} else {
			byte[] tempFloat = new byte[4];
			// rotation
			System.arraycopy(serializedRotateEvent, 0, tempFloat, 0, 4);
			int rotationint = Converter.byteArrayToInt(tempFloat);
			_rotation = Float.intBitsToFloat(rotationint);
			
			// center - x coordinate
			System.arraycopy(serializedRotateEvent, 4, tempFloat, 0, 4);
			int dxint = Converter.byteArrayToInt(tempFloat);
			
			// center - y coordinate
			System.arraycopy(serializedRotateEvent, 8, tempFloat, 0, 4);
			int dyint = Converter.byteArrayToInt(tempFloat);
			
			_center = new Location(Float.intBitsToFloat(dxint), Float.intBitsToFloat(dyint));
		}
	}
	

	@Override
	public int getEventType() {
		return EventType.ROTATE_EVENT.ordinal(); 
	}
	
	/**
	 * Constructs the data packet with this event data. Message format for this
	 * event:
	 *  - 4 bytes : event type 
	 *  - 4 bytes : rotation
	 *  - 4 bytes : center - x coordinate
	 *  - 4 bytes : center - y coordinate
	 *  - 16 bytes total
	 */
	public byte[] serialize() {
		byte[] data = new byte[16];
		byte[] tempFloat = new byte[4];
		
		// name
		tempFloat = Converter.intToByteArray(getEventType());
		System.arraycopy(tempFloat, 0, data, 0, 4);
		
		// rotation
		//System.out.println("[RotateEvent] Rotation: " + _rotation);
		int intBits = Float.floatToIntBits(_rotation);
		tempFloat = Converter.intToByteArray(intBits);
		System.arraycopy(tempFloat, 0, data, 4, 4);
		
		// center - x coordinate
		intBits = Float.floatToIntBits(_center.getX());
		tempFloat = Converter.intToByteArray(intBits);
		System.arraycopy(tempFloat, 0, data, 8, 4);
		
		// center - y coordinate
		intBits = Float.floatToIntBits(_center.getY());
		tempFloat = Converter.intToByteArray(intBits);
		System.arraycopy(tempFloat, 0, data, 12, 4);

		
		return data;
	}

	@Override
	public String toString() {
		return ("Rotate Event - Rotation: " + _rotation + ", Center: " + _center.toString());
	}
	
	public float getRotation() {
		return _rotation;
	}
	
	public Location getCenter() {
		return _center;
	}
	
	public void setCenter(Location center) {
		_center = center;
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
	@Override
	public boolean isFocused() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean isOngoing = false;
	@Override
	public boolean isOngoing() {
		// TODO Auto-generated method stub
		return this.isOngoing;
	}
	@Override
	public void setFocused() {
		// TODO Auto-generated method stub
		
	}

}
