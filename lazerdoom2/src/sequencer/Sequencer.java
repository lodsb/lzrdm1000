package sequencer;

import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import sequencer.SequenceEvent.SequenceEventSubtype;
import sequencer.SequenceEvent.SequenceEventType;
import sequencer.SequencerEvent.SequencerEventSubtype;
import sequencer.SequencerEvent.SequencerEventType;


import com.trolltech.qt.QSignalEmitter.Signal1;
import com.trolltech.qt.core.QObject;

import control.ControlServer;


public class Sequencer extends QObject implements Runnable, SequencerInterface {
	
	// default resolution
	public static final int PPQ = 128;
	
	public static double beatMeasureToMs(int beat, int measure, double bpm) {
		return (240000*((double)beat/(double)measure)/bpm);
	}
	
	public static long bpmToPPQNanos(double bpm) {
		int PPQPerBar = 4*PPQ;
		
		return (long)(beatMeasureToMs(1, PPQPerBar, bpm)*1000000);
	}
	
	private static long currentGlobalTick = 0;
	public static long getCurrentGlobalTick() {
		return currentGlobalTick;
	}
	
	private ParallelSequenceContainer rootSequence;
	private ControlServer controlServer;
	private boolean isRunning  = false;
	
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();
	
	// for thread synchronization & signals
	public Signal1<Long> globalTickSignal = new Signal1<Long>();
	
	private Semaphore globalTickSyncSemaphore = new Semaphore(0);
	private ConcurrentLinkedQueue<Long> passedTickList = new ConcurrentLinkedQueue<Long>();
	private ConcurrentLinkedQueue<SequenceEvent> passedSequenceEventList = new ConcurrentLinkedQueue<SequenceEvent>();
	private ConcurrentLinkedQueue<SequencerEvent> passedSequencerEventList = new ConcurrentLinkedQueue<SequencerEvent>();
	
	ConcurrentLinkedQueue<SequencerEventListenerInterface> eventListeners = new ConcurrentLinkedQueue<SequencerEventListenerInterface>();

	public void registerSequencerEventListener(SequencerEventListenerInterface seli) {
		eventListeners.offer(seli);
	}
	
	public void unregisterSequencerEventListener(SequencerEventListenerInterface seli) {
		eventListeners.remove(seli);
	}
		
	public void postSequenceEvent(SequenceEvent sequenceEvent) {
		passedSequenceEventList.offer(sequenceEvent);
	}
	
	void postSequencerEvent(SequencerEvent sequencerEvent) {
		passedSequencerEventList.offer(sequencerEvent);
	}
	
	public Sequencer(ControlServer controlServer) {
		this.controlServer= controlServer;
		
		Thread thread = new Thread(this);
		this.moveToThread(thread);
		thread.start();
	}
	
	/*private Sequencer(SequenceContainerInterface mainSequence, ControlServer controlServer) {
		this.controlServer= controlServer;
		
		Thread thread = new Thread(this);
		this.moveToThread(thread);
		thread.start();
	}*/
	
	public boolean processTick(long tick) {
		currentGlobalTick = tick;
		readLock.lock();
		
		if(rootSequence != null) {
			isRunning = rootSequence.eval(tick);

			controlServer.flushMessages();
			passedTickList.offer(tick);

			globalTickSyncSemaphore.release();
		} else {
			isRunning = false;
		}
		readLock.unlock();
		
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
						sentMessage.sequence._pumpSequenceEval(sentMessage.localTick);
					}
					
					SequenceEvent se;
					
					while((se = passedSequenceEventList.poll()) != null) {
						se.getSource()._pumpSequenceEvent(se);
					}
					
					SequencerEvent sre;
					
					while((sre = passedSequencerEventList.poll()) != null) {
						for(SequencerEventListenerInterface seli: eventListeners) {
							seli.dispatchSequencerEvent(sre);
						}
					}
					
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void reset() {
		readLock.lock();
			if(rootSequence != null) {
				this.rootSequence.reset();
			}
		readLock.unlock();
	}
	
	public void setRootSequence(ParallelSequenceContainer seq) {
		writeLock.lock();
			this.rootSequence = seq;
		writeLock.unlock();
	}
	
	public ParallelSequenceContainer getRootSequence() {
		ParallelSequenceContainer p = null;
		readLock.lock();
			p = this.rootSequence;
		readLock.unlock();
		
		return p;
	}

	public long sizeOfAllSequences() {
		long ret = 0;
		
		readLock.lock();
			if(rootSequence != null) {
				return rootSequence.size();
			} 
		readLock.unlock();
		return ret;
	}
}
