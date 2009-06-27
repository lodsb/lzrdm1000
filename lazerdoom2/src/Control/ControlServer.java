package Control;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.concurrent.ConcurrentLinkedQueue;

import Sequencer.SequenceInterface;
import de.sciss.jcollider.*;
import de.sciss.net.*;

public class ControlServer {
	
	// NOTE: not threadsafe! flush/appendMSG should be executed by the sequencer thread only!
	
	private OSCBundle currentBundle = new OSCBundle();
	
	private long serverLatency;
	private Server server;
	
	private ConcurrentLinkedQueue<SentMessage> recentMessages = new ConcurrentLinkedQueue<SentMessage>();
	
	public ControlServer(Server server, long latencyMs) {
		this.serverLatency = latencyMs;
		this.server = server;
	}
	
	public class SentMessage {
		public SequenceInterface sequenceInterface;
		public long localTick;
	}
	
	public ConcurrentLinkedQueue<SentMessage> _getRecentMessages() {
		return recentMessages;
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
	
	public void appendMessage(SequenceInterface si, long localTick, OSCMessage message) {
		SentMessage sm = new SentMessage();
		sm.localTick = localTick;
		sm.sequenceInterface = si;
		
		currentBundle.addPacket(message);
		recentMessages.offer(sm);
	}
}
