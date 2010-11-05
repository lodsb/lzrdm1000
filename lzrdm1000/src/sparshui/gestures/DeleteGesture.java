package sparshui.gestures;

import java.util.HashMap;
import java.util.Vector;

import sparshui.common.Event;
import sparshui.common.Location;

import java.util.Vector;

import lazerdoom.LazerdoomConfiguration;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.gui.QPainterPath;
import com.trolltech.qt.gui.QPainterPath_Element;

import sparshui.common.Event;
import sparshui.common.messages.events.DeleteEvent;
import sparshui.common.messages.events.DragEvent;
import sparshui.common.messages.events.GroupEvent;
import sparshui.common.messages.events.TouchEvent;
import sparshui.common.path.TouchPath;
import sparshui.gestures.StandardDynamicGesture.TouchData;
import sparshui.server.TouchPoint;

/**
 * 
 */
public class DeleteGesture implements Gesture {
	private static HashMap<Integer, TouchPath> paths = new HashMap<Integer, TouchPath>();
	
	double maxDeviance = 0.025;
	@Override
	public String getName() {
		return "GroupGesture";
	}
	
	@Override
	public Vector<Event> processChange(Vector<TouchPoint> touchPoints, TouchPoint changedTouchPoint) {
		Vector<Event> events = new Vector<Event>();
		
		switch (changedTouchPoint.getState()){
		case BIRTH:
			TouchPath p0 = new TouchPath(changedTouchPoint.getLocation());
			paths.put(changedTouchPoint.getID(), p0);
			
			events.add(new DeleteEvent(changedTouchPoint.getLocation(), null,  true, false, changedTouchPoint.getID()));
			break;
			
		case MOVE:
			TouchPath p1 = paths.get(changedTouchPoint.getID());
			if(p1 != null) {
				p1.addPoint(changedTouchPoint.getLocation());
			}			
			
			events.add(new DeleteEvent(changedTouchPoint.getLocation(), null,  true, false, changedTouchPoint.getID()));
			
			break;
		case DEATH:
			QPointF crossPoint = null;
			boolean gestureSuccessful = false;
			
			TouchPath p2 = paths.get(changedTouchPoint.getID());
			if(p2 != null) {
				p2.addPoint(changedTouchPoint.getLocation());
				p2.simplify();
				crossPoint = p2.getIntersectionPoint();
				if(crossPoint != null) {
					if(p2.pathLenght() >= LazerdoomConfiguration.getInstance().minimumDeleteGestureLength) {
						if(p2.slopeAtStart()*p2.slopeAtEnd() < 0.0) {
							gestureSuccessful = true;
						}
					}
				}
			}
			
			events.add(new DeleteEvent(changedTouchPoint.getLocation(), crossPoint,  false, gestureSuccessful, changedTouchPoint.getID()));
			//System.out.println("Delete gesture!");
			
			break;
			
		}
		return events;
	}

	private QPointF parsePath(QPainterPath p2) {
		/*Location loc = new Location(0,0);
		// gesture form is like
		//     ___
		//     \ /
		//      X crosspoint
		//     /  \
		//    /    \
		//  start  end
		//
		// or flipped horizontally/vertically
		//
		
		
		// check slopes - have to be converse
		if(Math.signum(p2.slopeAtPercent(1.0)) != Math.signum(p2.slopeAtPercent(0.0))) {
			System.out.println("YEAH");
			
		}  
		System.out.println(p2);
		QPainterPath p3 = p2.toReversed().intersected(p2);
		System.out.println(p3);
		if(Math.signum(p2.slopeAtPercent(1.0)) != Math.signum(p2.slopeAtPercent(0.0))) {
			System.out.println("YEAH");
			
		}  
		int j = 0;
		for(int i = 0; i < p3.elementCount(); i++) {
			QPainterPath_Element e = p3.elementAt(i);
			if(e.isMoveTo() && j != 1) {
				loc = new Location((float)e.toPoint().x(), (float)e.toPoint().y());
				j++;
			}
		}
		
		return loc;*/
		return null;
	}

	@Override
	public GestureType getGestureType() {
		return GestureType.GROUP_GESTURE;
	}
	
}