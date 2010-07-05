package sparshui.common.messages.core;

import sparshui.common.messages.Message;

/**
 * 
 */
public class GesturesMessage extends Message<Void> {
	private String[] _gestures;
	
	/**
	 * 
	 */
	public GesturesMessage() {
		_gestures = null;
	}
	
	/**
	 * Construct a new get gestures message with the
	 * given gestures.
	 * 
	 * @param gestures
	 * 		The gestures.
	 */
	public GesturesMessage(String[] gestures) {
		_gestures = gestures;
	}
	
	/**
	 * 
	 * @return
	 * 		The gestures available.
	 */
	public String[] getGestures() {
		return _gestures;
	}
}