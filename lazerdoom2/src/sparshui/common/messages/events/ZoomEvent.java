package sparshui.common.messages.events;


import com.trolltech.qt.core.QPointF;

import gui.multitouch.TouchItemInterface;
import sparshui.common.Event;
import sparshui.common.Location;
import sparshui.common.utils.Converter;

public class ZoomEvent implements Event {
	private static final long serialVersionUID = -4658011539863774168L;
	
	private float _scale;
	private Location _center;
	private int id;
	public void setTouchID(int id) {
		this.id = id;
	}
	public int getTouchID() {
		return this.id;
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

	
	public ZoomEvent() {
		_scale = 1;
		_center = new Location();
	}
	
	public ZoomEvent(float scale, Location center) {
		_scale = scale;
		_center = center;
		//System.out.println("ZoomEvent Constructed, Scale = " + _scale);
	}
	
	public float getScale() {
		return _scale;
	}
	
	public Location getCenter() {
		return _center;
	}
	
	public void setCenter(Location center) {
		_center = center;
	}
	
	/**
	 * Constructs a ZoomEvent from a serialized version of ZoomEvent.
	 *  - 4 bytes : scale 
	 *  - 4 bytes : center - x coordinate 
	 *  - 4 bytes : center - y coordinate
	 *  - 12 bytes total
	 *
	 * @param serializedZoomEvent
	 */
	public ZoomEvent(byte[] serializedZoomEvent) {
		
		if (serializedZoomEvent.length < 12) {
			// TODO add error handling
			System.err.println("Error constructing Zoom Event.");
			_scale = 1;
			_center = new Location(0, 0);
		} else {
			byte[] tempFloat = new byte[4];
			// scale
			System.arraycopy(serializedZoomEvent, 0, tempFloat, 0, 4);
			int scaleInt = Converter.byteArrayToInt(tempFloat);
			_scale = Float.intBitsToFloat(scaleInt);
			
			// center - x coordinate
			System.arraycopy(serializedZoomEvent, 4, tempFloat, 0, 4);
			int dxint = Converter.byteArrayToInt(tempFloat);
			
			// center - y coordinate
			System.arraycopy(serializedZoomEvent, 8, tempFloat, 0, 4);
			int dyint = Converter.byteArrayToInt(tempFloat);
			
			_center = new Location(Float.intBitsToFloat(dxint), Float.intBitsToFloat(dyint));
		}
	}

	@Override
	public int getEventType() {
		return EventType.ZOOM_EVENT.ordinal();
	}

	/**
	 * Constructs the data packet with this event data. Message format for this
	 * event:
	 *  - 4 bytes : event type
	 *  - 4 bytes : scale 
	 *  - 4 bytes : center - x coordinate 
	 *  - 4 bytes : center - y coordinate
	 *  - 16 bytes total
	 */
	public byte[] serialize() {

		byte[] data = new byte[16];
		byte[] tempFloat = new byte[4];
		
		
		// event type
		tempFloat = Converter.intToByteArray(getEventType());
		System.arraycopy(tempFloat, 0, data, 0, 4);
		// scale
		int intBits = Float.floatToIntBits(_scale);
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
		return ("ZOOM Scale: " + _scale + ", Center: " + _center.toString());
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
	@Override
	public boolean isOngoing() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setFocused() {
		// TODO Auto-generated method stub
		
	}	

}
