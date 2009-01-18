package sequence.scene;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import com.mallardsoft.tuple.*;

import sequence.GenericSequenceController;

import com.trolltech.qt.gui.*;

public abstract class AbstractGenericSequenceScene<P,T> extends QGraphicsScene {

	private class GenericSequenceSceneSequenceItemGroup<P,T> {
		public LinkedHashMap<P, SequenceSceneItemInterface<T>> timestampToSceneItems;
		public LinkedHashMap<SequenceSceneItemInterface<T>, P> sceneItemsToTimestamp;
		
		GenericSequenceSceneSequenceItemGroup() {
			timestampToSceneItems = new LinkedHashMap<P, SequenceSceneItemInterface<T>>();
			sceneItemsToTimestamp = new LinkedHashMap<SequenceSceneItemInterface<T>, P>();
		}
		
		public void put(P point, SequenceSceneItemInterface<T> item) {
			timestampToSceneItems.put(point, item);
			sceneItemsToTimestamp.put(item, point);
		}
		
		public void remove(P point) {
			SequenceSceneItemInterface<T> item;
			if((item = timestampToSceneItems.get(point)) != null) {
				sceneItemsToTimestamp.remove(item);
				timestampToSceneItems.remove(point);
			}
		}
		
		public void remove(SequenceSceneItemInterface<T> item) {
			P point;
			if((point = sceneItemsToTimestamp.get(item)) != null) {
				sceneItemsToTimestamp.remove(item);
				timestampToSceneItems.remove(point);
			}
		}
		
		public SequenceSceneItemInterface<T> get(P point) {
			return timestampToSceneItems.get(point);
		}
		
		public P get(SequenceSceneItemInterface<T> item) {
			return sceneItemsToTimestamp.get(item);
		}
	}
	
	public Signal2<SequenceSceneItemInterface<T>,T> sequenceItemModifying = new Signal2<SequenceSceneItemInterface<T>,T>();
	public Signal2<SequenceSceneItemInterface<T>,T> sequenceItemModified = new Signal2<SequenceSceneItemInterface<T>,T>();
	
	private GenericSequenceController<P,T> currentSequenceController = null;
	
	// This container is used to map sequences to their respecitve graphic items (used for toggling show etc)
	// qgraphicsitemgroups are not used since the parent item merges the childrens' events etc.
	
	private LinkedHashMap<GenericSequenceController<P,T>, GenericSequenceSceneSequenceItemGroup<P,T>> sequenceControllersToSequenceItems = new LinkedHashMap<GenericSequenceController<P,T>, GenericSequenceSceneSequenceItemGroup<P,T>>(); 
	
	public void addSequenceController(GenericSequenceController<P,T> sequenceController) {
		currentSequenceController = sequenceController;
		if(!sequenceControllersToSequenceItems.containsKey(sequenceController)) {
			// add all elements to the scene
			Map<P,T> entries = sequenceController.getAllEntries();
			
			GenericSequenceSceneSequenceItemGroup<P,T> sceneItems = new GenericSequenceSceneSequenceItemGroup<P,T>();
			for(Map.Entry<P,T> entry: entries.entrySet()) {
				SequenceSceneItemInterface<T> sceneItem = createSequenceItem(entry.getValue());
				QGraphicsItem graphicsItem = sceneItem.getRepresentation();
				
				sceneItem.getModifiedSignal().connect(this, "sequenceItemModified(SequenceSceneItemInterface<T> item, T entry)");
				sceneItem.getModifyingSignal().connect(this, "sequenceItemModifying(SequenceSceneItemInterface<T> item, T entry)");
				
				P point = entry.getKey();
				
				addGraphicsItemToScene(point, graphicsItem);
				graphicsItem.show();
				
				sceneItems.put(point, sceneItem);
			}
			sequenceControllersToSequenceItems.put(sequenceController, sceneItems);
		}
		
		currentSequenceController = sequenceController;
	}
	
	protected abstract SequenceSceneItemInterface<T> createSequenceItem(T entry);
	
	protected abstract void addGraphicsItemToScene(P point, QGraphicsItem item);
	
	public void removeSequenceController(GenericSequenceController<P,T> sequenceController) {
		GenericSequenceSceneSequenceItemGroup<P,T> sceneItems = sequenceControllersToSequenceItems.get(sequenceController);
		if(sceneItems != null) {
			for(SequenceSceneItemInterface<T> sceneItem: sceneItems.timestampToSceneItems.values()) {
				this.removeItem(sceneItem.getRepresentation());
			}
			
			sequenceControllersToSequenceItems.remove(sequenceController);
		}
	}
	
	public void showSequenceControllerEntries(GenericSequenceController<P,T> sequenceController, boolean visible) {
		GenericSequenceSceneSequenceItemGroup<P,T> sceneItems = sequenceControllersToSequenceItems.get(sequenceController);
		if(sceneItems != null) {
			for(SequenceSceneItemInterface<T> sceneItem: sceneItems.timestampToSceneItems.values()) {
				sceneItem.getRepresentation().setVisible(visible);
			}
		}
	}
	
	private void sequenceItemModified(SequenceSceneItemInterface<T> item, T entry) {
		GenericSequenceSceneSequenceItemGroup<P,T> sceneItems;
		// FIXME: disable signal slots for the modified signal
		for(Map.Entry<GenericSequenceController<P,T>, GenericSequenceSceneSequenceItemGroup<P,T>> mapEntry: sequenceControllersToSequenceItems.entrySet()) {
			sceneItems = mapEntry.getValue();
			if(sceneItems != null) {
				P point;
				if((point = sceneItems.get(item)) != null) {
					mapEntry.getKey().modifyEntry(point, entry);
				}
			}
		} 
	}
	
	private void sequenceItemModifying(SequenceSceneItemInterface<T> item, T entry) {
		sequenceItemModifying.emit(item, entry);
	}
	
	private void entryAddedToSequence(P point, T entry) {
		GenericSequenceSceneSequenceItemGroup<P,T> sceneItems;
		
		// FIXME: possible bug when no controller has been added
		if(currentSequenceController != null) {
			sceneItems = sequenceControllersToSequenceItems.get(currentSequenceController);
			if(sceneItems != null) {
				SequenceSceneItemInterface<T> sceneItem = createSequenceItem(entry);
				QGraphicsItem graphicsItem = sceneItem.getRepresentation();
				
				addGraphicsItemToScene(point, graphicsItem);
				graphicsItem.show();
				
				sceneItems.put(point, sceneItem);
			}
		} 
	}
	
	private void entryRemovedFromSequence(P point) {
		GenericSequenceSceneSequenceItemGroup<P,T> sceneItems;
		
		// FIXME: possible bug when no controller has been added
		if(currentSequenceController != null) {
			sceneItems = sequenceControllersToSequenceItems.get(currentSequenceController);
			if(sceneItems != null) {
				SequenceSceneItemInterface<T> sceneItem;
				if((sceneItem = sceneItems.get(point)) != null) {
					QGraphicsItem graphicsItem = sceneItem.getRepresentation();
					this.removeItem(graphicsItem);
					sceneItems.remove(point);
				}
			}
		} 
	}
	
	private void entryModifiedInSequence(P point, T entry) {
		GenericSequenceSceneSequenceItemGroup<P,T> sceneItems;
		
		// FIXME: possible bug when no controller has been added
		if(currentSequenceController != null) {
			sceneItems = sequenceControllersToSequenceItems.get(currentSequenceController);
			if(sceneItems != null) {
				SequenceSceneItemInterface<T> sceneItem;
				if((sceneItem = sceneItems.get(point)) != null) {
					sceneItem.update(entry);
					sequenceItemModified.emit(sceneItem, entry);
				}
			}
		} 
	}
}
