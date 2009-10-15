package sparshui.gestures;

import java.util.HashMap;
import java.util.Vector;

import lazerdoom.LazerdoomConfiguration;

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
	
	int currentPathLenght = 0;
	
	HashMap<Integer, Integer> pathLenghts = new HashMap<Integer, Integer>();
	
	protected Vector<Event> processBirth(TouchData touchData) {
		pathLenghts.put(touchData.getUniqueID(), 0);
		return null;
	}
	
	@Override
	protected Vector<Event> processMove(TouchData touchData) {
		Integer currentLenght = pathLenghts.get(touchData.getUniqueID());
		Vector<Event> events = null;
		
		if(currentLenght != null) {
			if(currentLenght % LazerdoomConfiguration.gestureRecognitionPathUpdateResolution == 0) {
				events = new Vector<Event>();
				
				events.add(new DragEvent(touchData.getLocation(), true, false, touchData.getUniqueID()));
			}
			
			pathLenghts.put(touchData.getUniqueID(), ++currentLenght);
		}	
			/*if(_knownPoints.size() < 2 && currentPathLenght % LazerdoomConfiguration.gestureRecognitionPathUpdateResolution == 0) {
			return super.processMove(touchData);
		} else {
			adjustOffset();
			return null;
		}
		*/
		
		return events;
	}
	
	@Override
	protected Vector<Event> processDeath(TouchData touchData) {
		Vector<Event> events = new Vector<Event>();
		DragEvent e = new DragEvent(touchData.getLocation(), false, true, touchData.getUniqueID());
		
		pathLenghts.remove(touchData.getUniqueID());
		events.add(e);
		return events;
	}

}
