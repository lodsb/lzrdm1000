package sparshui.common.messages.events;

import SceneItems.TouchItemInterface;

import com.trolltech.qt.core.QPointF;

import sparshui.common.Event;
import sparshui.common.utils.Converter;

public class DblClkEvent implements Event {
	private static final long serialVersionUID = -1643892133742179717L;
	
	private int _id;
	private float _x;
	private float _y;

	//default constructor
	public DblClkEvent() {
		_id = 0;
		_x = 0;
		_y = 0;
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

	
	//specific constructor
	/**
	 * 
	 * @param id
	 * @param x
	 * @param y
	 * @param state
	 */
	public DblClkEvent(int id, float x, float y) {
		_id = id;
		_x = x;
		_y = y;
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
	
	/**
	 *  Constructs a new DlbClkEvent from a serialized version
	 * 		- 4 bytes	: id
	 * 		- 4 bytes	: x
	 * 		- 4 bytes	: y
	 *		- 12 bytes total
	 *
	 * @param data the serialized version of touchEvent
	 */
	public DblClkEvent(byte[] data) {
		if (data.length < 12) {
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
		}
	}
	
	@Override
	public int getEventType() {
		return EventType.DBLCLK_EVENT.ordinal();
	}

	/**
	 * Constructs the data packet with this event data. Message format for this
	 * event:
	 * 		- 4 bytes	: event type
	 * 		- 4 bytes	: id
	 * 		- 4 bytes	: x
	 * 		- 4 bytes	: y
	 * 		- 16 bytes total
	 */
	public byte[] serialize() {
		byte[] data = new byte[16];
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

		System.out.print("DblClkEvent.serialize(): data: ");
		for(byte d : data){
			System.out.print(d+",");
		}
		System.out.println();
		
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
