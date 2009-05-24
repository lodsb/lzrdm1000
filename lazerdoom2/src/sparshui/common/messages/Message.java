package sparshui.common.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class is a base class for classes that represent messages to be sent
 * over a socket. It relies heavily on the functionality provided by Serializer.
 *
 * @param <ReturnType>
 */
public abstract class Message<ReturnType> {
	private boolean _hasReturnValue;
	
	/**
	 * 
	 */
	protected Message() {
		_hasReturnValue = false;
	}
	
	/**
	 * 
	 * @param hasReturnValue
	 */
	protected Message(boolean hasReturnValue) {
		_hasReturnValue = hasReturnValue;
	}
	
	/**
	 * Send this message to the client.
	 * @param in
	 * 		The input data stream.
	 * @param out
	 * 		The output data stream.
	 * @return
	 * 		The return type.
	 */
	@SuppressWarnings("unchecked")
	public ReturnType send(DataInputStream in, DataOutputStream out) {
		Serializer.write(this, out);
		if(_hasReturnValue)
			return (ReturnType)Serializer.read(in);
		else
			return null;
	}
	
	/**
	 * Send this message to the client.
	 * @param in
	 * 		The input stream.
	 * @param out
	 * 		The output stream.
	 * @return
	 * 		The value of the read call.
	 */
	public ReturnType send(InputStream in, OutputStream out) {
		return send(new DataInputStream(in), new DataOutputStream(out));
	}
}
