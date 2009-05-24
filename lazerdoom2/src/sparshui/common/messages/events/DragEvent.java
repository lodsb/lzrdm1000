package sparshui.common.messages.events;

import SceneItems.TouchItemInterface;

import com.trolltech.qt.core.QPointF;

import sparshui.common.Event;
import sparshui.common.utils.Converter;

public class DragEvent implements Event {
	private static final long serialVersionUID = -2305607021385835330L;
	
	private float _absx;
	private float _absy;

	public DragEvent() {
		_absx = 0;
		_absy = 0;
	}
	
	public DragEvent(float absx, float absy) {
		_absx = absx;
		_absy = absy;
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
	 * Constructs a dragEvent from a complete serialized version of the drag
	 * event.
	 *  - 4 bytes : dx 
	 *  - 4 bytes : dy 
	 *  - 8 bytes total
	 * 
	 * @param serializedDragEvent
	 *            The byte array that represents a serialized Drag Event.
	 */
	public DragEvent(byte[] serializedDragEvent) {
		if (serializedDragEvent.length < 8) {
			// TODO add error handling
			System.err.println("Error constructing Drag Event.");
			_absx = 0;
			_absy = 0;
		} else {
			byte[] tempFloat = new byte[4];
			
			// Get the x coordinate
			System.arraycopy(serializedDragEvent, 0, tempFloat, 0, 4);
			int dxint = Converter.byteArrayToInt(tempFloat);
			_absx = Float.intBitsToFloat(dxint);
			
			// Get the y coordinate
			System.arraycopy(serializedDragEvent, 4, tempFloat, 0, 4);
			int dyint = Converter.byteArrayToInt(tempFloat);
			_absy = Float.intBitsToFloat(dyint);
		}
	}
	
	
	public float getAbsX() {
		return _absx;
	}

	public float getAbsY() {
		return _absy;
	}
	
	public void setAbsX(float x) {
		_absx = x;
	}
	
	public void setAbsY(float y) {
		_absy = y;
	}

	@Override
	public int getEventType() {
		return EventType.DRAG_EVENT.ordinal(); 
	}

	@Override
	public String toString() {
		String ret = "Drag Event: absx = " + _absx + ", absy = " + _absy;
		return ret;
	}

	/**
	 * Constructs the data packet with this event data. Message format for this
	 * event: 
	 * 
	 * - 4 bytes : EventType 
	 * - 4 bytes : dx 
	 * - 4 bytes : dy
	 * - 12 bytes total
	 */
	public byte[] serialize() {
		byte[] data = new byte[12];
		byte[] tempFloat = new byte[4];
		
		// Event Type
		tempFloat = Converter.intToByteArray(getEventType());
		System.arraycopy(tempFloat, 0, data, 0, 4);
		
		// dx
		int intBits = Float.floatToIntBits(_absx);
		tempFloat = Converter.intToByteArray(intBits);
		System.arraycopy(tempFloat, 0, data, 4, 4);
		//System.out.println("X = " + _absx);
		
		// dy
		intBits = Float.floatToIntBits(_absy);
		tempFloat = Converter.intToByteArray(intBits);
		System.arraycopy(tempFloat, 0, data, 8, 4);
		//System.out.println("X = " + _absy);
		
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
