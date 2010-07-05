package sparshui.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import sparshui.common.ConnectionType;
import sparshui.common.NetworkConfiguration;


/**
 * The main gesture server class.
 * 
 * @author Tony Ross
 *
 */
public class GestureServer implements Runnable {
	private ServerSocket _serverSocket;
	private Vector<ClientConnection> _clients = new Vector<ClientConnection>();

	/**
	 * Start GestureServer Service.
	 * @param args
	 */
	public static void main(String[] args) {
		(new GestureServer()).run();
	}
	
	/**
	 * @throws IOException 
	 * 		If there is a communication error.
	 */
	public GestureServer() {
		openSocket();
	}
	
	/**
	 * Start accepting connections.
	 */
	@Override
	public void run() {
		acceptConnections();
	}
	
	/**
	 * Process a touch point birth by getting the groupID and gestures
	 * for the touch point.
	 * @param touchPoint
	 * 		The new touch point.
	 */
	public void processBirth(TouchPoint touchPoint) {
		Vector<ClientConnection> clients_to_remove = new Vector<ClientConnection>();
		
		for(ClientConnection client : _clients) {
			// Return if the client claims the touch point
			try {
				if(client.processBirth(touchPoint)) return;
			} catch (IOException e) {
				// This occurs if there is a communication error
				// with the client.  In this case, we will want
				// to remove the client.
				clients_to_remove.add(client);
			}
		}
		for (int i = 0; i < clients_to_remove.size(); i++) {
			_clients.remove(clients_to_remove.elementAt(i));
			System.out.println("[GestureServer] Client Disconnected");
		}
	}
	
	/**
	 * 
	 */
	private void openSocket() {
		try {
			_serverSocket = new ServerSocket(NetworkConfiguration.PORT);
			System.out.println("[GestureServer] Socket Open");
		} catch (IOException e) {
			System.err.println("[GestureServer] Failed to open a server socket.");
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	private void acceptConnections() {
		System.out.println("[GestureServer] Accepting Connections");
		while(!_serverSocket.isClosed()) {
			try {
				acceptConnection(_serverSocket.accept());
			} catch (IOException e) {
				System.err.println("[GestureServer] Failed to establish client connection");
				e.printStackTrace();
			}
		}
		System.out.println("[GestureServer] Socket Closed");
	}
	
	/**
	 * 
	 * @param socket
	 * @throws IOException 
	 */
	private void acceptConnection(Socket socket) throws IOException {
		int type = socket.getInputStream().read();
		if(type == ConnectionType.CLIENT.value()) {
			acceptClientConnection(socket);
		} else if(type == ConnectionType.INPUT_DEVICE.value()) {
			acceptInputDeviceConnection(socket);
		}
	}
	
	/**
	 * 
	 * @param socket
	 * @throws IOException 
	 */
	private void acceptClientConnection(Socket socket) throws IOException {
		System.out.println("[GestureServer] ClientConnection Accepted");
		ClientConnection cc = new ClientConnection(socket);
		_clients.add(cc);
	}
	
	/**
	 * 
	 * @param socket
	 * @throws IOException 
	 */
	private void acceptInputDeviceConnection(Socket socket) throws IOException {
		System.out.println("[GestureServer] InputDeviceConnection Accepted");
		new InputDeviceConnection(this, socket);
	}
}
