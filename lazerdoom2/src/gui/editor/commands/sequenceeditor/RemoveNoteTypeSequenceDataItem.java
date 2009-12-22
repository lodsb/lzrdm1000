package gui.editor.commands.sequenceeditor;

import sequencer.EventPointsSequence;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.gui.QGraphicsScene;

import control.types.BaseType;

import edu.uci.ics.jung.graph.util.Pair;
import gui.editor.BaseEditorCommand;
import gui.item.editor.sequencedataeditor.TouchableDoubleTypeSequenceDataItem;
import gui.item.editor.sequencedataeditor.TouchableNoteTypeSequenceDataItem;
import gui.item.editor.sequencedataeditor.TouchableSequenceDataItem;


public class RemoveNoteTypeSequenceDataItem<T extends BaseType> extends SequenceEditorCommand {

	private QGraphicsScene scene;
	private EventPointsSequence<T> sequence;
	private TouchableNoteTypeSequenceDataItem item;
	
	public RemoveNoteTypeSequenceDataItem(EventPointsSequence<T> sequence, TouchableNoteTypeSequenceDataItem item, QGraphicsScene scene) {
		super();
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
