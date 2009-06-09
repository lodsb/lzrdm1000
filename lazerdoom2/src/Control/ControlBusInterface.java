package Control;

import Control.Types.BaseType;
import de.sciss.net.*;
import de.sciss.jcollider.*;

public interface ControlBusInterface<T extends BaseType> {
	void setSynthAndControlDesc(Synth synth, ControlDesc desc);
	public void setValue(T baseType);
	public void setDefaultValue();
	public ControlDesc getControlDesc();
}
