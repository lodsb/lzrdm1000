package Control;

import java.util.LinkedList;

import Sequencer.BaseSequence;
import de.sciss.jcollider.ControlDesc;
import de.sciss.jcollider.Synth;
import Control.Types.BaseType;

public class TestingControlBus<T extends BaseType> implements ControlBusInterface<T> {

	public class ControlBusEntry {
		public T value;
		public long tick;
		public BaseSequence sequence;
		
		public ControlBusEntry(T value, long tick, BaseSequence sequence) {
			this.value = value;
			this.tick = tick;
			this.sequence = sequence;
		}
	}
	
	private LinkedList<ControlBusEntry> entries = new LinkedList<ControlBusEntry>();
	
	@Override
	public ControlDesc getControlDesc() {
		// TODO Auto-generated method stub
		return null;
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
		this.entries.add(new ControlBusEntry(baseType, tick, si));
	}

}
