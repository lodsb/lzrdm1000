package control;

import control.types.BaseType;
import de.sciss.jcollider.ControlDesc;

public interface ControlOperatorInterface<T extends BaseType> {
	
	public int numberOfOperands();
	
	public boolean consume(T operand);
	public BaseType getResult();
}
