package message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import com.trolltech.qt.core.QObject;

public abstract class ThreadXBar<In, Out> extends QObject implements ProcessorInterface{
	
	protected class Container<T1, T2> {
		T1 in;
		T2 out;
		int id;
	}

	private Semaphore sema;
	private AtomicInteger containerCounter = new AtomicInteger();	
	private int numPreallocContainers;
	private ArrayList<Container<In, Out>> containers = new ArrayList<Container<In, Out>>();
	
	private ConcurrentLinkedQueue<Container<In,Out>> sendQueue = new ConcurrentLinkedQueue<Container<In,Out>>();
	private LinkedBlockingQueue<Container<In,Out>> recvQueue = new LinkedBlockingQueue<Container<In, Out>>();
	
	public ThreadXBar() {
		this._prealloc(100);
	}
	
	protected ConcurrentLinkedQueue<Container<In,Out>> getSendQueue() {
		return this.sendQueue;
	}
	
	protected LinkedBlockingQueue<Container<In,Out>> getRecvQueue() {
		return this.recvQueue;
	} 
	
	public ThreadXBar(int prealloc) {
		this._prealloc(prealloc);
	}

	private void _prealloc(int containers) {
		for(int i = 0; i < containers; i++) {
			this.containers.add(new Container<In, Out>());
		}
		
		this.numPreallocContainers = containers;
	}
	
	private Random rnd = new Random();
	
	private Container<In, Out> getContainer() {
		//FIXME: bug? i think so!
		//Container<In,Out> container = containers.get((containerCounter.get()));
		//containerCounter.set((containerCounter.get()+1)% numPreallocContainers);
		//container.id = rnd.nextInt(); //this.containerCounter.get();
		//return container;
		
		return new Container<In, Out>();
	}

	abstract protected Out execute(In in); 
	
	public Out get(In input) {
		Container<In, Out> container = this.getContainer();
		container.in = input;
		sendQueue.add(container);
		
		Container<In, Out> recvContainer = null;
		Iterator<Container<In,Out>> iterator;
		
		if(this.sema !=  null) {
			this.sema.release();
		}
		
		while(true) {
			recvContainer = recvQueue.peek();
			
			if(recvContainer != null) {
				iterator = recvQueue.iterator();
				
				while(iterator.hasNext()) {
					recvContainer = iterator.next();
					
					if(recvContainer == container) {
						return container.out;
					}
				}
			}
		}
	}
	
	@Override
	public void process() {
		Container<In, Out> sendContainer = null;
		
		if((sendContainer = sendQueue.poll()) != null) {
			sendContainer.out = this.execute(sendContainer.in);
			this.postResult(sendContainer);
		}
	}
	
	protected void postResult(Container<In, Out> container) {
		this.recvQueue.add(container);
	}
	
	@Override
	public void setSemaphore(Semaphore s) {
		this.sema = s;
	}
	
}

// scheduler in task2
