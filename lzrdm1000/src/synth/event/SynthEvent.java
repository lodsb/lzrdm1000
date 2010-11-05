package synth.event;

public class SynthEvent {
	private Object event;
	private int id;
	
	public SynthEvent(int id, Object event) {
		this.id = id;
		this.event = event;
	}
	
	public int getID() {
		return id;
	}
	
	public Object getEvent() {
		return event;
	}
}
