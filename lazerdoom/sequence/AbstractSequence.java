package sequence;

public abstract class AbstractSequence<P,T> {
	abstract void insert(P point, T o);
	abstract void insertSequence(P point, AbstractSequence<P,T> s);
	
	abstract T getAt(P point);
	
	abstract void remove(P point);
	abstract void removePeriod(P start, P end);
	
	abstract long length();

	abstract AbstractSequence<P,T> deepCopy();
}
