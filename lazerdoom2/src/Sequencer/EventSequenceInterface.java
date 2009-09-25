package Sequencer;

import Control.ControlBusInterface;
import Control.Types.BaseType;

public interface EventSequenceInterface<EventType extends BaseType> extends SequenceInterface {
	//package
	void insert(EventType t, long tick);
	void remove(long tick, EventType t);
	//void remove(EventType t);
	void shift(long tick, long offset);
	
	void setLength(long length);
	void setStartOffset(long tick);
	long getStartOffset();
	long getLength();
	
	void addControlBus(ControlBusInterface<EventType> cb);
	void removeControlBus(ControlBusInterface<EventType> cb);
	void removeAllControlBusses();
}
