package lazerdoom;

public interface ResourceInterface<T> {
	public void setName(String name);
	public String getName();
	
	public void load(T resource);
	public void save(T resource);
}
