package sequence;

import java.util.*;
import java.util.Map.Entry;


public class GenericTimelineSequence<T> extends AbstractSequence<Long, T> {
	private class GenericSequenceNode<T> {
		public AbstractSequence<Long,T> sequence = null;
		public Long timestamp = null;
		public T value = null;
	}
	
	// Temporary sequence data handling; possible improvements:
	// sequence data should be saved in a relative manner
	// BPtree with prefetching
	
	// Temporary container 
	SortedMap<Long, GenericSequenceNode<T>> sequenceMap;
	
	private static int blkSize = 16;
	private static int noLeafNodes = 64;
	
	private GenericTimelineSequence(SortedMap<Long, GenericSequenceNode<T>> s) {
		this.sequenceMap = s;
	}
	
	public GenericTimelineSequence() {
		this.sequenceMap = new BPTree<Long, GenericSequenceNode<T>>(blkSize,noLeafNodes);
	}
	
	@Override
	T getAt(Long timestamp) {
		T ret;
		GenericSequenceNode<T> node = sequenceMap.get(timestamp);
		
		if(node != null) {
			if(node.sequence == null) {
				ret = node.value;
			} else {
				ret = node.sequence.getAt(node.timestamp);
			}
			
			return ret;
		}
		
		return null;
	}

	@Override
	void insert(Long timestamp, T o) {
		GenericSequenceNode<T> node = new GenericSequenceNode<T>();
		node.value = o;
		sequenceMap.put(timestamp, node);
	}

	@Override
	void insertSequence(Long timestamp, AbstractSequence<Long, T> s) {		
		long sequenceLength = s.length();
		for(long t = 0; t < sequenceLength; t++) {
			GenericSequenceNode<T> node = new GenericSequenceNode<T>();
			node.sequence = s;
			node.timestamp = t;
			sequenceMap.put(timestamp, node);
		}
	}

	@Override
	void remove(Long timestamp) {
		sequenceMap.remove(timestamp);
	}

	@Override
	void removePeriod(Long timeStart, Long timeEnd) {
		long length = timeEnd - timeEnd;
		
		for(long t = 0; t <= length; t++) {
			sequenceMap.remove(t+timeStart);
		}
	}

	@Override
	AbstractSequence<Long, T> deepCopy() {
		SortedMap<Long, GenericSequenceNode<T>> newSeqMap;
		newSeqMap = new BPTree<Long, GenericSequenceNode<T>>(blkSize,noLeafNodes);
		
		Iterator<Entry<Long, GenericSequenceNode<T>>> iterator = sequenceMap.entrySet().iterator();
		while(iterator.hasNext()) {
			GenericSequenceNode<T> newNode = new GenericSequenceNode<T>();
			
			Entry<Long, GenericSequenceNode<T>> entry = iterator.next();
			GenericSequenceNode<T> cpyNode = entry.getValue();
			
			if(cpyNode.sequence != null) {
				AbstractSequence<Long,T> ns = cpyNode.sequence.deepCopy();
				newNode.sequence = ns;
				newNode.timestamp = cpyNode.timestamp;
			} else {
				// value should be copied!
				// FIXME!
				newNode.value = cpyNode.value;
			}  
			
			newSeqMap.put(entry.getKey(), newNode);
		}
		return new GenericTimelineSequence<T>(newSeqMap);
	}
	
	@Override
	long length() {
		return sequenceMap.size();
	}

}
