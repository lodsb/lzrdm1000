package Sequencer;

import java.util.concurrent.atomic.AtomicLong;

import lazerdoom.LzrDmObjectInterface;

import Sequencer.SequenceEvent.SequenceEventSubtype;
import Sequencer.SequenceEvent.SequenceEventType;

import com.trolltech.qt.QSignalEmitter.Signal1;
import com.trolltech.qt.core.QObject;

public class Pause extends BaseSequence implements SequenceInterface, LzrDmObjectInterface {

	private AtomicLong pauseTicks = new AtomicLong();
	private long runTicks = 0;
	private boolean isRunning = false;
	
	public Pause(SequencerInterface sequencer, long pauseTicks) {
		super(sequencer);
		this.pauseTicks.set(pauseTicks);
	}
	
	public void setPauseTicks(long pauseTicks) {
		if(this.pauseTicks.get() != pauseTicks) {
			this.postSequenceEvent(SequenceEventType.SET_LENGTH, SequenceEventSubtype.SIZE_IN_TICKS, pauseTicks);
			this.postSequenceEvent(SequenceEventType.SEQUENCE_SIZE_CHANGED, SequenceEventSubtype.SIZE_IN_TICKS, pauseTicks);
			this.pauseTicks.set(pauseTicks);
		}
	}
	
	@Override
	public boolean eval(long tick) {
		super.eval(tick);
		if(tick == 0) {
			this.postSequenceEvent(SequenceEventType.STARTED, SequenceEventSubtype.NONE, null);
			runTicks = 0;
		}
		
		if(runTicks < pauseTicks.get()) {
			runTicks++;
			isRunning = true;
		} else {
			if(isRunning) {
				this.postSequenceEvent(SequenceEventType.STOPPED, SequenceEventSubtype.NONE, null);
			}
			
			isRunning = false;
		}
		
		return isRunning;
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}
	
	@Override
	public void reset() {
		super.reset();
		runTicks = 0;
	}

	@Override
	public long size() {
		return pauseTicks.get();
	}

	@Override
	public SequenceInterface deepCopy() {
		// TODO Auto-generated method stub
		Pause copy = new Pause(this.getSequencer(), this.pauseTicks.get());
		this.postSequenceEvent(SequenceEventType.CLONED_SEQUENCE, SequenceEventSubtype.NONE, copy);
		return copy;
	}

}
