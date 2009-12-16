package session;


import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import sequencer.BaseSequence;
import sequencer.EventPointsSequence;

import control.types.DoubleType;
import control.types.NoteType;

public class NoteEventPointsDataDescriptor extends BaseSequenceDataDescriptor {

	public class NoteDataEntry {
		public NoteDataEntry(long tick, int data, float gateOn, long length, int dataOff, float gateOff) {
			this.tickOn = tick;
			this.dataOn = data;
			this.gateOn = gateOn;
			
			this.length = length;
			this.dataOff = dataOff;
			this.gateOff = gateOff;
		}
		
		public long tickOn;
		public int dataOn;
		public float gateOn;
		public long length;
		
		public int dataOff;
		public float gateOff;
	}
	
	LinkedList<LinkedList<NoteDataEntry>> data;
	
	long startOffset;
	
	public LinkedList<LinkedList<NoteDataEntry>> getNoteData() {
		return this.data;
	}
	
	public NoteEventPointsDataDescriptor(BaseSequence sequence) {
		super(sequence);
		startOffset = 0;
	}
	
	@Override
	public void gatherData() {
		// more uglyness...
		super.gatherData();
		EventPointsSequence<NoteType> seq = (EventPointsSequence<NoteType>) SessionHandler.getInstance().getRegisteredObject(this.getSequenceID());
		this.startOffset = seq.getStartOffset();

		data = new LinkedList<LinkedList<NoteDataEntry>>();
		Iterator<Entry<Long, CopyOnWriteArrayList<NoteType>>> it = seq.getIterator();
		while(it.hasNext()) {
			Entry<Long, CopyOnWriteArrayList<NoteType>> entry = it.next();
			
			CopyOnWriteArrayList<NoteType> currentList = entry.getValue();
			LinkedList<NoteDataEntry> newList = new LinkedList<NoteDataEntry>();
			for(NoteType e: currentList) {
				if(!e.isNoteOff()) {
					NoteDataEntry nde = new NoteDataEntry(entry.getKey(),
														e.getNoteNumber(),
														e.getGateValue(),
														e.getLength(),
														e.getNoteOff().getNoteNumber(),
														e.getNoteOff().getGateValue());
					newList.add(nde);
				}
			}
			if(newList.size() > 0) {
				data.add(newList);
			}
		}
		
	}
	
	public long getStartOffset() {
		return this.startOffset;
	}
}
