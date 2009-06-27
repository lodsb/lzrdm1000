package Sequencer;

import java.util.LinkedList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import com.trolltech.qt.QSignalEmitter.Signal1;
import com.trolltech.qt.core.QObject;

import Control.ControlServer;

public class Sequencer extends QObject implements SequenceInterface, Runnable {

	private Signal1<Long> evalSignal = new Signal1<Long>();
	@Override
	public Signal1<Long> getSequenceEvalUpdateSignal() {
		return evalSignal;
	}
	
	private SequenceInterface mainSequence;
	private ControlServer controlServer;
	private boolean isRunning  = false;
	private long currentTick = 0;
	
	// for thread synchronization & signals
	private Semaphore globalTickSyncSemaphore = new Semaphore(0);
	private ConcurrentLinkedQueue<Long> passedTickList = new ConcurrentLinkedQueue<Long>();
	
	
	public Signal1<Long> globalTick = new Signal1<Long>();
	
	public Sequencer(SequenceInterface mainSequence, ControlServer controlServer) {
		this.mainSequence = mainSequence;
		this.controlServer= controlServer;
		
		Thread thread = new Thread(this);
		this.moveToThread(thread);
		thread.start();
	}
	
	@Override
	public SequenceInterface deepCopy() {
		return new Sequencer(this.mainSequence.deepCopy(), this.controlServer);
	}

	@Override
	public synchronized boolean eval(long tick) {
		isRunning = mainSequence.eval(tick);
		
		controlServer.flushMessages();
		passedTickList.offer(tick);

		
		currentTick = tick;
		
		globalTickSyncSemaphore.release();
		
		return isRunning;
	}
	
	public void run() {
		while(true) {
				try {
					globalTickSyncSemaphore.acquire();
					
					Long passedTick;
					while((passedTick = passedTickList.poll()) != null) {
						evalSignal.emit(passedTick);
					}
						
					// send update signals of the respective sequences to the i.e. GUI thread
					// the list of sent messages is a lock-free one!
					ConcurrentLinkedQueue<ControlServer.SentMessage> queue = controlServer._getRecentMessages();
					ControlServer.SentMessage sentMessage;
					
					while((sentMessage = queue.poll()) != null) {
						sentMessage.sequenceInterface.getSequenceEvalUpdateSignal().emit(sentMessage.localTick);
					}
					
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public void reset() {
		this.mainSequence.reset();
	}

	@Override
	public long size() {
		return mainSequence.size();
	}

}
