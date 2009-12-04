package Sequencer;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

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
		//System.out.println("DFDDDDSSD");
		for(SequenceEvalListenerInterface svali: evalListeners) {
			//FIXME: this isnt used for gui updates... instead low freq eval!
			//SequencerView.getInstance().propagateSequenceEval(svali, tick);
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
		this.createSequenceEvents();
	}
	
	BaseSequence(SequencerInterface sequencer) {
		this.sequencer = sequencer;
		this.createSequenceEvents();
	}
	
	/*
	 * FIXME: hack to circumvent memory allocation in "realtime" thread
	 */
	
	private int numSequenceEvents = 1000;
	private AtomicInteger currentSequenceEventIndex = new AtomicInteger();
	private CopyOnWriteArrayList<SequenceEvent> sequenceEventContainers = new CopyOnWriteArrayList<SequenceEvent>();
	
	private void createSequenceEvents() {
		for(int i = 0; i < numSequenceEvents; i++) {
			sequenceEventContainers.add(new SequenceEvent());
		}
		
		currentSequenceEventIndex.set(0);
	}
	
	private SequenceEvent getSequenceEvent() {
		SequenceEvent event = sequenceEventContainers.get((currentSequenceEventIndex.get()));
		currentSequenceEventIndex.set((currentSequenceEventIndex.get()+1)% numSequenceEvents);
		return event;
	}

	
	void postSequenceEvent(SequenceEvent.SequenceEventType type, SequenceEvent.SequenceEventSubtype subtype, Object argument) {
		SequenceEvent event = getSequenceEvent();
		event.setEvent(this, type, subtype, argument);
		this.sequencer.postSequenceEvent(event);
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
