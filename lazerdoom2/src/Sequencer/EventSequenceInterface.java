package Sequencer;

import Control.ControlBus;
import Control.Types.BaseType;

public interface EventSequenceInterface<EventType extends BaseType> extends SequenceInterface {
	public void insert(EventType t, long tick);
	public void remove(long tick);
	public void remove(EventType t);
	public void shift(long tick, long offset);
	
	public void setStartOffset(long ticks);
	public long getStartOffset();
	public void setEndPoint(long ticks);
	public long getEndPoint();
	
	public void addControlBus(ControlBus<EventType> cb);
	public void removeControlBus(ControlBus<EventType> cb);
	public void removeAllControlBusses();
}
