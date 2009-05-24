package Sequencer;

import Control.ControlBus;

public interface EventSequenceInterface<EventType> extends SequenceInterface {
	public void insert(EventType t, long tick);
	public void remove(long tick);
	public void remove(EventType t);
	public void shift(long tick, long offset);
	
	public void setControlBus(ControlBus<EventType> cb);
}
