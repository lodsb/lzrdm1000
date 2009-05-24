package sparshui.gestures;


import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import sparshui.common.Event;
import sparshui.common.Location;
import sparshui.common.messages.events.ZoomEvent;
import sparshui.server.TouchPoint;

public class ZoomGesture extends StandardDynamicGesture {

	private float _scale;
	protected Location _offset = null;
	protected Location _offsetCentroid = null;

	@Override
	public String getName() {
		return "ZoomGesture";
	}
	
	@Override
	public GestureType getGestureType() {
		return GestureType.ZOOM_GESTURE;
	}
	
	@Override
	protected TouchData createTouchData(TouchPoint touchPoint) {
		return new ZoomData(touchPoint.getLocation());
	}

	@Override
	protected Vector<Event> processBirth(TouchData touchData) {
		if (_offset == null) {
			_offset = new Location(0, 0);
			_offsetCentroid = _newCentroid;
		} else {
			adjustOffset();
		}
		calculateScaleChange();
		return null;
	}

	@Override
	protected Vector<Event> processDeath(TouchData touchData) {
		if(_knownPoints.size() == 0) {
			_offset = null;
			_offsetCentroid = null;
		} else {
			adjustOffset();
		}
		calculateScaleChange();
		return null;
	}

	@Override
	protected Vector<Event> processMove(TouchData touchData) {
		Vector<Event> events = new Vector<Event>();
		adjustOffset();
		updateOffsetCentroid();
		_scale = calculateScaleChange();
		events.add(new ZoomEvent(_scale, _newCentroid));
		return events;
	}
	
	private float calculateScaleChange() {
		float retScale = 0;
		Collection<TouchData> touchPoints = _knownPoints.values();
		Iterator<TouchData> touchDataIterator = touchPoints.iterator();
		while (touchDataIterator.hasNext()) {
			TouchData pointData = touchDataIterator.next();
			ZoomData pointZoomData = null;
			//System.out.println("Processing Zoom");
			if (pointData instanceof ZoomData) {
				//System.out.println("Cast successful");
				pointZoomData = (ZoomData) pointData;
				float distance = (float) Math.hypot(_newCentroid.getX() - pointData.getLocation().getX(), _newCentroid.getY() - pointData.getLocation().getY());
				pointZoomData.setDistance(distance);
				//System.out.println("Point distance: " + pointZoomData.getDistance() + ", Old Distance: " + pointZoomData.getOldDistance());


				//System.out.println("New Centroid: " + _newCentroid.toString());
				//System.out.println("Point: + " + pointZoomData.getDistance());
				retScale += pointZoomData.getDistance() / pointZoomData.getOldDistance();

			}
		}
		retScale = retScale / _knownPoints.size();
		if (retScale < 5 && retScale > 0.2) {
			//System.out.println("Scale: " + retScale);
			return retScale;
		}
		else {
			return 1;
		}
	}

	/**
	 * 
	 */
	protected void adjustOffset() {
		_offset = new Location(_newCentroid.getX() - _oldCentroid.getX(),
				_newCentroid.getY() - _oldCentroid.getY());
	}

	/**
	 * 
	 */
	protected void updateOffsetCentroid() {
		float x = _newCentroid.getX() - _offset.getX();
		float y = _newCentroid.getY() - _offset.getY();
		_offsetCentroid = new Location(x, y);
	}
	
	/**
	 * 
	 */
	protected class ZoomData extends TouchData {
		private float _distance;
		private float _oldDistance;
		public ZoomData(Location location) {
			super(location);
			_oldDistance = _distance = (float)Math.hypot(location.getX() - _newCentroid.getX(), location.getY() - _newCentroid.getY());
			//System.out.println("[ZoomGesture] Zoom Event Constructed, Distance = " + _distance);
		}
		public float getDistance() {
			return _distance;
		}
		public float getOldDistance() {
			return _oldDistance;
		}
		public void setDistance(float distance) {
			_oldDistance = _distance;
			_distance = distance;
		}
	}

	/*
	 * @Override public Vector<Event> processChange(Vector<TouchPoint>
	 * touchPoints, TouchPoint changedTouchPoint) { Vector<Event> retEvents =
	 * new Vector<Event>(); // If this is the first update, initialize the
	 * known points if (_knownPoints.size() == 0) { _sumX = 0; _sumY = 0; for
	 * (TouchPoint touchPoint : touchPoints) { addTouchPoint(touchPoint); }
	 * _oldCentroid = new Location(_sumX, _sumY); _newCentroid = new
	 * Location(_sumX, _sumY); } else { // A touch point has been changed.
	 * Recalculate the centroid // and fire a drag event if a point has moved
	 * 
	 * _oldCentroid = _newCentroid; _sumX = _oldCentroid.getX() *
	 * _knownPoints.size(); _sumY = _oldCentroid.getY() * _knownPoints.size(); //
	 * Process a birth if (changedTouchPoint.getState() == TouchState.BIRTH) {
	 * processBirth(changedTouchPoint); } // Process a move else if
	 * (changedTouchPoint.getState() == TouchState.MOVE) {
	 * processMove(changedTouchPoint); _scale = getScaleChange(touchPoints);
	 * retEvents.add(new ZoomEvent(_scale, _newCentroid)); return retEvents; } //
	 * Process a death else if (changedTouchPoint.getState() ==
	 * TouchState.DEATH) { processDeath(changedTouchPoint); } } return
	 * retEvents; }
	 * 
	 * private float getScaleChange(Vector<TouchPoint> touchPoints) { float
	 * scale = 0; for (TouchPoint touchPoint : touchPoints) { float distance1 =
	 * getDistance(touchPoint, _newCentroid); float distance2 =
	 * getDistance(touchPoint, _oldCentroid); scale += distance1 / distance2; }
	 * return scale; }
	 * 
	 * private Float getDistance(TouchPoint touchPoint, Location location) {
	 * float x = touchPoint.getLocation().getX() - location.getX(); float y =
	 * touchPoint.getLocation().getY() - location.getY(); return
	 * (float)Math.hypot(x, y); }
	 */
}
