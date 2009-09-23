package Sequencer;

public class SequencerEvent {
	enum SequencerEventType {
		SEQUENCE_PLAYER_ADDED,
		SEQUENCE_PLAYER_REMOVED,
		EVENT_POINTS_SEQUENCE_ADDED,
		EVENT_POINTS_SEQUENCE_REMOVED,
		PAUSE_ADDED,
		PAUSE_REMOVED,
		CONNECTION_ADDED,
		CONNECTION_REMOVED
	}
	
	enum SequencerEventSubtype {
		NONE,
		SEQUENCE_PLAYER,
		EVENT_POINTS_SEQUENCE,
		PAUSE,
		SEQUENCE_PAIR
	}
	
	SequencerEventType eventType;
	SequencerEventSubtype subType;
	Object arg;
	
	SequencerEvent(SequencerEventType type, SequencerEventSubtype subtype, Object arg) {
		this.eventType = type;
		this.subType = subtype;
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
