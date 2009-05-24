package sparshui.common.messages.core;

import sparshui.common.messages.Message;

/**
 * 
 */
public class GetGroupID extends Message<GroupIDMessage> {
	private float _x = 0;
	private float _y = 0;
	
	/**
	 * 
	 */
	public GetGroupID() {
		super(true);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public GetGroupID(float x, float y) {
		this();
		_x = x;
		_y = y;
	}
	
	/**
	 * 
	 * @return
	 * 		The x value.
	 */
	public float getX() {
		return _x;
	}
	
	/**
	 * 
	 * @return
	 * 		The y value.
	 */
	public float getY() {
		return _y;
	}

}
