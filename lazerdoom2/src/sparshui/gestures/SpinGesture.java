package sparshui.gestures;

import java.util.Iterator;
import java.util.Vector;

import sparshui.common.Event;
import sparshui.common.messages.events.*;
import sparshui.server.TouchPoint;

public class SpinGesture extends StandardDynamicGesture {
	public enum AXIS {
		XAXIS, YAXIS, ZAXIS
	}

	private final double EPSSPIN = 10.0f;

	private Vector<TouchPoint> _axisPoints;
	MultiPointDragGesture _multiPointDragGesture;
	AXIS _fixedAxis;

	public SpinGesture() {
		_axisPoints = new Vector<TouchPoint>();
		_fixedAxis = AXIS.ZAXIS; /*
									 * default spin is rotate, which is rotate
									 * about z axis
									 */
		_multiPointDragGesture = new MultiPointDragGesture();
	}

	@Override
	public String getName() {
		return "SpinGesture";
	}
	
	@Override
	public GestureType getGestureType() {
		return GestureType.SPIN_GESTURE;
	}

	@Override
	protected Vector<Event> processBirth(TouchData touchData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Vector<Event> processDeath(TouchData touchData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Vector<Event> processMove(TouchData touchData) {
		// TODO Auto-generated method stub
		return null;
	}

	protected void handleAxisPointBirth(TouchPoint touchPoint) {
		float dx, dy;
		switch (_axisPoints.size()) {
		case 1:
			/* calculate the fixed axis */
			dx = Math.abs(touchPoint.getLocation().getX()
					- _axisPoints.elementAt(0).getLocation().getX());
			dy = Math.abs(touchPoint.getLocation().getY()
					- _axisPoints.elementAt(0).getLocation().getY());
			if (Math.abs(dx) > Math.abs(dy)) {
				_fixedAxis = AXIS.YAXIS;
			} else {
				_fixedAxis = AXIS.XAXIS;
			}
			/* fall through */
		case 0:
			_axisPoints.add(touchPoint);
		default:
			/* do nothing */
			;
		}
	}

	protected boolean isAxisPoint(TouchPoint touchPoint) {
		Iterator<TouchPoint> touchPointIterator = _axisPoints.iterator();
		while (touchPointIterator.hasNext()) {
			if (touchPointIterator.next().getID() == touchPoint.getID()) {
				return true;
			}
		}
		return false;
	}

	public Vector<Event> processChange(Vector<TouchPoint> touchPoints,
			TouchPoint touchPoint) {
		/*
		 * In the case of the birth of a point, see whether the axis has been
		 * set, if it has, then handle it as the dragGesture's birth. In the
		 * case of a move, if the axisPoint is moved, ignore it. In the case of
		 * a death, if the axisPoint dies, then destroy the gesture
		 */
		if (_axisPoints.size() < 2 || isAxisPoint(touchPoint)) {
			switch (touchPoint.getState()) {
			case BIRTH:
				handleAxisPointBirth(touchPoint);
				break;
			case MOVE:
				/* do nothing */
				break;
			case DEATH:
				/* delete _multiPointDragGesture */
				// _multiPointDragGesture = null;
			}
			return new Vector<Event>();
		} else {
			/* the changed touch point corresponds to the multiPointDrag */
			Vector<Event> ev = _multiPointDragGesture.processChange(
					touchPoints, touchPoint);
			int i = 0;

			/* Get the drag events, if any, and convert them to spin */
			for (i = 0; i < ev.size(); ++i) {
				if (ev.elementAt(i) instanceof DragEvent) {
					ev.insertElementAt(dragToSpin(ev.elementAt(i)), i);
					ev.remove(i+1);
				}
			}
			return ev;
		}
	}

	protected Event dragToSpin(Event event) {
		float xTheta, yTheta;
		if (event instanceof DragEvent) {

			switch (_fixedAxis) {
			case XAXIS:
				yTheta = (float) (((DragEvent) event).getAbsY() * EPSSPIN);
				return new SpinEvent((float) 0, yTheta, (float)0);
			case YAXIS:
				xTheta = (float) (((DragEvent) event).getAbsX() * EPSSPIN);
				return new SpinEvent(xTheta, 0, 0);
			default:
				return new SpinEvent(0, 0, 0);
			}
		} else {
			return null;
		}
	}

}
