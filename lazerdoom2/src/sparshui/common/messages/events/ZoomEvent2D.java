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
	
	private double xZoom = 1.0;
	private double yZoom = 1.0;
	public double getXZoom() {
		return xZoom;
	}
	
	public double getYZoom() {
		return yZoom;
	}
	
	public ZoomEvent2D(double xZoom, double yZoom, Location loc, boolean isOng, boolean isSucc, int id) {
		super(loc);
		this.setTouchID(id);
		this.setFlags(isOng, isSucc);
		this.xZoom = xZoom;
		this.yZoom = yZoom;
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
