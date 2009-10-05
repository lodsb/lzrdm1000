package Sequencer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

import lazerdoom.LzrDmObjectInterface;

import Sequencer.SequenceEvent.SequenceEventSubtype;
import Sequencer.SequenceEvent.SequenceEventType;

import com.trolltech.qt.QSignalEmitter.Signal1;
import com.trolltech.qt.core.QObject;

import Control.ControlBusInterface;
import Control.TestingControlBus;
import Control.Types.BaseType;
import Control.Types.DoubleType;


public class EventPointsSequence<EventType extends BaseType> extends BaseSequence implements EventSequenceInterface<EventType>, LzrDmObjectInterface {
	
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
	
	Map<Long, CopyOnWriteArrayList<EventType>> events;
	
	long startOffset = 0;
	long sequenceLength = 0;
	
	boolean isRunning = false;
	
	public Iterator<Entry<Long, CopyOnWriteArrayList<EventType>>> getIterator() {
		return this.events.entrySet().iterator();
	}
	
	private EventPointsSequence(SequencerInterface seq, Map<Long, CopyOnWriteArrayList<EventType>> events, CopyOnWriteArrayList<ControlBusInterface<EventType>> controlBuses, long startOffset, long sequenceLength) {
		super(seq);
		this.events = events;
		this.controlBuses = controlBuses;
		this.startOffset = startOffset;
		this.sequenceLength = sequenceLength;
	}
	
	public EventPointsSequence(SequencerInterface seq) {
		super(seq);
		this.events =  new ConcurrentHashMap<Long, CopyOnWriteArrayList<EventType>>(); 
		this.controlBuses = new CopyOnWriteArrayList<ControlBusInterface<EventType>>();
	} 
	
	
	@Override
	public void insert(EventType t, long tick) {
		CopyOnWriteArrayList<EventType> list;
		if((list = this.events.get(tick)) != null) {
			list.add(t);
		} else {
			list = new CopyOnWriteArrayList<EventType>();
			list.add(t);
			this.events.put(tick, list);
		}
		this.postSequenceEvent(SequenceEventType.INSERT, SequenceEventSubtype.TICK, tick);
	}

	@Override
	public void remove(long tick, EventType t) {
		CopyOnWriteArrayList<EventType> list;
		
		if((list = this.events.get(tick)) != null) {
			System.out.println(list);
			list.remove(t);
			System.out.println("WHAT "+list);
			
			if(list.size() == 0) {
				this.events.remove(tick);
			}
		}

		this.postSequenceEvent(SequenceEventType.REMOVE, SequenceEventSubtype.TICK, tick);
	}

	/*@Override
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
	}*/

	@Override
	public void addControlBus(ControlBusInterface<EventType> cb) {
		if(!this.controlBuses.contains(cb)) {
			this.controlBuses.add(cb);
			this.postSequenceEvent(SequenceEventType.ADD_CTRL_BUS, SequenceEventSubtype.CTRL_BUS, cb);
		}
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
		
		Iterator<Entry<Long, CopyOnWriteArrayList<EventType>>> it = this.getIterator();
		ConcurrentHashMap<Long, CopyOnWriteArrayList<EventType>> map = new ConcurrentHashMap<Long, CopyOnWriteArrayList<EventType>>();
		
		while(it.hasNext()) {
			Entry<Long, CopyOnWriteArrayList<EventType>> entry;
			entry = it.next();
	
			map.put(entry.getKey(), entry.getValue());
		}
		
		copy = new EventPointsSequence<EventType>(this.getSequencer(),  map , controlBusList, this.startOffset, this.sequenceLength);
		
		this.postSequenceEvent(SequenceEventType.CLONED_SEQUENCE, SequenceEventSubtype.SEQUENCE, copy);
		
		return copy;
	}
	
	private CopyOnWriteArrayList<EventType> eventList;
	@Override
	public boolean eval(long tick) {
		//System.out.println("eval "+tick);
		if(tick == 0) {
			this.postSequenceEvent(SequenceEventType.STARTED, SequenceEventSubtype.NONE, null);
		} 
		
		if(tick > (this.sequenceLength-this.startOffset)+1) {
			this.isRunning = false;
			return false;
		} else if(tick == (this.sequenceLength-this.startOffset)-1) {
			this.isRunning = false;
			this.postSequenceEvent(SequenceEventType.STOPPED, SequenceEventSubtype.NONE, null);
			for(ControlBusInterface<EventType> bus: this.controlBuses) {
				bus.setDefaultValue(this, tick);
			}
			return false;
		}
	
		tick = tick+this.startOffset;
		// random
		super.eval(tick);
		if((eventList = events.get(tick)) != null) {
			for(ControlBusInterface<EventType> bus: this.controlBuses) {
				for(EventType event: eventList) {
					System.out.println("@tick "+tick);
					bus.setValue(this, tick, event);
				}
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
		return this.getCompleteSize();
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
		
		this.postSequenceEvent(SequenceEventType.SET_START, SequenceEventSubtype.SIZE_IN_TICKS, this.startOffset);
		this.postSequenceEvent(SequenceEventType.SEQUENCE_SIZE_CHANGED, SequenceEventSubtype.SIZE_IN_TICKS, this.getCompleteSize());
	}

	@Override
	public long getLength() {
		return this.sequenceLength;
	}
	
	private long getCompleteSize() {
		return this.sequenceLength-this.startOffset;
	}

	@Override
	public void setLength(long length) {
		this.sequenceLength = length;
		this.postSequenceEvent(SequenceEventType.SET_LENGTH, SequenceEventSubtype.SIZE_IN_TICKS, this.getLength());
		this.postSequenceEvent(SequenceEventType.SEQUENCE_SIZE_CHANGED, SequenceEventSubtype.SIZE_IN_TICKS, this.getCompleteSize());
	}
	
	EventType _testingGetValueOfTick(long tick) {
		return null; //this.events.get(tick);
	}
	
	public String toString() {
		return this.events.toString();
	}
}
