package sparshui.common.messages.events;

import sparshui.common.TouchState;

public class TapEvent extends TouchEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3057591266391991841L;
	
	public TapEvent(int id, float x, float y) {
		super(id,x,y,TouchState.DEATH);
	}

}
