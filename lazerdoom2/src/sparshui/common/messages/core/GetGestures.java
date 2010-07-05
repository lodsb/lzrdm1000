package sparshui.common.messages.core;

import sparshui.common.messages.Message;

/**
 * 
 */
public class GetGestures extends Message<GesturesMessage> {
	private int _groupID = 0;
	
	/**
	 * 
	 */
	public GetGestures() {
		super(true);
	}
	
	/**
	 * Construct a get gestures message.
	 * 
	 * @param groupID
	 * 		The group ID.
	 */
	public GetGestures(int groupID) {
		this();
		_groupID = groupID;
	}
	
	/**
	 * 
	 * @return
	 * 		The group ID
	 */
	public int getGroupID() {
		return _groupID;
	}
}
