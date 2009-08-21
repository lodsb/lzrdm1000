package sparshui.common.messages.events;

import sparshui.common.Event;
import sparshui.common.Location;
import GUI.Multitouch.TouchItemInterface;

import com.trolltech.qt.core.QPointF;


public abstract class ExtendedGestureEvent implements Event {
	private QPointF _pos = null;
	private Location loc;
	
	public ExtendedGestureEvent(Location loc) {
		this.loc = loc;
	}
	
	public double getRelX() {
		return this.loc.getX();
	}
	
	public double getRelY() {
		return this.loc.getY();
	}

	public void setSceneLocation(QPointF pos) {
		_pos = pos;
	}
	public QPointF getSceneLocation() {
		return _pos;
	}
	
	private TouchItemInterface item = null;
	
	public TouchItemInterface getSource() {
		return item;
	}
	
	public void setSource(TouchItemInterface it) {
		item = it;
	}
	
	private boolean isSuccessful = false;
	private boolean isOngoing = false;
	
	public void setFlags(boolean isOng, boolean isSucc) {
		this.isSuccessful = isSucc;
		this.isOngoing = isOng;
	}
	
	public boolean isOngoing() {
		return this.isOngoing;
	}
	
	public boolean isSuccessful() {
		return this.isSuccessful;
	}
}
