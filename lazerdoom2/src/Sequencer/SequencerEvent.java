package Sequencer;

public class SequencerEvent {
	enum SequencerEventType {
		SEQUENCE_PLAYER_ADDED,
		SEQUENCE_PLAYER_REMOVED
	}
	
	enum SequencerEventSubtype {
		NONE,
		SEQUENCE_PLAYER
	}
	
	SequencerEventType eventType;
	SequencerEventSubtype subType;
	Object arg;
	
	SequencerEvent(SequencerEventType type, SequencerEventSubtype subtype, Object arg) {
		eventType = type;
		subType = subtype;
	}
	
	public SequencerEventType getEventType() {
		return eventType;
	}
	
	public SequencerEventSubtype getEventSubtype() {
		return subType;
	}
	
	public Object getArgument() {
		return arg;
	}
}
