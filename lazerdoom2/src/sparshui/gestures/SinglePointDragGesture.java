package sparshui.gestures;

import java.util.Vector;

import sparshui.common.Event;

/**
 * 
 */
public class SinglePointDragGesture extends MultiPointDragGesture {

	@Override
	public String getName() {
		return "SinglePointDragGesture";
	}
	
	@Override
	protected Vector<Event> processMove(TouchData touchData) {
		if(_knownPoints.size() < 2) {
			return super.processMove(touchData);
		} else {
			adjustOffset();
			return null;
		}
	}

}
