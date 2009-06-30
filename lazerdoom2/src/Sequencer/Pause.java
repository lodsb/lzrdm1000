package Sequencer;

import Sequencer.SequenceEvent.SequenceEventSubtype;
import Sequencer.SequenceEvent.SequenceEventType;

import com.trolltech.qt.QSignalEmitter.Signal1;
import com.trolltech.qt.core.QObject;

public class Pause extends BaseSequence implements SequenceInterface {

	private long pauseTicks;
	private long runTicks = 0;
	private Sequencer sequencer;
	
	public Pause(Sequencer sequencer, long pauseTicks) {
		super(sequencer);
		this.pauseTicks = pauseTicks;
		this.sequencer = sequencer;
	}
	
	void setPauseTicks(long pauseTicks) {
		if(this.pauseTicks != pauseTicks) {
			this.postSequenceEvent(SequenceEventType.SEQUENCE_SIZE_CHANGED, SequenceEventSubtype.SIZE_IN_TICKS, pauseTicks);
			this.pauseTicks = pauseTicks;
		}
	}
	
	@Override
	public boolean eval(long tick) {
		if(runTicks != pauseTicks) {
			runTicks++;
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean isRunning() {
		return (runTicks == pauseTicks);
	}

	@Override
	public void reset() {
		super.reset();
		runTicks = 0;
	}

	@Override
	public long size() {
		return pauseTicks;
	}

	@Override
	public SequenceInterface deepCopy() {
		// TODO Auto-generated method stub
		Pause copy = new Pause(this.sequencer, this.pauseTicks);
		this.postSequenceEvent(SequenceEventType.CLONED_SEQUENCE, SequenceEventSubtype.NONE, copy);
		return copy;
	}

}
