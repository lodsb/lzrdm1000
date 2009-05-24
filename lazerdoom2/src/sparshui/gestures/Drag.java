package sparshui.gestures;

import java.util.Vector;

import sparshui.common.Event;
import sparshui.common.messages.events.DragEvent;

/**
 * 
 */
public class Drag extends StandardDynamicGesture {
	
	/**
	 * 
	 */
	public Drag() {
		super();
	}

	@Override
	public String getName() { return "sparshui.gestures.Drag"; }
	
	@Override
	public GestureType getGestureType() {
		return GestureType.DRAG_GESTURE;
	}

	@Override
	protected Vector<Event> processBirth(TouchData touchData) {
		return null; // Ignore, no work to be done
	}

	@Override
	protected Vector<Event> processDeath(TouchData touchData) {
		return null; // Ignore, no work to be done
	}

	@Override
	protected Vector<Event> processMove(TouchData touchData) {
		Vector<Event> events = new Vector<Event>();
		float x = _newCentroid.getX() - _oldCentroid.getX();
		float y = _newCentroid.getY() - _oldCentroid.getY();
		if(x != 0 || y != 0) events.add(new DragEvent(x, y));
		return events;
	}

}