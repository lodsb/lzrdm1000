package Sequencer;

import java.util.LinkedList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import com.trolltech.qt.QSignalEmitter.Signal1;
import com.trolltech.qt.core.QObject;

import Control.ControlServer;

public class Sequencer extends QObject implements SequenceInterface, Runnable {
	
	private SequenceInterface mainSequence;
	private ControlServer controlServer;
	private boolean isRunning  = false;
	
	
	
	// RELATIVE!!"!"!"!"!"!"
	
	
	private long currentTick = 0;
	
	
	// for thread synchronization & signals
	public Signal1<Long> globalTickSignal = new Signal1();
	private Semaphore globalTickSyncSemaphore = new Semaphore(0);
	private ConcurrentLinkedQueue<Long> passedTickList = new ConcurrentLinkedQueue<Long>();
	private ConcurrentLinkedQueue<SequenceEvent> passedSequenceEventList = new ConcurrentLinkedQueue<SequenceEvent>();
	
	void postSequenceEvent(SequenceEvent sequenceEvent) {
		passedSequenceEventList.offer(sequenceEvent);
	}
	
	public Sequencer(ControlServer controlServer) {
		this.controlServer= controlServer;
		
		Thread thread = new Thread(this);
		this.moveToThread(thread);
		thread.start();
	}
	
	public void setMainSequence(SequenceInterface mainSequence) {
		this.mainSequence = mainSequence;
	}
	
	@Override
	public SequenceInterface deepCopy() {
		if(mainSequence !=  null) {
			Sequencer seq = new Sequencer(this.controlServer);
			seq.setMainSequence(this.mainSequence.deepCopy());
			
			return seq;
			
		} else {
			return new Sequencer(this.controlServer);
		}
	}

	@Override
	public synchronized boolean eval(long tick) {
		if(mainSequence != null) {
			isRunning = mainSequence.eval(tick);

			controlServer.flushMessages();
			passedTickList.offer(tick);


			currentTick = tick;

			globalTickSyncSemaphore.release();
		}
		return isRunning;
	}
	
	public void run() {
		while(true) {
				try {
					globalTickSyncSemaphore.acquire();
					
					Long passedTick;
					while((passedTick = passedTickList.poll()) != null) {
						globalTickSignal.emit(passedTick);
					}
						
					// send update signals of the respective sequences to the i.e. GUI thread
					// the list of sent messages is a lock-free one!
					ConcurrentLinkedQueue<ControlServer.SentMessage> queue = controlServer._getRecentMessages();
					ControlServer.SentMessage sentMessage;
					
					while((sentMessage = queue.poll()) != null) {
						sentMessage.sequence.getSequenceEvalUpdateSignal().emit(sentMessage.localTick);
					}
					
					SequenceEvent se;
					
					while((se = passedSequenceEventList.poll()) != null) {
						se.getSource().getSequenceEventSignal().emit(se);
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
		this.reset();
		
		if(mainSequence != null) {
			this.mainSequence.reset();
		}
	}

	@Override
	public long size() {
		if(mainSequence != null) {
			return mainSequence.size();
		} else {
			return 0;
		}
	}

}
