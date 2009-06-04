package Control;

import java.io.IOException;
import java.net.SocketAddress;
import de.sciss.jcollider.*;
import de.sciss.net.*;

public class ControlServer {
	
	// NOTE: not threadsafe! flush/appendMSG should be executed by the sequencer thread only!
	
	private OSCBundle currentBundle = new OSCBundle();
	
	private long serverLatency;
	private Server server;
	
	public ControlServer(Server server, long latencyMs) {
		this.serverLatency = latencyMs;
		this.server = server;
	}
	
	public void flushMessages() {
		if(currentBundle.getPacketCount() != 0) {
			
			currentBundle.setTimeTagAbsMillis(OSCBundle.NOW+serverLatency);
			
			try {
				server.sendBundle(currentBundle);
			} catch (IOException e) {
				System.out.println("Error sending OSCBundle to SC3Server:\n"+e.getMessage());
				e.printStackTrace();
			}
			
			currentBundle = new OSCBundle();
		}
	}
	
	public void setServerLatency(long latency) {
		this.serverLatency = latency;
	}
	
	public void appendMessage(OSCMessage message) {
		currentBundle.addPacket(message);
	}
}
