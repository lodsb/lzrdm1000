package sparshui.common.messages.events;

import GUI.Multitouch.*;

import com.trolltech.qt.core.QPointF;

import sparshui.common.Event;
import sparshui.common.Location;
import sparshui.common.utils.Converter;

public class GroupEvent extends ExtendedGestureEvent {
	private static final long serialVersionUID = -2305607021385835330L;
	
	private float _absx;
	private float _absy;
	private int id;
	public void setTouchID(int id) {
		this.id = id;
	}
	public int getTouchID() {
		return this.id;
	}
			
	public GroupEvent(Location loc, boolean isOng, boolean isSucc, int id) {
		super(loc);
		this.id = id;
		this.setFlags(isOng, isSucc);
	}	
	
	private QPointF _pos = null;
	@Override
	public void setSceneLocation(QPointF pos) {
		_pos = pos;
	}
	@Override
	public QPointF getSceneLocation() {
		return _pos;
	}


	@Override
	public int getEventType() {
		return EventType.GROUP_EVENT.ordinal();
	}

	@Override
	public String toString() {
		String ret = "Group Event: absx = " + _absx + ", absy = " + _absy+" ongoing "+this.isOngoing()+" succ "+this.isSuccessful()+" uid "+this.id;
		return ret;
	}
	@Override
	public byte[] serialize() {
		return null;
	}
}

