package sparshui.gestures;

import java.util.Vector;

import sparshui.common.Event;
//import sparshui.common.Location;
import sparshui.common.TouchState;
//import sparshui.common.events.DragEvent;
import sparshui.common.messages.events.TouchEvent;

/**
 *
 */
public class CompositeTouchGesture extends MultiPointDragGesture {

	/**
	 * 
	 */
	public CompositeTouchGesture() {
		super();
	}

	@Override
	public String getName() {
		return "CompositeTouchGesture";
	}

	@Override
	protected Vector<Event> processBirth(TouchData touchData) {
		Vector<Event> events = new Vector<Event>();
		super.processBirth(touchData);
		if(_knownPoints.size() == 1) {
			events.add(new TouchEvent(0, _offsetCentroid.getX(), _offsetCentroid.getY(), TouchState.BIRTH));
		}
		return events;
	}

	@Override
	protected Vector<Event> processMove(TouchData touchData) {
		Vector<Event> events = new Vector<Event>();
		updateOffsetCentroid();
		events.add(new TouchEvent(0, _offsetCentroid.getX(), _offsetCentroid.getY(), TouchState.MOVE));
		return events;
	}

	@Override
	protected Vector<Event> processDeath(TouchData touchData) {
		Vector<Event> events = new Vector<Event>();
		if(_knownPoints.size() == 0) {
			events.add(new TouchEvent(0, _offsetCentroid.getX(), _offsetCentroid.getY(), TouchState.DEATH));
		}
		super.processDeath(touchData);
		return events;
	}
	
}
