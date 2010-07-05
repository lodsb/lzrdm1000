package sparshui.server;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;

import sparshui.common.Location;

/**
 * Represents a network connection to a client.
 * 
 * @author Tony Ross
 * 
 */
public class ClientConnection {

	/**
	 * 
	 */
	private ServerToClientProtocol _protocol;

	/**
	 * 
	 */
	private HashMap<Integer, Group> _groups;

	/**
	 * Instantiate the connection on the specified socket.
	 * 
	 * @param socket
	 * 		The socket that has been opened to the client.
	 * @throws IOException
	 * 		If there is a communication error.
	 */
	public ClientConnection(Socket socket) throws IOException {
		_protocol = new ServerToClientProtocol(socket);
		_groups = new HashMap<Integer, Group>();
	}

	/**
	 * Process a touch point birth.
	 * @param touchPoint
	 * 		The touch point to process.
	 * @return
	 * 		True if a group was found for the touch point,
	 * 		false otherwise.
	 * @throws IOException
	 * 		If there is a communication error.
	 */
	public boolean processBirth(TouchPoint touchPoint) throws IOException {

		int groupID = getGroupID(touchPoint.getLocation());
		System.out.println("[ClientConnection] Has GroupID: " + groupID);
		Group group = getGroup(groupID);
		if (group != null) {
			// Client claims point
			//System.out.println("Client claims point");
			touchPoint.setGroup(group);
			return true;
		} else {
			System.out.println("[ClientConnection] Client did not claim point");
			// Client does not claim point
			return false;
		}
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	private Vector<Integer> getGestures(int groupID) throws IOException {
		return _protocol.getGestures(groupID);
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	private int getGroupID(Location location) throws IOException {
		return _protocol.getGroupID(location);
	}

	/**
	 * 
	 * @param groupID
	 * @return
	 * @throws IOException
	 */
	private Group getGroup(int groupID) throws IOException {
		if (groupID == 0)
			return null;
		Group group = null;
		if (_groups.containsKey(groupID)) {
			group = _groups.get(groupID);
		} else {
			// This is a new group, so get its allowed gestures and construct

			// System.out.println("[ClientConnection] Getting Group Gestures ID:
			// " + groupID);
			Vector<Integer> gestureIDs = getGestures(groupID);
			group = new Group(groupID, gestureIDs, _protocol);
			_groups.put(groupID, group);
		}

		// System.out.println("[ClientConnection] Returning Group");
		return group;
	}

}
