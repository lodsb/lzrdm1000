package Sequencer;

import java.util.LinkedList;

public class TestingSequencer implements SequencerInterface, Runnable {

	private LinkedList<SequenceEvent> sequenceEvents = new LinkedList<SequenceEvent>();
	
	private long remainingTicks = 0;
	private SequenceInterface si = null;
	private int loops = 0;
	
	private Thread sequencerThread = new Thread(this);
	
	public LinkedList<SequenceEvent> getSequenceEventList() {
		return sequenceEvents;
	}
	
	@Override
	public void postSequenceEvent(SequenceEvent sequenceEvent) {
		sequenceEvents.add(sequenceEvent);
	}
	
	
	public void simulateEvals(SequenceInterface sequence, long ticks, int loops) {
		System.out.println("Simulating "+ticks+" ticks and "+loops+ " loops with sequence "+sequence);
		long startTime = System.currentTimeMillis();
		
		for(int k = 0; k < loops; k++) {
			for(long i = 0; i < ticks; i++) {
				sequence.eval(i);
			}
		}
		long stopTime = System.currentTimeMillis();
		long runTime = stopTime - startTime;
		System.out.println("Run time: " + runTime+ " ticks/ms "+(ticks*loops)/runTime);

	}
	
	public void simulateEvalsThreaded(SequenceInterface sequence, long ticks, int loops) {
		this.remainingTicks = ticks;
		this.si = sequence;
		this.loops = loops;
		
		sequencerThread.start();
	}
	
	public boolean threadFinished() {
		return !sequencerThread.isAlive();
	}

	@Override
	public boolean processTick(long tick) {
		return true;
	}

	@Override
	public void run() {
		this.simulateEvals(this.si, this.remainingTicks, loops);
	}

}
