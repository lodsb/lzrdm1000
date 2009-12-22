package message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import com.trolltech.qt.core.QObject;

public class ThreadComSlotted<In> extends QObject implements Processor{

	public Signal1<In> executeSignal = new Signal1<In>();
	
	private ConcurrentLinkedQueue<In> sendQueue = new ConcurrentLinkedQueue<In>();
		
	protected ConcurrentLinkedQueue<In> getSendQueue() {
		return this.sendQueue;
	} 
	
	public void post(In input) {
		sendQueue.add(input);
	}
		
	@Override
	public void process() {
		In input;
		
		if((input = sendQueue.poll()) != null) {
			this.executeSignal.emit(input);
		}
	}		
}

