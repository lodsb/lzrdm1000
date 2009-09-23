package Sequencer;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

import lazerdoom.Core;

import Sequencer.SequenceEvent.SequenceEventSubtype;
import Sequencer.SequenceEvent.SequenceEventType;

public class SequencePlayer extends BaseSequence implements SequencePlayerInterface {
	
	public SequencePlayer(SequencerInterface sequencer) {
		super(sequencer);
	}
	
	private AtomicReference<SequenceInterface> sequence = new AtomicReference<SequenceInterface>();
	
	private AtomicBoolean startSequence = new AtomicBoolean();
	private long scheduledStartTicks = 0;
	
	private long startTicks = 0;
	
	private AtomicBoolean stopSequence = new AtomicBoolean();
	private long scheduledStopTicks = 0;
	
	private AtomicBoolean isRunning = new AtomicBoolean();
	
	@Override
	public SequenceInterface getSequence() {
		return sequence.get();
	}

	public void scheduleStartNext(long ticks) {
		long lastEventTick = Sequencer.getCurrentGlobalTick()%ticks;
		long nextEventTick = ticks-lastEventTick;
		this.scheduleStart(nextEventTick);  
	}
	
	public void scheduleStopNext(long ticks) {
		long lastEventTick = Sequencer.getCurrentGlobalTick()%ticks;
		long nextEventTick = ticks-lastEventTick;
		this.scheduleStop(nextEventTick);  
	}
	
	@Override
	public void scheduleStart(long ticks) {
		isRunning.set(false);
		if(this.sequence.get() != null) {
			startSequence.set(true);
			scheduledStartTicks = ticks;

			this.postSequenceEvent(SequenceEventType.SEQUENCE_PLAYER_STARTING, SequenceEventSubtype.TICK, ticks);
		}
	}

	@Override
	public void scheduleStop(long ticks) {
		isRunning.set(false);
		stopSequence.set(true);
		scheduledStopTicks = ticks;
		
		this.postSequenceEvent(SequenceEventType.SEQUENCE_PLAYER_STOPPING, SequenceEventSubtype.TICK, ticks);
	}

	@Override
	public void setSequence(SequenceInterface sequence) {
		this.sequence.set(sequence);
	}

	@Override
	public void startSequenceImmidiately() {
		isRunning.set(false);
		if(this.sequence.get() != null) { 
			startSequence.set(true);
			scheduledStartTicks = 0;
		}
	}

	@Override
	public void stopSequenceImmidiately() {
		isRunning.set(false);
		stopSequence.set(true);
		scheduledStopTicks = 0;
	}

	@Override
	public SequenceInterface deepCopy() {
		SequencePlayer sp = new SequencePlayer(this.getSequencer());
		sp.startSequence.set(this.startSequence.get());
		sp.stopSequence.set(this.stopSequence.get());
		sp.startTicks = this.startTicks;
		sp.sequence.set(this.sequence.get());
		
		return sp;
	}

	@Override
	public boolean eval(long tick) {
		//currentTick.set(currentTick.get()+1);
	/*	if(tick % 100 == 0) {
			System.out.println("player eval "+tick+" t-s "+(tick-startTicks));
		}*/
//		System.out.print(" sp "+tick+" ");
		if(sequence.get() != null) {
			if(isRunning.get()) {
					if(tick % 100 == 0) {
				System.out.println("player eval "+tick+" t-s "+(tick-startTicks));
			}
				
					this.isRunning.set(sequence.get().eval(tick-startTicks));
					
					if(!this.isRunning.get()) {
						this.postSequenceEvent(SequenceEventType.SEQUENCE_PLAYER_STOPPED, SequenceEventSubtype.NONE, null);
					}
			} else {
				if(startSequence.get()) {
					if(scheduledStartTicks == 0) {
						startTicks = tick+1;
						sequence.get().eval(0);
						isRunning.set(true);
						
						startSequence.set(false);
						this.postSequenceEvent(SequenceEventType.SEQUENCE_PLAYER_STARTED, SequenceEventSubtype.NONE, null);
						
					} else {
						scheduledStartTicks--;
					}
				} else if(stopSequence.get()) {
					if(scheduledStopTicks >= 0) {
						scheduledStopTicks--;
						sequence.get().eval(tick-startTicks);
						
						this.postSequenceEvent(SequenceEventType.SEQUENCE_PLAYER_STOPPED, SequenceEventSubtype.NONE, null);
						
					} else {
						stopSequence.set(false);
					}
				}
			}
		return this.sequence.get().isRunning();
		
		}
	
		return false;
	}

	@Override
	public boolean isRunning() {
		if(this.sequence != null) {
			return this.sequence.get().isRunning();
		} else {
			return false;
		}
	}

	@Override
	public void reset() {
		if(sequence.get() != null) {
			sequence.get().reset();
		}
		
		this.stopSequenceImmidiately();
	}

	@Override
	public long size() {
		if(sequence.get() != null) {
			return sequence.get().size();
		}
		return 0;
	}
	
	public String toString() {
		return "Player: { "+this.sequence+" }";
	}

}
