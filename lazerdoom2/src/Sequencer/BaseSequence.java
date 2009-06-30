package Sequencer;

import Sequencer.SequenceEvent.SequenceEventSubtype;
import Sequencer.SequenceEvent.SequenceEventType;

import com.trolltech.qt.core.QObject;

public abstract class BaseSequence extends QObject implements SequenceInterface {

	// for gui synchronization
	private Signal1<Long> evalSignal = new Signal1<Long>();
	private Signal1<SequenceEvent> eventSignal = new Signal1<SequenceEvent>();
	
	public Signal1<Long> getSequenceEvalUpdateSignal() {
		return evalSignal;
	}

	public Signal1<SequenceEvent> getSequenceEventSignal() {
		return eventSignal;
	}
	
	protected Sequencer sequencer;
	BaseSequence(Sequencer sequencer) {
		this.sequencer = sequencer;
	}
	
	void postSequenceEvent(SequenceEvent.SequenceEventType type, SequenceEvent.SequenceEventSubtype subtype, Object argument) {
		this.sequencer.postSequenceEvent(new SequenceEvent(this, type, subtype, argument));
	}
	
	@Override
	public abstract SequenceInterface deepCopy();

	@Override
	public abstract boolean eval(long tick);

	@Override
	public abstract boolean isRunning();

	@Override
	public void reset() {
		this.postSequenceEvent(SequenceEventType.RESET, SequenceEventSubtype.NONE, null);
	}

	@Override
	public abstract long size();

}
