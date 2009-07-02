package Sequencer;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class GeneralSequencePlayer implements SequencePlayerInterface {
	private SequenceInterface sequence = null;
	
	private AtomicBoolean startSequence;
	private long scheduledStartTicks = 0;
	
	private long relativeTicks = 0;
	
	private AtomicBoolean stopSequence;
	private long scheduledStopTicks = 0;
	
	private AtomicBoolean isRunning;
	
	@Override
	public SequenceInterface getSequence() {
		return sequence;
	}

	@Override
	public void scheduleStart(long ticks) {
		isRunning.set(false);
		startSequence.set(true);
		scheduledStartTicks = ticks;
	}

	@Override
	public void scheduleStop(long ticks) {
		isRunning.set(false);
		stopSequence.set(true);
		scheduledStopTicks = ticks;
	}

	@Override
	public void setSequence(SequenceInterface sequence) {
		this.sequence = sequence;
	}

	@Override
	public void startSequenceImmidiately() {
		isRunning.set(false);
		startSequence.set(true);
		scheduledStartTicks = 0;
	}

	@Override
	public void stopSequenceImmidiately() {
		isRunning.set(false);
		stopSequence.set(true);
		scheduledStopTicks = 0;
	}

	@Override
	public SequenceInterface deepCopy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean eval(long tick) {
		if(sequence != null) {
			if(isRunning.get()) {
				relativeTicks = tick-relativeTicks;
				sequence.eval(relativeTicks);
			} else {
				if(startSequence.get()) {
					if(scheduledStartTicks == 0) {
						relativeTicks = tick;
						sequence.eval(relativeTicks);
						isRunning.set(true);
						
						startSequence.set(false);
					} else {
						scheduledStartTicks--;
					}
				} else if(stopSequence.get()) {
					relativeTicks = tick-relativeTicks;
					
					if(scheduledStopTicks >= 0) {
						scheduledStopTicks--;
						sequence.eval(relativeTicks);
					} else {
						stopSequence.set(false);
					}
				}
			}
		return this.sequence.isRunning();
		
		}
	
		return false;
	}

	@Override
	public boolean isRunning() {
		if(this.sequence != null) {
			return this.sequence.isRunning();
		} else {
			return false;
		}
	}

	@Override
	public void reset() {
		isRunning.set(false);
		
		if(sequence != null) {
			sequence.reset();
		}
	}

	@Override
	public long size() {
		if(sequence != null) {
			return sequence.size();
		}
		
		return 0;
	}

}
