package Sequencer;
public class SequenceEvent {
	enum SequenceEventType {
		//TRANSPORTATION
		STARTED,
		STOPPED,
		
		//CONTAINERS
		APPEND_SEQUENCE,
		PREPEND_SEQUENCE,
		REMOVE_SEQUENCE,
		
		//CLONE
		CLONED_SEQUENCE,
		
		//EVENT SEQUENCES
		RESET,
		INSERT,
		SEQUENCE_SIZE_CHANGED,
		REMOVE,
		SHIFT,
		SET_START,
		SET_END,
		
		//CONTROL
		ADD_CTRL_BUS,
		REMOVE_CTRL_BUS
	};
	
	enum SequenceEventSubtype {
		NONE,
		SIZE_IN_TICKS
	};
	
	private BaseSequence sequenceInterface;
	private SequenceEventType sequenceEventType;
	private SequenceEventSubtype sequenceEventSubtype;
	private Object arg;
	
	SequenceEvent(BaseSequence si, SequenceEventType sequenceEventType, SequenceEventSubtype subtype, Object arg) {
		this.sequenceInterface = si;
		this.sequenceEventType = sequenceEventType;
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
	
	public Object getArgument() {
		return arg;
	}
}
