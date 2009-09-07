package sparshui.gestures;

import java.util.Vector;

import sparshui.common.Event;
import sparshui.common.messages.events.DragEvent;
import sparshui.gestures.StandardDynamicGesture.TouchData;

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
	
	@Override
	protected Vector<Event> processDeath(TouchData touchData) {
		Vector<Event> events = new Vector<Event>();
		DragEvent e = new DragEvent(_offsetCentroid, false, true, touchData.getUniqueID());
		super.processDeath(touchData);
		events.add(e);
		return events;
	}

}
