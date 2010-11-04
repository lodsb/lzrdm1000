package synth.event;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.LinkedList;

import message.Intercom;

import de.sciss.jcollider.Synth;
import de.sciss.net.OSCListener;
import de.sciss.net.OSCMessage;

public class SynthEventServer implements OSCListener {
	
	private static SynthEventServer instance;
	
	public static SynthEventServer getInstance() {
		if(instance == null) {
			instance = new SynthEventServer();
		}
		
		return instance;
	}
	
	private HashMap<Integer, LinkedList<SynthEventListenerInterface>> eventListeners = new HashMap<Integer, LinkedList<SynthEventListenerInterface>>();

	/*
	 * triggered by osclistener as registered Core!
	 * the further processing is triggered in SequencerView
	 * 
	 * @see de.sciss.net.OSCListener#messageReceived(de.sciss.net.OSCMessage, java.net.SocketAddress, long)
	 */
	int shit = 0;
	@Override
	public void messageReceived(OSCMessage arg0, SocketAddress arg1, long arg2) {
		Integer synthId = (Integer) arg0.getArg(0);
		Integer eventId = (Integer)(arg0.getArg(1));
		Object event = (arg0.getArg(2));
		
		synchronized(eventListeners) {
			LinkedList<SynthEventListenerInterface> entries = null;
		
			if((entries = eventListeners.get(synthId)) != null) {	
				for(SynthEventListenerInterface entry: entries) {
					SynthEvent se = new SynthEvent(eventId, event);
					Intercom.getInstance().system.synthEventDispatch.propagateSynthEvent(entry, se);
				}
			}
			
		}
	}
	
	public void addSynthEventListener(Synth synth, SynthEventListenerInterface synthInterface) {
		Integer synthId = synth.getNodeID();
		synchronized(eventListeners) {
			LinkedList<SynthEventListenerInterface> entries = null;
		
			if((entries = eventListeners.get(synthId)) == null) {
				entries = new LinkedList<SynthEventListenerInterface>();
				eventListeners.put(synthId, entries);
			}
			
			entries.add(synthInterface);
		}
	}
	
	public void removeSynthEventListener(Synth synth, SynthEventListenerInterface synthInterface) {
		Integer synthId = synth.getNodeID();
		synchronized(eventListeners) {
			LinkedList<SynthEventListenerInterface> entries = null;
		
			if((entries = eventListeners.get(synthId)) != null) {
				entries.remove(synthInterface);
			}
		}		
	}
}
