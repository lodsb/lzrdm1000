package control;

import sequencer.BaseSequence;
import sequencer.SequenceInterface;
import control.types.BaseType;
import de.sciss.net.*;
import de.sciss.jcollider.*;

public interface ControlBusInterface<T extends BaseType> {
	void setSynthAndControlDesc(Synth synth, ControlDesc desc);
	public void setValue(BaseSequence si, long tick, T baseType);
	public void setDefaultValue(BaseSequence si, long tick);
	public ControlDesc getControlDesc();
}
