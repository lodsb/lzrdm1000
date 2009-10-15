package sparshui.gestures;

import java.util.Vector;

import sparshui.common.Event;
import sparshui.common.messages.events.DragEvent;
import sparshui.common.messages.events.ZoomEvent2D;
import sparshui.gestures.StandardDynamicGesture.TouchData;

public class ZoomGesture2D extends MultiPointDragGesture {

	@Override
	public String getName() {
		return "ZoomGesture2D";
	}
	
	public GestureType getGestureType() {
		return GestureType.ZOOM_GESTURE2D;
	}
	
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
		} else if (currentTouchPoints == 2 && p1Data != null) {
			p2Data = touchData;
			
			origDistance = 	Math.sqrt((
							(p1Data.getLocation().getX() -
							p2Data.getLocation().getX())*
							(p1Data.getLocation().getX() -
							p2Data.getLocation().getX())
							) +
							(
							(p1Data.getLocation().getY() -
							p2Data.getLocation().getY())*
							(p1Data.getLocation().getY() -
							p2Data.getLocation().getY())
							));
						
			System.out.println("ZOOM init "+origDistance);
			
		} else {
			p1Data = null;
			p2Data = null;
		}
		
		return null;
	}
	
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
			
			double newDistance = Math.sqrt((
							(p1Data.getLocation().getX() -
							p2Data.getLocation().getX())*
							(p1Data.getLocation().getX() -
							p2Data.getLocation().getX())
							) +
							(
							(p1Data.getLocation().getY() -
							p2Data.getLocation().getY())*
							(p1Data.getLocation().getY() -
							p2Data.getLocation().getY())
							)); 
			
			double xEdgeLength = Math.abs((p1Data.getLocation().getX()+p2Data.getLocation().getX()));
			double yEdgeLength = Math.abs((p1Data.getLocation().getY()+p2Data.getLocation().getY()));
			
			double edgeLength = xEdgeLength+yEdgeLength;
			
			double globalZoomFactor = newDistance/origDistance;
			

			double xZoom = (xEdgeLength/edgeLength)*globalZoomFactor;
			double yZoom = (yEdgeLength/edgeLength)*globalZoomFactor;
			
			System.out.println("ZOOM update "+origDistance+" "+newDistance+" el "+edgeLength+" xel "+xEdgeLength+" yel "+yEdgeLength);
			System.out.println("ZOOM zoom "+xZoom+" "+yZoom);
			
			
			events = new Vector<Event>();
			events.add(new ZoomEvent2D(xZoom, yZoom, p1Data.getLocation(), true, true, p1Data.getUniqueID()));
		}
		
		return events;
	}
	
	@Override
	protected Vector<Event> processDeath(TouchData touchData) {
		currentTouchPoints--;
		System.out.println("ZOOM DEATH" + currentTouchPoints);
		return null;
	}
}
