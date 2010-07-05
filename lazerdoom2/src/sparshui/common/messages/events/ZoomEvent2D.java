package sparshui.common.messages.events;

import sparshui.common.Location;

public class ZoomEvent2D extends ExtendedGestureEvent {
	private boolean isFocused = false;
	
	@Override
	public boolean isFocused() {
		return this.isFocused;
	}
	
	@Override
	public void setFocused() {
		this.isFocused = true;
	}

	private double angle = 0.0;

	public double getPinchAngle() {
		return angle;
	}
	
	private double globalZoom = 1.0;
	public double getGlobalZoom() {
		return globalZoom;
	}
	
	public ZoomEvent2D(double angle, double globalZoom, Location loc, boolean isOng, boolean isSucc, int id) {
		super(loc);
		this.setTouchID(id);
		this.setFlags(isOng, isSucc);
		
		this.angle = angle;
		this.globalZoom = globalZoom;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4404058051493060641L;

	@Override
	public int getEventType() {
		return EventType.DRAG_EVENT.ordinal();
	}

	@Override
	public byte[] serialize() {
		// TODO Auto-generated method stub
		return null;
	}
}
