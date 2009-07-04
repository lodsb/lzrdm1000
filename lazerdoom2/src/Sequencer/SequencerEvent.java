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
	
	SequencerEvent(SequencerEventType type, SequencerEventSubtype subtype) {
		eventType = type;
		subType = subtype;
	}
	
	public SequencerEventType getEventType() {
		return eventType;
	}
	
	public SequencerEventSubtype getEventSubtype() {
		return subType;
	}
}
