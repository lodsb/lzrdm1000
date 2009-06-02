package Control;

import Control.Types.BaseType;
import de.sciss.net.*;

public interface ControlBusInterface<T extends BaseType> {
	public void setValue(T baseType);
}
