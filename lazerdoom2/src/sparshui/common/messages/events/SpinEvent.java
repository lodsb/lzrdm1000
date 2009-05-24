package sparshui.common.messages.events;

import SceneItems.TouchItemInterface;

import com.trolltech.qt.core.QPointF;

import sparshui.common.Event;

public class SpinEvent implements Event {
	private static final long serialVersionUID = 6394319277586792988L;
	
	private float _rotationX;
	private float _rotationY;
	private float _rotationZ;
	
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
		return EventType.SPIN_EVENT.ordinal();
	}
	
	public SpinEvent(){	
		_rotationX = (float) 0.0;
		_rotationY = (float) 0.0;
		_rotationZ = (float) 0.0;
	}
	
	public SpinEvent(float rotationX, float rotationY, float rotationZ){
		_rotationX = rotationX;
		_rotationY = rotationY;
		_rotationZ = rotationZ;
	}
	
	public float getRotationX(){
		return _rotationX;
	}
	public float getRotationY(){
		return _rotationY;
	}
	public float getRotationZ(){
		return _rotationZ;
	}

	public void setRotationX(float rotation){
		_rotationX = rotation;
	}
	public void setRotationY(float rotation){
		_rotationY = rotation;
	}
	public void setRotationZ(float rotation){
		_rotationZ = rotation;
	}

	@Override
	public byte[] serialize() {
		// TODO Auto-generated method stub
		System.err.println("Serialize() not implemented for spinEvent()");
		return null;
	}
	
	private TouchItemInterface item = null;
	
	@Override
	public TouchItemInterface getSource() {
		return item;
	}
	@Override
	public void setSource(TouchItemInterface it) {
		item = it;
	}	
}
