package control.types;

public interface BaseType<T> {
	public T defaultValue();
	public T getValue();
	public float getFloatValue();
	// ugly hack to support multidimensional types (note: freq+gate);
	public float getFloatValue2();
	
	public void setValue(T t);
}
