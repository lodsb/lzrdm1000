package sparshui.common.messages.events;

import GUI.Multitouch.*;

import com.trolltech.qt.core.QPointF;

import sparshui.common.Event;
import sparshui.common.Location;
import sparshui.common.utils.Converter;

public class DragEvent extends ExtendedGestureEvent {

	public DragEvent(Location loc, boolean isOng, boolean isSucc, int id) {
		super(loc);
		this.setTouchID(id);
		this.setFlags(isOng, isSucc);
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