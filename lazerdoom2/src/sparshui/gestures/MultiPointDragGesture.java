package sparshui.gestures;

import java.util.Vector;

import sparshui.common.Event;
import sparshui.common.Location;
import sparshui.common.messages.events.DragEvent;

/**
 * 
 */
public class MultiPointDragGesture extends StandardDynamicGesture {

	/**
	 * 
	 */
	protected Location _offset = null;

	protected Location _offsetCentroid = null;
	
	/**
	 * 
	 */
	public MultiPointDragGesture() {
		super();
	}

	@Override
	public String getName() {
		return "DragGesture";
	}
	
	@Override
	public GestureType getGestureType() {
		return GestureType.MULTI_POINT_DRAG_GESTURE;
	}

	@Override
	protected Vector<Event> processBirth(TouchData touchData) {
		if(_offset == null) {
			_offset = new Location(0,0);
			_offsetCentroid = _newCentroid;
		} else {
			adjustOffset();
		}
		return null;
	}

	@Override
	protected Vector<Event> processMove(TouchData touchData) {
		Vector<Event> events = new Vector<Event>();
		updateOffsetCentroid();
		//System.out.println("Drag processing move: x: " + _offsetCentroid.getX() + ", y: " + _offsetCentroid.getY());
		DragEvent e = new DragEvent(_offsetCentroid.getX(), _offsetCentroid.getY());
		e.setTouchID(touchData.getUniqueID());
		events.add(e);
		return events;
	}

	@Override
	protected Vector<Event> processDeath(TouchData touchData) {
		Vector<Event> events = null;
		if(_knownPoints.size() == 0) {
			_offset = null;
			_offsetCentroid = null;
		} else {
			adjustOffset();
		}
		/*DragEvent e = new DragEvent(_offsetCentroid.getX(), _offsetCentroid.getY());
		e.setTouchID(touchData.getUniqueID());
		events.add(e);*/
		return events;
	}
	
	/**
	 * 
	 */
	protected void adjustOffset() {
		_offset = new Location(
				_newCentroid.getX() - _oldCentroid.getX() + _offset.getX(),
				_newCentroid.getY() - _oldCentroid.getY() + _offset.getY()
		);
	}
	
	/**
	 * 
	 */
	protected void updateOffsetCentroid() {
		float x = _newCentroid.getX() - _offset.getX();
		float y = _newCentroid.getY() - _offset.getY();
		_offsetCentroid = new Location(x, y);
	}

}