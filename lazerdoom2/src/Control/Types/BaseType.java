package Control.Types;

public interface BaseType<T> {
	public T defaultValue();
	public T getValue();
	public float getFloatValue();
	
	public void setValue(T t);
}
