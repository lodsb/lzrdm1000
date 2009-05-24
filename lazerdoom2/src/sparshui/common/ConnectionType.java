package sparshui.common;

public enum ConnectionType {
	CLIENT(0),
	INPUT_DEVICE(1);
	
	private final int _value;
	
	/**
	 * Create a new connection type of the specified value.
	 * @param value
	 */
	ConnectionType(int value) {
		_value = value;
	}
	
	/**
	 * Return the integer value of this connection type.
	 * @return
	 * 		The integer value for this connection type.
	 */
	public int value() {
		return _value;
    }
}
