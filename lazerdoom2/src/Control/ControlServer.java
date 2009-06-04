package Control;

import java.io.IOException;
import java.net.SocketAddress;

import de.sciss.net.*;

public class ControlServer {
	
	// NOTE: not threadsafe! flush/appendMSG should be executed by the sequencer thread only!
	
	private OSCBundle currentBundle = new OSCBundle();
	
	private long serverLatency;
	private SocketAddress clientAddress;
	private OSCServer server;
	
	public ControlServer(int port, long latencyMs) {
		this.serverLatency = latencyMs;
		
		try {
			this.server = OSCServer.newUsing(OSCServer.UDP, port);
		} catch (IOException e) {
			System.out.println("ERROR creating OSCServer:\n"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void flushMessages() {
		if(currentBundle.getPacketCount() != 0) {
			
			currentBundle.setTimeTagAbsMillis(OSCBundle.NOW+serverLatency);
			
			try {
				server.send(currentBundle, clientAddress);
			} catch (IOException e) {
				System.out.println("Error sending OSCMessage:\n"+e.getMessage());
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
