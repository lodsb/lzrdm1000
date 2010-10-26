package sequence;

import java.util.HashMap;

public abstract class AbstractSequence<P,T> implements SequenceInterface {
	public abstract void insert(P point, T o);
	public abstract void insertSequence(P point, AbstractSequence<P,T> s);
	
	public abstract T getAt(P point);
	
	public abstract void remove(P point);
	public abstract void removePeriod(P start, P end);
	
	public abstract P length();

	public abstract AbstractSequence<P,T> deepCopy();
	public abstract HashMap<P, T> getAllEntries();
}
