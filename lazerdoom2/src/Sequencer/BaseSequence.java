package Sequencer;

import java.util.concurrent.ConcurrentLinkedQueue;

import lazerdoom.LzrDmObjectInterface;

import GUI.View.SequencerView;
import Sequencer.SequenceEvent.SequenceEventSubtype;
import Sequencer.SequenceEvent.SequenceEventType;

import com.trolltech.qt.QSignalEmitter;
import com.trolltech.qt.core.QObject;

public abstract class BaseSequence extends QObject implements SequenceInterface, LzrDmObjectInterface {

	ConcurrentLinkedQueue<SequenceEventListenerInterface> eventListeners = new ConcurrentLinkedQueue<SequenceEventListenerInterface>();
	ConcurrentLinkedQueue<SequenceEvalListenerInterface> evalListeners = new ConcurrentLinkedQueue<SequenceEvalListenerInterface>();

	public void registerSequenceEventListener(SequenceEventListenerInterface seli) {
		eventListeners.offer(seli);
	}
	
	public void registerSequenceEvalListener(SequenceEvalListenerInterface svali) {
		evalListeners.offer(svali);
	}
	
	public void unregisterSequenceEventListener(SequenceEventListenerInterface seli) {
		eventListeners.remove(seli);
	}
	
	public void unregisterSequenceEvalListener(SequenceEvalListenerInterface svali) {
		evalListeners.remove(svali);
	}
	
	void _pumpSequenceEvent(SequenceEvent se) {
		for(SequenceEventListenerInterface seli: eventListeners) {
			SequencerView.getInstance().propagateSequenceEvent(seli, se);
		}
	}
	
	void _pumpSequenceEval(long tick) {
		System.out.println("DFDDDDSSD");
		for(SequenceEvalListenerInterface svali: evalListeners) {
			svali.dispatchEvalEvent(tick);
		}
	}
	
	private SequencerInterface sequencer;
	
	protected SequencerInterface getSequencer() {
		return sequencer;
	}
	
	protected void setSequencer(SequencerInterface sequencer) {
		this.sequencer = sequencer;
	}
	
	protected BaseSequence() {
	}
	
	BaseSequence(SequencerInterface sequencer) {
		this.sequencer = sequencer;
	}
	
	void postSequenceEvent(SequenceEvent.SequenceEventType type, SequenceEvent.SequenceEventSubtype subtype, Object argument) {
		this.sequencer.postSequenceEvent(new SequenceEvent(this, type, subtype, argument));
	}
	
	@Override
	public abstract SequenceInterface deepCopy();

	private long updateResolution = 5;
	@Override
	public boolean eval(long tick) {
		if(tick % updateResolution == 0) {
			this.postSequenceEvent(SequenceEventType.EVALUATED_LOW_FREQ, SequenceEventSubtype.TICK, tick);
		}
		
		return true;
	}

	@Override
	public abstract boolean isRunning();

	@Override
	public void reset() {
		this.postSequenceEvent(SequenceEventType.RESET, SequenceEventSubtype.NONE, null);
		this.postSequenceEvent(SequenceEventType.STOPPED, SequenceEventSubtype.NONE, null);
	}

	@Override
	public abstract long size();

}
