package lazerdoom;

public interface ResourcePoolInterface<T,T2> extends ResourceInterface<T>{
	public ResourceInterface<T2> create(T2 resource);
	public ResourceInterface<T2> createNew();
	
	public void remove(ResourceInterface<T2> resource);
}
