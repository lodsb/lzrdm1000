package sparshui.gestures;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import sparshui.common.Event;
import sparshui.common.Location;
import sparshui.common.messages.events.RotateEvent;
import sparshui.server.TouchPoint;

/**
 * 
 */
public class RotateGesture extends StandardDynamicGesture {

		int currentTouchPoints = 0;
			
		TouchData p1Data = null;
		TouchData p2Data = null;
		
		
		double origDistance = 1.0;
		double origXDist = 1.0;
		double origYDist = 1.0;
		
		protected Vector<Event> processBirth(TouchData touchData) {
			currentTouchPoints++;
			
			// two touchpoints -> origin for zoom
			if(currentTouchPoints == 1) {
				p1Data = touchData;
				p2Data = null;
			} else if (currentTouchPoints == 2) {
				p2Data = touchData;
				if(p1Data.getLocation().getY() > p2Data.getLocation().getY()) {
					TouchData xchg = p1Data;
					p1Data = p2Data;
					p2Data = xchg;
				}
				
			} else {
				p1Data = null;
				p2Data = null;
			}
			
			return null;
		}
		
		double currentAngle = 0.0;
		
		@Override
		protected Vector<Event> processMove(TouchData touchData) {
			Vector<Event> events = null;
			if(p1Data != null && p2Data != null) {
				double xDA = 1.0;
				double yDA = 1.0;	
				
				if(p1Data.getUniqueID() == touchData.getUniqueID()) {
					p1Data = touchData;
				} else if(p2Data.getUniqueID() == touchData.getUniqueID()) {
					p2Data = touchData;
				}
				double y1 = p1Data.getLocation().getY();
				double y2 = p2Data.getLocation().getY();
				
				double x1 = p1Data.getLocation().getX();
				double x2 = p2Data.getLocation().getX();
				
				double angle = (Math.atan2(y2 - y1, x2 - x1) * 180 / Math.PI)-90.0;
				currentAngle = angle;
				System.out.println("ANGLE: "+angle);
				events = new Vector<Event>();
				events.add(new RotateEvent((float)angle, p1Data.getLocation(), p1Data.getUniqueID(), true));
				//events.add(new ZoomEvent2D(xZoom, yZoom, p1Data.getLocation(), true, true, p1Data.getUniqueID()));
			}
			
			return events;
		}
		
		@Override
		protected Vector<Event> processDeath(TouchData touchData) {
			Vector<Event> events = null;
			if(currentTouchPoints == 2) {
				events = new Vector<Event>();
				events.add(new RotateEvent((float)currentAngle, p1Data.getLocation(), p1Data.getUniqueID(), false));
			}
			currentTouchPoints--;
			System.out.println("ZOOM DEATH" + currentTouchPoints);
			return events;
		}


	@Override
	public String getName() {
		return "RotateGesture";
	}
	
	@Override
	public GestureType getGestureType() {
		return GestureType.ROTATE_GESTURE;
	}
}
