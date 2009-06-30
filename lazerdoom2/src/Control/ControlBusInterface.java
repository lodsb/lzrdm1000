package Control;

import Sequencer.BaseSequence;
import Sequencer.SequenceInterface;
import Control.Types.BaseType;
import de.sciss.net.*;
import de.sciss.jcollider.*;

public interface ControlBusInterface<T extends BaseType> {
	void setSynthAndControlDesc(Synth synth, ControlDesc desc);
	public void setValue(BaseSequence si, long tick, T baseType);
	public void setDefaultValue(BaseSequence si, long tick);
	public ControlDesc getControlDesc();
}
