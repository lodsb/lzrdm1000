package sparshui.common.messages.core;

import sparshui.common.messages.Message;

public class GroupIDMessage extends Message<Void> {
	private int _groupID;
	
	/**
	 * 
	 */
	public GroupIDMessage() {
		_groupID = 0;
	}
	
	/**
	 * Construct a groupID message
	 * @param groupID
	 * 		The group ID.
	 *  		
	 */
	public GroupIDMessage(int groupID) {
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
