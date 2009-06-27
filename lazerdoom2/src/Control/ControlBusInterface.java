package Control;

import Sequencer.SequenceInterface;
import Control.Types.BaseType;
import de.sciss.net.*;
import de.sciss.jcollider.*;

public interface ControlBusInterface<T extends BaseType> {
	void setSynthAndControlDesc(Synth synth, ControlDesc desc);
	public void setValue(SequenceInterface si, long tick, T baseType);
	public void setDefaultValue(SequenceInterface si, long tick);
	public ControlDesc getControlDesc();
}
