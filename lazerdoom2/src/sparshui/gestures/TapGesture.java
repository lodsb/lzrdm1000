package sparshui.gestures;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import sparshui.common.Event;
import sparshui.common.Location;
import sparshui.common.messages.events.RotateEvent;
import sparshui.common.messages.events.TapEvent;
import sparshui.server.TouchPoint;

/**
 * 
 */
public class TapGesture extends StandardDynamicGesture {

		private static double maxDeviance = 0.025*0.025;
		private HashMap<Integer, Location> currentTp = new HashMap<Integer, Location>();
	
		protected Vector<Event> processBirth(TouchData touchData) {
			currentTp.put(touchData.getUniqueID(),touchData.getLocation());
			return null;
		}
		
		@Override
		protected Vector<Event> processMove(TouchData touchData) {
			return null;
		}
		
		@Override
		protected Vector<Event> processDeath(TouchData touchData) {
			Vector<Event> events = null;
			
			Location loc = null;
			
			if((loc = currentTp.get(touchData.getUniqueID())) != null) {
				double x = touchData.getLocation().getX();
				double y = touchData.getLocation().getY();
				
				double distance = (double)(x-loc.getX())*(x-loc.getX())+(y-loc.getY())*(y-loc.getY());
				if(distance <= maxDeviance) {
					events = new Vector<Event>();
					events.add(new TapEvent(touchData.getUniqueID(), (float)x,(float)y));
				}
				
				currentTp.remove(touchData.getUniqueID());
			}
			
			return events;
		}


	@Override
	public String getName() {
		return "TapGesture";
	}
	
	@Override
	public GestureType getGestureType() {
		return GestureType.TAP_GESTURE;
	}
}
