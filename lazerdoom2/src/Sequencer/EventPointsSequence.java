package Sequencer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

import Sequencer.SequenceEvent.SequenceEventSubtype;
import Sequencer.SequenceEvent.SequenceEventType;

import com.trolltech.qt.QSignalEmitter.Signal1;
import com.trolltech.qt.core.QObject;

import Control.ControlBusInterface;
import Control.TestingControlBus;
import Control.Types.BaseType;
import Control.Types.DoubleType;


public class EventPointsSequence<EventType extends BaseType> extends BaseSequence implements EventSequenceInterface<EventType> {
	
	// TODO: 1) locking event-Array & processing // reentrant-lock implemented, better option available? evaluate!
	// 		 2) implement startoffset&endpoint
	
	private ReentrantLock eventQueueLock = new ReentrantLock();
	
	private long nextEventTick = 0; 
	private long oldTick = 0;
	private EventType nextEvent;
	private EventType currentEvent;
	private Iterator<Long> tickIterator;
	private Iterator<EventType> eventIterator;
	
	CopyOnWriteArrayList<ControlBusInterface<EventType>> controlBuses;
	
	Map<Long, EventType> events;
	
	long startOffset = 0;
	long sequenceLength = 0;
	
	boolean isRunning = false;
	
	public Iterator<Entry<Long, EventType>> getIterator() {
		return this.events.entrySet().iterator();
	}
	
	private EventPointsSequence(SequencerInterface seq, Map<Long, EventType> events, CopyOnWriteArrayList<ControlBusInterface<EventType>> controlBuses, long startOffset, long sequenceLength) {
		super(seq);
		this.events = events;
		this.controlBuses = controlBuses;
		this.startOffset = startOffset;
		this.sequenceLength = sequenceLength;
	}
	
	public EventPointsSequence(SequencerInterface seq) {
		super(seq);
		this.events =  new ConcurrentHashMap<Long, EventType>(); 
		this.controlBuses = new CopyOnWriteArrayList<ControlBusInterface<EventType>>();
	} 
	
	
	@Override
	public void insert(EventType t, long tick) {
		this.events.put(tick, t);
		this.postSequenceEvent(SequenceEventType.INSERT, SequenceEventSubtype.TICK, tick);
	}

	@Override
	public void remove(long tick) {
		this.events.remove(tick);
		this.postSequenceEvent(SequenceEventType.REMOVE, SequenceEventSubtype.TICK, tick);
	}

	@Override
	public void remove(EventType t) {

		long tick = -1;
		
		for(Entry<Long, EventType> entry: this.events.entrySet()) {
			if(entry.getValue() == t) {
				tick = entry.getKey();
			}
		}
		
		if(tick != -1) {
			this.events.remove(tick);
		}
		
		this.postSequenceEvent(SequenceEventType.REMOVE, SequenceEventSubtype.EVENT, t);
	}

	@Override
	public void addControlBus(ControlBusInterface<EventType> cb) {
		this.controlBuses.add(cb);
		
		this.postSequenceEvent(SequenceEventType.ADD_CTRL_BUS, SequenceEventSubtype.CTRL_BUS, cb);
	}

	public void removeControlBus(ControlBusInterface<EventType> cb) {
		this.controlBuses.remove(cb);
		
		this.postSequenceEvent(SequenceEventType.REMOVE_CTRL_BUS, SequenceEventSubtype.CTRL_BUS, cb);
	}
	
	@Override
	public SequenceInterface deepCopy() {
		EventPointsSequence<EventType> copy;
		
		CopyOnWriteArrayList<ControlBusInterface<EventType>> controlBusList = new CopyOnWriteArrayList<ControlBusInterface<EventType>>();
		for(ControlBusInterface<EventType> bus: controlBuses) {
			controlBusList.add(bus);
		}

		copy = new EventPointsSequence<EventType>(this.getSequencer(),  new ConcurrentHashMap<Long, EventType>(events), controlBusList, this.startOffset, this.sequenceLength);
		
		this.postSequenceEvent(SequenceEventType.CLONED_SEQUENCE, SequenceEventSubtype.SEQUENCE, copy);
		
		return copy;
	}
	
	@Override
	public boolean eval(long tick) {
		//System.out.println("eval "+tick);
		if(tick == 0) {
			this.postSequenceEvent(SequenceEventType.STARTED, SequenceEventSubtype.NONE, null);
		} 
		
		if(tick > this.sequenceLength) {
			this.isRunning = false;
			return false;
		} else if(tick == this.sequenceLength) {
			this.isRunning = false;
			this.postSequenceEvent(SequenceEventType.STOPPED, SequenceEventSubtype.NONE, null);
			return false;
		}
	
		tick = tick+this.startOffset;
		EventType event;
		// random
		if((event = events.get(tick)) != null) {
			for(ControlBusInterface<EventType> bus: this.controlBuses) {
				bus.setValue(this, tick, event);
			}
		}
		
		return true;
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public void reset() {
		super.reset();
		
		for(ControlBusInterface<EventType> bus: this.controlBuses) {
				bus.setDefaultValue(this, 0);
		}
	}
	
	@Override
	public long size() {
		return this.sequenceLength;
	}

	@Override
	public void shift(long tick, long offset) {
		/*ConcurrentHashMap<Long, EventType> newMap = new ConcurrentHashMap<Long, EventType>();
		
		for(Entry<Long, EventType> entry: this.events.entrySet()) {
			if(entry.getValue() == t) {
				tick = entry.getKey();
			}
		}*/
	}

	@Override
	public void removeAllControlBusses() {
		for(ControlBusInterface<EventType> cb: controlBuses) {
			this.postSequenceEvent(SequenceEventType.REMOVE_CTRL_BUS, SequenceEventSubtype.CTRL_BUS, cb);
		}
		
		controlBuses.clear();
	}

	@Override
	public long getStartOffset() {
		return this.startOffset;
	}

	@Override
	public void setStartOffset(long ticks) {
		this.startOffset = ticks;
		
		this.postSequenceEvent(SequenceEventType.SET_START, SequenceEventSubtype.SIZE_IN_TICKS, ticks);
		this.postSequenceEvent(SequenceEventType.SEQUENCE_SIZE_CHANGED, SequenceEventSubtype.SIZE_IN_TICKS, ticks);
	}

	@Override
	public long getLength() {
		return this.sequenceLength;
	}

	@Override
	public void setLength(long length) {
		this.sequenceLength = length;
		this.postSequenceEvent(SequenceEventType.SET_LENGTH, SequenceEventSubtype.SIZE_IN_TICKS, length);
		this.postSequenceEvent(SequenceEventType.SEQUENCE_SIZE_CHANGED, SequenceEventSubtype.SIZE_IN_TICKS, length);
	}
	
	EventType _testingGetValueOfTick(long tick) {
		return this.events.get(tick);
	}
	
	public String toString() {
		return this.events.toString();
	}
}
