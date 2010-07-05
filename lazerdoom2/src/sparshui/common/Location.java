package sparshui.common;

import java.io.Serializable;

/**
 * Represents a 2D location with float values.
 * 
 * @author Jay Roltgen
 */
public class Location implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3472243250219991476L;
	private float _x;
	private float _y;

	/**
	 * Cosntruct a default location.  Values are initialized
	 * as the coordinates (0, 0).
	 */
	public Location() {
		_x = 0;
		_y = 0;
	}
	
	/**
	 * Construct a specific location.
	 * @param x
	 * 		The x coordinate value of the location.
	 * @param y
	 * 		The y coordinate value of the location.
	 */
	public Location(float x, float y) {
		_x = x;
		_y = y;
	}
	
	/**
	 * 
	 * @return
	 * 		The x coordinate value.
	 */
	public float getX() {
		return _x;
	}
	
	/**
	 * 
	 * @return
	 * 		The y coordinate value.
	 */
	public float getY() {
		return _y;
	}
	
	public String toString() {
		return "x = " + _x + ", y = " + _y;
	}
	
}
