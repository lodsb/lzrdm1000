package message;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;


public class Scheduler implements ProcessorInterface {

	private CopyOnWriteArrayList<ProcessorInterface> processors = new CopyOnWriteArrayList<ProcessorInterface>();
	private Semaphore sema;
	
	public Scheduler(Semaphore s) {
		this.sema = s;
	}
	
	public void registerProcessor(ProcessorInterface processor) {
		processors.add(processor);
		processor.setSemaphore(this.sema);
	}
	
	public void removeProcessor(ProcessorInterface processor) {
		processors.remove(processor);
	}
	
	@Override
	public void process() {
		for(ProcessorInterface processor: processors) {
			processor.process();
		}
	}

	@Override
	public void setSemaphore(Semaphore s) {
		// argh... for now discard
		this.sema = s;
	}
}
