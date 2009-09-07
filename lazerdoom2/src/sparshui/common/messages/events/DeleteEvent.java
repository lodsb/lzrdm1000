package sparshui.common.messages.events;

import com.trolltech.qt.core.QPointF;

import sparshui.common.Location;

public class DeleteEvent extends ExtendedGestureEvent {

	private static final long serialVersionUID = 1337117117L;
	private QPointF crossPoint = null;
	
	public DeleteEvent(Location loc, QPointF crossPoint,  boolean isOng, boolean isSucc, int id) {
		super(loc);
		this.setTouchID(id);
		this.setFlags(isOng, isSucc);
		this.crossPoint = crossPoint;
	}	
	
	public QPointF getCrossPoint() {
		return this.crossPoint;
	}
	
	private QPointF sceneCrossPoint;
	
	public void setSceneCrossPoint(QPointF point) {
		this.sceneCrossPoint = point;
	}
	
	public QPointF getSceneCrossPoint() {
		return this.sceneCrossPoint;
	}
	
	@Override
	public int getEventType() {
		return EventType.DELETE_EVENT.ordinal();
	}

	@Override
	public byte[] serialize() {
		// TODO Auto-generated method stub
		return null;
	}

}
