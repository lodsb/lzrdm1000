package session;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import sequencer.BaseSequence;
import sequencer.EventPointsSequence;

import control.types.DoubleType;


public class DoubleEventPointsDataDescriptor extends BaseSequenceDataDescriptor {

	public class DoubleDataEntry {
		public DoubleDataEntry(long tick, double data) {
			this.tick = tick;
			this.data = data;
		}
		
		public long tick;
		public double data;
	}
	
	LinkedList<LinkedList<DoubleDataEntry>> data;
	
	long startOffset;
	
	public LinkedList<LinkedList<DoubleDataEntry>> getDoubleData() {
		return this.data;
	}
	
	public DoubleEventPointsDataDescriptor(BaseSequence sequence) {
		super(sequence);
		startOffset = 0;
	}
	
	@Override
	public void gatherData() {
		super.gatherData();
		EventPointsSequence<DoubleType> seq = (EventPointsSequence<DoubleType>) SessionHandler.getInstance().getRegisteredObject(this.getSequenceID());
		this.startOffset = seq.getStartOffset();

		data = new LinkedList<LinkedList<DoubleDataEntry>>();
		Iterator<Entry<Long, CopyOnWriteArrayList<DoubleType>>> it = seq.getIterator();
		while(it.hasNext()) {
			Entry<Long, CopyOnWriteArrayList<DoubleType>> entry = it.next();
			
			CopyOnWriteArrayList<DoubleType> currentList = entry.getValue();
			LinkedList<DoubleDataEntry> newList = new LinkedList<DoubleDataEntry>();
			for(DoubleType e: currentList) {
				newList.add(new DoubleDataEntry(entry.getKey(), e.getValue()));
			}
			
			data.add(newList);
		}
		
	}
	
	public long getStartOffset() {
		return this.startOffset;
	}

}
