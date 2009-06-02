package Control;

import Control.Types.BaseType;

public interface ControlOperatorInterface<T extends BaseType> {
	
	public int numberOfOperands();
	
	public boolean consume(T operand);
	public BaseType getResult();
	
}
