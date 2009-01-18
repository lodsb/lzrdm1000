package sequence;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


import com.trolltech.qt.core.*;

public class GenericSequenceController<P,T> extends QObject {
	private AbstractSequence<P,T> model;
	
	public Signal2<P, T> 	entryAdded;
	public Signal2<P, T> 	entryChanged;
	public Signal1<P>		entryRemoved;
	
	GenericSequenceController(AbstractSequence<P,T> model) {
		this.model = model;
		
	}
	
	public void addEntry(P point, T entry) {
		model.insert(point, entry);
		entryAdded.emit(point, entry);
	}
	
	public void modifyEntry(P point, T entry) {
		model.insert(point, entry);
		entryChanged.emit(point, entry);
	}
	
	public void removeEntry(P point) {
		model.remove(point);
	}
	
	public HashMap<P,T> getAllEntries() {
		return null;
	}
}
