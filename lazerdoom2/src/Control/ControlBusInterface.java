package Control;

import Control.Types.BaseType;
import de.sciss.net.*;
import de.sciss.jcollider.*;

public interface ControlBusInterface<T extends BaseType> {
	public void setSynthAndParameter(Synth synth, String parameter);
	public void setValue(T baseType);
}
