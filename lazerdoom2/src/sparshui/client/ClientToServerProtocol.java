package sparshui.client;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import sparshui.common.ClientProtocol;
import sparshui.common.Event;
import sparshui.common.Location;
import sparshui.common.messages.events.*;
import sparshui.common.utils.Converter;

/**
 * ClientToServerProtocol implements the Client side protocol.
 * It is the interface between Client.java and the lower level
 * socket calls to communicate with the Gesture Server.
 * 
 * @author Jay Roltgen
 *
 */
public class ClientToServerProtocol extends ClientProtocol {

	/**
	 * Constructor calls ClientProtocol's constructor and sets up 
	 * data input and output streams for the specified socket.
	 * 
	 * @param socket
	 * 		The socket that is connected to the Gesture Server.
	 * @throws IOException
	 * 		If there is a communication error.
	 */
	public ClientToServerProtocol(Socket socket) throws IOException {
		super(socket);
	}

	/**
	 * Processes a request from the Gesture Server
	 * 
	 * @param client
	 * 		The Client object that the server wants to communicate with.
	 */
	public void processRequest(Client client) {
		try {
			// Read the message type
			MessageType type = recvType();
			// Read the length of the message
			int length = _in.readInt();
			// Create a byte array to hold message data
			byte[] data = new byte[length];
			// Read message data
			_in.readFully(data);
			
			// Dispatch to appropriate handler method
			switch (type) {
			case EVENT:
				handleEvents(client, data);
				break;
			case GET_GROUP_ID:
				handleGetGroupID(client, data);
				break;
			case GET_ALLOWED_EVENTS:
				handleGetAllowedGestures(client, data);
				break;
			}
		} catch (IOException e) {
			System.err.println("[Client Protocol] GestureServer Connection Lost");
			System.exit(0);
		}
	}

	/**
	 * Handle the EVENT message by sending it on to the client.
	 * 
	 * @param client
	 * 		The client to send events to.
	 * @param data
	 * 		The data associated with this event.
	 */
	private void handleEvents(Client client, byte[] data) {
		// If there are no events, return immediately.
		if (data.length < 1) {
			return;
		}
		// Get the group ID - the first four bytes
		int groupID = (int) data[0] + (int) data[1] + (int) data[2]
				+ (int) data[3];

		// Get the first integer of the new array - this is the event type.
		int eventType = (int) data[4] + (int) data[5] + (int) data[6]
				+ (int) data[7];

		// Copy the new data into a new byte array, omitting the group ID and
		// event type
		byte[] newData = new byte[data.length - 8];
		System.arraycopy(data, 8, newData, 0, data.length - 8);

		Event event = null;

		switch (eventType) {
		/*case 0: // EventType.DRAG_EVENT.ordinal():
			event = new DragEvent(newData);
			break;*/
		case 1: // EventType.ROTATE_EVENT.ordinal():
			event = new RotateEvent(newData);
			break;
		case 2: // EventType.SPIN_EVENT.ordinal():
			// TODO change from default constructor
			/*event = new SpinEvent();
			break;*/
		case 3: // EventType.TOUCH_EVENT.ordinal():
			event = new TouchEvent(newData);
			break;
		case 4: // EventType.ZOOM_EVENT.ordinal():
			event = new ZoomEvent(newData);
			break;
		case 5: // EventType.DBLCLICK_EVENT.ordinal();
			/*event = new DblClkEvent(newData);
			break;*/
		}
		
		if (event != null)
			client.processEvent(groupID, event);

	}

	/**
	 * Handle the get group ID message.
	 * @param client
	 * 		The client the server wants to request group ID for.
	 * @param data
	 * 		The data specific to the group ID message.
	 * @throws IOException
	 * 		If there is a connection error.
	 */
	private void handleGetGroupID(Client client, byte[] data)
			throws IOException {
		byte[] tempFloat = new byte[4];
		
		// Read the x-coordinate
		System.arraycopy(data, 0, tempFloat, 0, 4);
		int intxbits = Converter.byteArrayToInt(tempFloat);

		// Read the y-coordinate
		System.arraycopy(data, 4, tempFloat, 0, 4);
		int intybits = Converter.byteArrayToInt(tempFloat);

		float x = Float.intBitsToFloat(intxbits);
		float y = Float.intBitsToFloat(intybits);
		
		Location location = new Location(x, y);
		int groupID = client.getGroupID(location);

		_out.writeInt(groupID);
	}

	/**
	 * Returns a list of valid gesture IDs to the gesture server. The message
	 * protocol format is as follows:
	 * 		int - 4 bytes: total number of gesture IDs being sent
	 * 		ints - 4 bytes each: gesture IDs
	 * 
	 * @param client
	 * 		The client that this call is pushed to.
	 * @param data
	 * 		The data that holds the groupID for the call.
	 * @throws IOException
	 * 		If there is a connection error.
	 */
	private void handleGetAllowedGestures(Client client, byte[] data)
			throws IOException {
		int groupID = (data[0] << 12) + (data[1] << 8) + (data[2] << 4)
				+ data[3];

		// Get the allowed Gesture IDs
		List<Integer> gestureIDs = client.getAllowedGestures(groupID);
		int length = gestureIDs.size() * 4;
		_out.writeInt(length);

		// Write the gesture IDs
		for (int gestureID : gestureIDs) {
			_out.writeInt(gestureID);
		}
	}

	/**
	 * Receive the type of the message.
	 * @return
	 * 		The message type.
	 * @throws IOException
	 * 		If there is a connection error.
	 */
	private MessageType recvType() throws IOException {
		int data = (int) _in.readByte();
		// System.out.println("recvType: "+data);
		return MessageType.values()[data];
	}

}
