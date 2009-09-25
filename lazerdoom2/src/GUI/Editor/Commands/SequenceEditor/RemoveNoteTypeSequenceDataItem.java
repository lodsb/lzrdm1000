package GUI.Editor.Commands.SequenceEditor;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.gui.QGraphicsScene;

import edu.uci.ics.jung.graph.util.Pair;

import Control.Types.BaseType;
import GUI.Editor.BaseEditorCommand;
import GUI.Item.Editor.SequenceDataEditor.TouchableDoubleTypeSequenceDataItem;
import GUI.Item.Editor.SequenceDataEditor.TouchableNoteTypeSequenceDataItem;
import GUI.Item.Editor.SequenceDataEditor.TouchableSequenceDataItem;
import Sequencer.EventPointsSequence;

public class RemoveNoteTypeSequenceDataItem<T extends BaseType> extends BaseEditorCommand {

	private QGraphicsScene scene;
	private EventPointsSequence<T> sequence;
	private TouchableNoteTypeSequenceDataItem item;
	
	public RemoveNoteTypeSequenceDataItem(EventPointsSequence<T> sequence, TouchableNoteTypeSequenceDataItem item, QGraphicsScene scene) {
		this.scene = scene;
		this.sequence = sequence;
		this.item = item;
	}
	@Override
	public boolean execute() {
		TouchableNoteTypeSequenceDataItem noteOff = null;
		TouchableNoteTypeSequenceDataItem noteOn = null;
		if(!this.item.isNoteOff()) {
			noteOff = this.item.noteOff();
			noteOn = this.item;
		} else {
			noteOff = this.item;
			noteOn = this.item.noteOn();
		}

		Pair<Object> p = noteOn.getTickValuePairFromPosition();
		this.sequence.remove((Long)p.getFirst(), (T)p.getSecond());

		Pair<Object> p3 = noteOff.getTickValuePairFromPosition();
		this.sequence.remove((Long)p3.getFirst(), (T)p3.getSecond());

		noteOff.destroy();
		this.scene.removeItem(noteOn);
		this.scene.removeItem(noteOff);


		return true;
	}

}
