package GUI.Editor.SequenceDataEditor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import sparshui.common.messages.events.DragEvent;

import com.trolltech.qt.core.QPointF;

import edu.uci.ics.jung.graph.util.Pair;

import Control.Types.BaseType;
import GUI.Editor.Editor;
import GUI.Editor.Commands.SequenceDataItemMove;
import GUI.Item.Editor.SequenceDataEditor.*;
import GUI.Scene.Editor.SequenceDataEditorScene;
import Sequencer.EventPointsSequence;
import Sequencer.SequenceEvalListenerInterface;
import Sequencer.SequenceEvent;
import Sequencer.SequenceEventListenerInterface;

public class SequenceDataEditor<EventType extends BaseType> extends Editor implements SequenceEvalListenerInterface, SequenceEventListenerInterface {

	private EventPointsSequence<EventType> sequence;
	private SequenceDataEditorScene<EventType> scene;
	
	public SequenceDataEditor(SequenceDataEditorScene<EventType> scene, EventPointsSequence<EventType> sequence) {
		super(scene);
		
		this.sequence = sequence;
		this.scene = scene;
		
		this.loadFromSequence(sequence);
	}
	
	private void loadFromSequence(EventPointsSequence<EventType> epseq) {
		Iterator<Entry<Long, EventType>> iterator =  epseq.getIterator();
		System.out.println("what???");
		while(iterator.hasNext()) {
			System.out.println("it ");
			Entry<Long, EventType> entry = iterator.next();
			scene.addNewEditorItem(entry.getKey(), entry.getValue());
		}
	}

	HashMap<Integer, Pair<Object>> dragStartPositionMap = new HashMap<Integer, Pair<Object>>();
	
	@Override 
	protected void handleDragEvent(DragEvent event) {
		if(event.getSource() != null && event.getSource() instanceof TouchableSequenceDataItem) {
			TouchableSequenceDataItem<EventType> item = (TouchableSequenceDataItem<EventType>) event.getSource();
			if(!event.isDrop()) {
				if(!dragStartPositionMap.containsKey(event.getTouchID())) {
					dragStartPositionMap.put(event.getTouchID(), item.getValue());
				} 
			} else {
				Pair<Object> pair;

				if((pair = dragStartPositionMap.get(event.getTouchID())) != null) {
					dragStartPositionMap.remove(event.getTouchID());

					this.executeCommand(new SequenceDataItemMove<EventType>(item , pair, item.getValue(), this.sequence));
				}
			}

			this.scene.updateEditorItem(item, event.getSceneLocation());		

		}

	}
	
	@Override
	public void dispatchEvalEvent(Long tick) {
		scene.setPlayCursor(tick);
	}

	@Override
	public void dispatchSequenceEvent(SequenceEvent se) {
		// currently ignore...
	}

}
