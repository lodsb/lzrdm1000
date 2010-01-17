package message;

import java.util.concurrent.Semaphore;

public interface ProcessorInterface {
	public void process();
	public void setSemaphore(Semaphore s);
}
