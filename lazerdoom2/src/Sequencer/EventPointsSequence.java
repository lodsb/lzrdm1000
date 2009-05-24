package Sequencer;

import java.util.ArrayList;
import java.util.Collections;

import Control.ControlBus;

public class EventPointsSequence<EventType> implements EventSequenceInterface<EventType> {

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
	
	ArrayList<EventContainer<EventType>> events = new ArrayList<EventContainer<EventType>>();
	
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
	}

	@Override
	public void setControlBus(ControlBus<EventType> cb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SequenceInterface deepCopy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean eval(long tick) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void shift(long tick, long offset) {
		// TODO Auto-generated method stub
		
	}

}
