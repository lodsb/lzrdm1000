package sparshui.common;

//import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * The abstract class for the client protocol that defines members
 * common to both the server-side and client-side protocols.
 * 
 * @author Tony Ross
 *
 */
public abstract class ClientProtocol {

	/**
	 * 
	 */
	protected Socket _socket;
	
	/**
	 * 
	 */
	protected DataInputStream _in;
	
	/**
	 * 
	 */
	protected DataOutputStream _out;

	/**
	 * Create a new ClientProtocol object.
	 * 
	 * @param socket 
	 * 		The socket that has been opened.
	 * @throws IOException
	 * 		If there is a communication error.
	 */
	public ClientProtocol(Socket socket) throws IOException {
		_socket = socket;
		_in = new DataInputStream(_socket.getInputStream());
		_out = new DataOutputStream(_socket.getOutputStream());
	}
	
	/**
	 * 
	 * @author tross
	 *
	 */
	protected enum MessageType {
		
		/**
		 * 
		 */
		EVENT,
		
		/**
		 * 
		 */
		GET_GROUP_ID,
		
		/**
		 * 
		 */
		GET_ALLOWED_EVENTS
	}
}
