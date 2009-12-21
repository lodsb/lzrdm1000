package message;

import java.util.concurrent.CopyOnWriteArrayList;


public class Scheduler implements Processor {

	private CopyOnWriteArrayList<Processor> processors = new CopyOnWriteArrayList<Processor>();
	
	public void registerProcessor(Processor processor) {
		processors.add(processor);
	}
	
	public void removeProcessor(Processor processor) {
		processors.remove(processor);
	}
	
	@Override
	public void process() {
		for(Processor processor: processors) {
			processor.process();
		}
	}
}
