package message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import com.trolltech.qt.core.QObject;

public class ThreadComSlotted<In> extends QObject implements ProcessorInterface{

	public Signal1<Object> executeSignal = new Signal1<Object>();
	
	private ConcurrentLinkedQueue<In> sendQueue = new ConcurrentLinkedQueue<In>();
		
	private Semaphore sema;
	
	protected ConcurrentLinkedQueue<In> getSendQueue() {
		return this.sendQueue;
	} 
	
	public void post(In input) {
		sendQueue.add(input);
		
		if(this.sema != null) {
			this.sema.release();
		}
	}
		
	@Override
	public void process() {
		In input;
		if((input = sendQueue.poll()) != null) {
			this.executeSignal.emit(input);
		}
	}

	@Override
	public void setSemaphore(Semaphore s) {
		this.sema = s;
	}
	
}

