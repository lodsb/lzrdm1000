package Control;

import java.util.ArrayList;
import java.util.LinkedList;
import Testing.*;

import Sequencer.BaseSequence;
import de.sciss.jcollider.ControlDesc;
import de.sciss.jcollider.Synth;
import Control.Types.BaseType;

public class TestingControlBus<T extends BaseType> implements ControlBusInterface<T> {

	public class ControlBusEntry {
		public float value;
		public long tick;
		public BaseSequence sequence;
		
		public ControlBusEntry(float value, long tick, BaseSequence sequence) {
			this.value = value;
			this.tick = tick;
			this.sequence = sequence;
		}
		
		public String toString() {
			return "value: "+value+"\ntick: "+tick+"\nSequence "+sequence;
		}
	}
	
	private LinkedList<ControlBusEntry> entries = new LinkedList<ControlBusEntry>();
	
	@Override
	public ControlDesc getControlDesc() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void clearEntriesAndReset() {
		this.entries.clear();
	}

	public LinkedList<ControlBusEntry> getControlBusEntryList() {
		return entries;
	}
	
	@Override
	public void setDefaultValue(BaseSequence si, long tick) {
	}

	@Override
	public void setSynthAndControlDesc(Synth synth, ControlDesc desc) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setValue(BaseSequence si, long tick, T baseType) {
		this.entries.add(new ControlBusEntry(baseType.getFloatValue(), tick, si));
	}

}
