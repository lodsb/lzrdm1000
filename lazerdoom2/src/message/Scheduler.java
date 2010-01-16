package message;

import java.util.concurrent.CopyOnWriteArrayList;


public class Scheduler implements ProcessorInterface {

	private CopyOnWriteArrayList<ProcessorInterface> processors = new CopyOnWriteArrayList<ProcessorInterface>();
	
	public void registerProcessor(ProcessorInterface processor) {
		processors.add(processor);
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
}
