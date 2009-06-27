package Sequencer;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import com.trolltech.qt.QSignalEmitter.Signal1;
import com.trolltech.qt.core.QObject;

import Control.ControlBusInterface;
import Control.Types.BaseType;

public class EventPointsSequence<EventType extends BaseType> extends QObject implements EventSequenceInterface<EventType> {
	
	// TODO: 1) locking event-Array & processing // reentrant-lock implemented, better option available? evaluate!
	// 		 2) implement startoffset&endpoint
	
	private Signal1<Long> evalSignal = new Signal1<Long>();
	@Override
	public Signal1<Long> getSequenceEvalUpdateSignal() {
		return evalSignal;
	}
	
	private ReentrantLock eventQueueLock = new ReentrantLock();
	
	private class EventContainer<type> implements Comparable<EventContainer<type>> {
		long tick = 0;
		EventType event = null;
		
		@Override
		public int compareTo(EventContainer<type> o) {
			if(o.tick == this.tick) {
				return 0;
			} else if(o.tick > this.tick) {
				return -1;
			}
			
			return 1;
		}
	}
	
	ArrayList<EventContainer<EventType>> events;
	ArrayList<ControlBusInterface<EventType>> controlBuses;
	long startOffset = 0;
	long endPoint = 0;
	
	long oldTick = -1;
	int nextEventIndex = 0;
	int currentEventListSize = 0;
	long sizeOfAllEvents = 0;
	long nextEventTickSchedule = 0;
	EventType nextEvent = null;
	boolean isRunning = false;
	
	private EventPointsSequence(ArrayList<EventContainer<EventType>> events, ArrayList<ControlBusInterface<EventType>> controlBuses, long startOffset, long endPoint) {
		this.events = events;
		this.controlBuses = controlBuses;
		this.startOffset = startOffset;
		this.endPoint = endPoint;
	}
	
	public EventPointsSequence() {
		this.events = new ArrayList<EventContainer<EventType>>(); 
		this.controlBuses = new ArrayList<ControlBusInterface<EventType>>();
	} 
	
	
	@Override
	public void insert(EventType t, long tick) {
		EventContainer<EventType> container = new EventContainer<EventType>();
		
		container.tick = tick;
		container.event = t;
		
		eventQueueLock.lock();
		//TODO: improve performance
		int index = 0;
		for(EventContainer<EventType> eventContainer: events) {
			if(container.compareTo(eventContainer) < 0) {
				break;
			}
			index++;
		}
		
		events.add(index, container);
		currentEventListSize = events.size();
		
		updateSizeOfAllEvents();
		
		eventQueueLock.unlock();
	}

	@Override
	public void remove(long tick) {
		int index = -1;
		
		eventQueueLock.lock();
		
		for(EventContainer<EventType> eventContainer: events) {
			if(eventContainer.tick == tick) {
				break;
			}
			index++;
		}
		
		if(index > -1) {
			events.remove(index+1);
		}
		
		currentEventListSize = events.size();
		
		updateSizeOfAllEvents();
		
		eventQueueLock.unlock();
	}

	@Override
	public void remove(EventType t) {
		int index = -1;
		
		eventQueueLock.lock();
		
		for(EventContainer<EventType> eventContainer: events) {
			if(eventContainer.event == t) {
				break;
			}
			index++;
		}
		
		if(index > -1) {
			events.remove(index+1);
		}
		
		currentEventListSize = events.size();
		
		updateSizeOfAllEvents();
		
		eventQueueLock.unlock();
	}

	@Override
	public void addControlBus(ControlBusInterface<EventType> cb) {
		this.controlBuses.add(cb);
		
	}

	public void removeControlBus(ControlBusInterface<EventType> cb) {
		this.controlBuses.remove(cb);
	}
	
	@Override
	public SequenceInterface deepCopy() {
		EventContainer<EventType> container;
		ArrayList<EventContainer<EventType>> eventList = new ArrayList<EventContainer<EventType>>();
		
		eventQueueLock.lock();
		
		for(EventContainer<EventType> eventContainer: events) {
			container = new EventContainer<EventType>();
			container.tick = eventContainer.tick;
			container.event = eventContainer.event;
			eventList.add(container);
		}
		
		ArrayList<ControlBusInterface<EventType>> controlBusList = new ArrayList<ControlBusInterface<EventType>>();
		for(ControlBusInterface<EventType> bus: controlBuses) {
			controlBusList.add(bus);
		}
		
		eventQueueLock.unlock();

		return new EventPointsSequence<EventType>(eventList, controlBusList, this.startOffset, this.endPoint);
	}

	
	private void queueAndProcessNextEvents(long tick) {
		if(nextEventTickSchedule == 0) {
			
			for(ControlBusInterface<EventType> bus: this.controlBuses) {
				bus.setValue(this, tick, nextEvent);
			}
			
			eventQueueLock.lock();
			
			if(nextEventIndex+1 < currentEventListSize) {
				isRunning = true;
				
				nextEventIndex++;
				EventContainer<EventType> container = events.get(nextEventIndex);
				nextEventTickSchedule = (tick%sizeOfAllEvents) - container.tick; 
				nextEvent = container.event;
			} else {
				// EOF
				isRunning = false;
				
				EventContainer<EventType> container = events.get(0);
				nextEventTickSchedule = (tick%sizeOfAllEvents) - container.tick; 
				nextEvent = container.event;
				
			}
			
			eventQueueLock.unlock();
		} 
		
		nextEventTickSchedule--;
	}
	
	@Override
	public boolean eval(long tick) {
		if(oldTick == tick-1) {
			//subsequent ticks
			queueAndProcessNextEvents(tick);
		} else {
			int index = -1;
			
			eventQueueLock.lock();
			
			for(EventContainer<EventType> eventContainer: events) {
				if(eventContainer.tick >= tick) {
					break;
				}
				index++;
			}
			
			if(index > -1) {
				EventContainer<EventType> container = events.get(index);
				nextEventTickSchedule = (tick%sizeOfAllEvents) - container.tick; 
				nextEvent = container.event;
				nextEventIndex = index;
				
				queueAndProcessNextEvents(tick);
			} else {
				isRunning= false;
			}
			
			eventQueueLock.unlock();
		}
		
		oldTick = tick;
		return isRunning;
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public void reset() {
		
		eventQueueLock.lock();
		
		EventContainer<EventType> eventContainer = null;
		
		for(ControlBusInterface<EventType> bus: this.controlBuses) {
				bus.setDefaultValue(this, 0);
		}
		eventQueueLock.unlock();
	}

	private void updateSizeOfAllEvents() {
		sizeOfAllEvents = 0;
		
		EventContainer<EventType> container = null;
		if((container = this.events.get(this.events.size()-1)) != null) {
			sizeOfAllEvents = container.tick;
		}
	}
	
	@Override
	public long size() {
		return sizeOfAllEvents;	
	}

	@Override
	public void shift(long tick, long offset) {
		
		long lastTick = 0;
		boolean found = false;
		
		eventQueueLock.lock();
		
		for(EventContainer<EventType> eventContainer: events) {
			if(found || eventContainer.tick >= tick) {
				eventContainer.tick += offset;
				lastTick = eventContainer.tick+1;
			} 
		}					
		
		
		ArrayList<Integer> toDeleteIndexes = new ArrayList<Integer>();
		long containerTick = 0;
		
		if(offset < 0) {
			// remove overlapping elements
			for(int i = events.size()-1; i >= 0; i--) {
				containerTick = events.get(i).tick;

				if(containerTick >= lastTick) {
					toDeleteIndexes.add(i);
				}

				lastTick = containerTick;
			}

			for(int index: toDeleteIndexes) {
				events.remove(index);
			}
		}
		
		
		eventQueueLock.unlock();
		
	}

	@Override
	public void removeAllControlBusses() {
		controlBuses.clear();
	}

	@Override
	public long getEndPoint() {
		return this.endPoint;
	}

	@Override
	public long getStartOffset() {
		return this.startOffset;
	}

	@Override
	public void setEndPoint(long ticks) {
		this.endPoint = ticks;
	}

	@Override
	public void setStartOffset(long ticks) {
		this.startOffset = ticks;
	}

}
