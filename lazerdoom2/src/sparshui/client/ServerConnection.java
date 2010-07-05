package sparshui.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.trolltech.qt.QThread;
import com.trolltech.qt.core.QObject;

import sparshui.common.ConnectionType;
import sparshui.common.NetworkConfiguration;
import sparshui.client.ClientToServerProtocol;

/**
 * Represents the connection to the GestureServer.
 * 
 * @author Jay Roltgen
 *
 */
public class ServerConnection extends QObject implements Runnable {
	
	private Client _client;
	private Socket _socket;
	private ClientToServerProtocol _protocol;
	
	/**
	 * Instantiate a Server Connection object.
	 * 
	 * @param address 
	 * 		The ip address of the server to connect to.
	 * @param client 
	 * 		The client object that the gesture client is using to
	 * 		listen for messages from the server.
	 * @throws UnknownHostException
	 * 		If "address" is an unknwown host.
	 * @throws IOException
	 * 		If a communication error ocurrs.
	 */
	public ServerConnection(String address, Client client) throws UnknownHostException, IOException {
		_client = client;
		_socket = new Socket(address, NetworkConfiguration.PORT);
		DataOutputStream out = new DataOutputStream(_socket.getOutputStream());
		out.writeByte(ConnectionType.CLIENT.value());
		_protocol = new ClientToServerProtocol(_socket);
		new QThread(this).start();
	}

	/**
	 * Begin processing requests.
	 */
	@Override
	public void run() {
		while(_socket.isConnected()) {
			_protocol.processRequest(_client);
		}
	}

}
