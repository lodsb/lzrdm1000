package Sequencer;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import Control.Types.DoubleType;
import Sequencer.SequenceEvent.SequenceEventType;

import com.trolltech.qt.QThread;
import com.trolltech.qt.core.QObject;

public class TestingSequencer extends QObject implements SequencerInterface, Runnable {

	private LinkedList<SequenceEvent> sequenceEvents = new LinkedList<SequenceEvent>();
	
	//private ConcurrentLinkedQueue<SequenceEvent> queuedSequenceEvents = new ConcurrentLinkedQueue<SequenceEvent>();
	
	private BlockingQueue<SequenceEvent> queuedSequenceEvents = new LinkedBlockingQueue<SequenceEvent>();

	ReentrantLock lock = new ReentrantLock();
	
	AtomicInteger shit = new AtomicInteger();
	AtomicInteger shit2 = new AtomicInteger();
	
	private class ProcessThread implements Runnable {
		public void run() {
			simulateEvals(si, remainingTicks, loops);
		}
	}
	
	
	private long remainingTicks = 0;
	private SequenceInterface si = null;
	private int loops = 0;
	
	private Thread sequencerThread;
	public Thread eventThread = new Thread(this);
	
	public TestingSequencer() {
		eventThread.start();
	}
	
	public LinkedList<SequenceEvent> getSequenceEventList() {
		return (LinkedList<SequenceEvent>) sequenceEvents.clone();
	}
	
	@Override
	public void postSequenceEvent(SequenceEvent sequenceEvent) {
		//lock.lock();
		sequenceEvents.add(sequenceEvent);
		try {
			queuedSequenceEvents.put(sequenceEvent);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//lock.unlock();
		if(sequenceEvent.getSequenceEventType() == SequenceEventType.INSERT) {
			shit.incrementAndGet();
			//System.out.println("WQ "+shit.get()+" "+shit2.get());
		}
		//System.out.println(sequenceEvents.size()+" "+queuedSequenceEvents.size());
	}
	
	public void clearEventEntriesAndReset() {
		sequenceEvents.clear();
	}
	
	public void simulateEvals(SequenceInterface sequence, long ticks, int loops) {
		System.out.println("Simulating "+ticks+" ticks and "+loops+ " loops with sequence "+sequence);
		long startTime = System.currentTimeMillis();
		
		for(int k = 0; k < loops; k++) {
			for(long i = 0; i < ticks; i++) {
				this.simulateOneEval(sequence, i);
			}
		}
		long stopTime = System.currentTimeMillis();
		long runTime = stopTime - startTime+1;
		System.out.println("Run time: " + runTime+ " ticks/ms "+(ticks*loops)/runTime);

	}
	
	public void simulateEvalsThreaded(SequenceInterface sequence, long ticks, int loops) {
		this.remainingTicks = ticks;
		this.si = sequence;
		this.loops = loops;
		
		sequencerThread = new Thread(new ProcessThread());
		sequencerThread.start();
	}
	
	public boolean threadFinished() {
		return !sequencerThread.isAlive();
	}

	public void simulateOneEval(SequenceInterface sequence, long tick) {
		sequence.eval(tick);
	}
	
	public void clearSequenceEventList() {
		sequenceEvents.clear();
	}
	
	@Override
	public boolean processTick(long tick) {
		return true;
	}

	@Override
	public void run() {
			while(true) {
				//System.out.println("?!? "+sequenceEvents.size()+" "+queuedSequenceEvents.size());
				SequenceEvent se;
				try {
					se = queuedSequenceEvents.take();
					se.getSource()._pumpSequenceEvent(se);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}

}
