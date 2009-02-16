package sequence.view;

import types.TypeHandlerInterface;

public interface TypehandlerDependent<T> {
	public void setTypeHandler(TypeHandlerInterface<T> t);
}
