package Sequencer;

import java.util.ArrayList;
import java.util.Collections;

import Control.ControlBus;
import Control.Types.BaseType;

public class EventPointsSequence<EventType extends BaseType> implements EventSequenceInterface<EventType> {
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
	ArrayList<ControlBus<EventType>> controlBuses;
	long startOffset = 0;
	long endPoint = 0;
	
	long oldTick = -1;
	int nextEventIndex = 0;
	int currentEventListSize = 0;
	long sizeOfAllEvents = 0;
	long nextEventTickSchedule = 0;
	EventType nextEvent = null;
	boolean isRunning = false;
	
	private EventPointsSequence(ArrayList<EventContainer<EventType>> events, ArrayList<ControlBus<EventType>> controlBuses, long startOffset, long endPoint) {
		this.events = events;
		this.controlBuses = controlBuses;
		this.startOffset = startOffset;
		this.endPoint = endPoint;
	}
	
	public EventPointsSequence() {
		this.events = new ArrayList<EventContainer<EventType>>(); 
		this.controlBuses = new ArrayList<ControlBus<EventType>>();
	} 
	
	
	@Override
	public void insert(EventType t, long tick) {
		EventContainer<EventType> container = new EventContainer<EventType>();
		
		container.tick = tick;
		container.event = t;
		
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
	}

	@Override
	public void remove(long tick) {
		int index = -1;
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
	}

	@Override
	public void remove(EventType t) {
		int index = -1;
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
	}

	@Override
	public void addControlBus(ControlBus<EventType> cb) {
		this.controlBuses.add(cb);
		
	}

	public void removeControlBus(ControlBus<EventType> cb) {
		this.controlBuses.remove(cb);
	}
	
	@Override
	public SequenceInterface deepCopy() {
		EventContainer<EventType> container;
		ArrayList<EventContainer<EventType>> eventList = new ArrayList<EventContainer<EventType>>();
		
		for(EventContainer<EventType> eventContainer: events) {
			container = new EventContainer<EventType>();
			container.tick = eventContainer.tick;
			container.event = eventContainer.event;
			eventList.add(container);
		}
		
		ArrayList<ControlBus<EventType>> controlBusList = new ArrayList<ControlBus<EventType>>();
		for(ControlBus<EventType> bus: controlBuses) {
			controlBusList.add(bus);
		}

		return new EventPointsSequence<EventType>(eventList, controlBusList, this.startOffset, this.endPoint);
	}

	
	private void queueAndProcessNextEvents(long tick) {
		if(nextEventTickSchedule == 0) {
			
			for(ControlBus<EventType> bus: this.controlBuses) {
				bus.appendMessage(nextEvent);
			}
			
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
		EventContainer<EventType> eventContainer = null;
		
		if((eventContainer = events.get(0)) != null) {
			for(ControlBus<EventType> bus: this.controlBuses) {
				bus.appendMessage(eventContainer.event.defaultValue());
			}
		}
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
