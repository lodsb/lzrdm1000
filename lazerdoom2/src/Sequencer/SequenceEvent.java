package Sequencer;
public class SequenceEvent {
	public enum SequenceEventType {
		_internal_transportation_events,
		//TRANSPORTATION
		STARTED,
		STOPPED,
		EVALUATED_LOW_FREQ, 
		
		_internal_sequence_data_changed_events,
		//CONTAINERS
		APPEND_SEQUENCE,
		PREPEND_SEQUENCE,
		REMOVE_SEQUENCE,
		
		//EVENT SEQUENCES
		RESET,
		INSERT,
		SEQUENCE_SIZE_CHANGED,
		REMOVE,
		SHIFT,
		SET_START,
		SET_LENGTH,
		
		//CONTROL
		_internal_control_bus_events,
		ADD_CTRL_BUS,
		REMOVE_CTRL_BUS,
		
		//SEQUENCE PLAYER
		_internal_sequence_player_events,
		SEQUENCE_PLAYER_STARTED,
		SEQUENCE_PLAYER_STOPPED,
		SEQUENCE_PLAYER_STOPPING,
		SEQUENCE_PLAYER_STARTING,
		
		_internal_misc_events, 
		//CLONE
		CLONED_SEQUENCE,
		
	};

	enum SequenceMetaEventType {
		TRANSPORTATION_EVENT,
		SEQUENCE_DATA_CHANGED_EVENT,
		CONTROL_CHANGED_EVENT,
		SEQUENCE_PLAYER_EVENT,
		MISC_EVENT,
	}
	
	enum SequenceEventSubtype {
		NONE,
		SEQUENCE,
		TICK,
		SIZE_IN_TICKS,
		EVENT,
		CTRL_BUS,
		SEQUENCE_PLAYER,
	};
	
	private BaseSequence sequenceInterface;
	private SequenceEventType sequenceEventType;
	private SequenceEventSubtype sequenceEventSubtype;
	private Object arg;
	
	public SequenceEvent() {
		
	}
	
	public void setEvent(BaseSequence si, SequenceEventType sequenceEventType, SequenceEventSubtype subtype, Object arg) {
		this.sequenceInterface = si;
		this.sequenceEventType = sequenceEventType;
		this.sequenceEventSubtype = subtype;
		this.arg = arg;
	}
	
	SequenceEvent(BaseSequence si, SequenceEventType sequenceEventType, SequenceEventSubtype subtype, Object arg) {
		this.sequenceInterface = si;
		this.sequenceEventType = sequenceEventType;
		this.sequenceEventSubtype = subtype;
		this.arg = arg;
	}

	public BaseSequence getSource() {
		return sequenceInterface;
	}

	public SequenceEventType getSequenceEventType() {
		return sequenceEventType;
	}
	
	public SequenceEventSubtype getSequenceEventSubtype() {
		return sequenceEventSubtype;
	}
	
	public SequenceMetaEventType getSequenceMetaEventType() {
		SequenceMetaEventType mt = SequenceMetaEventType.MISC_EVENT;
		int ordinalSequenceEventType = this.sequenceEventType.ordinal();
		
		if(ordinalSequenceEventType < SequenceEventType._internal_sequence_data_changed_events.ordinal()) {
			mt = SequenceMetaEventType.TRANSPORTATION_EVENT;
		} else if (ordinalSequenceEventType < SequenceEventType._internal_control_bus_events.ordinal()) {
			mt = SequenceMetaEventType.SEQUENCE_DATA_CHANGED_EVENT;
		} else if(ordinalSequenceEventType < SequenceEventType._internal_sequence_player_events.ordinal()) {
			mt = SequenceMetaEventType.CONTROL_CHANGED_EVENT;
		} else if(ordinalSequenceEventType < SequenceEventType._internal_misc_events.ordinal()) {
			mt = SequenceMetaEventType.SEQUENCE_PLAYER_EVENT;
		}
		
		return mt;
	}
	
	public Object getArgument() {
		return arg;
	}
	
	public String toString() {
		return "Metatype: "+this.getSequenceMetaEventType()+"\nType: "+this.getSequenceEventType()+"\nSubtype: "+this.getSequenceEventSubtype()+"\nSource: "+this.getSource()+"\nArg: "+this.arg;
	}
	
}
