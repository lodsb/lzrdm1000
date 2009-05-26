package Control;

import Control.Types.BaseType;

abstract public class ControlBus<T extends BaseType> {
	public abstract void appendMessage(BaseType baseType);
}
