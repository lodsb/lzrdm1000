package GUI.Editor.Commands.SequenceEditor;

import com.trolltech.qt.core.QPointF;

import edu.uci.ics.jung.graph.util.Pair;

import Control.Types.BaseType;
import Control.Types.DoubleType;
import GUI.Editor.BaseEditorCommand;
import GUI.Item.Editor.SequenceDataEditor.TouchableDoubleTypeSequenceDataItem;
import GUI.Item.Editor.SequenceDataEditor.TouchableNoteTypeSequenceDataItem;
import GUI.Item.Editor.SequenceDataEditor.TouchableSequenceDataItem;
import Sequencer.EventPointsSequence;

public class MoveNoteTypeSequenceDataItem<T extends BaseType> extends BaseEditorCommand {
	
	private EventPointsSequence<T> sequence;
	private QPointF pos;
	private TouchableNoteTypeSequenceDataItem item;
	
	public MoveNoteTypeSequenceDataItem(EventPointsSequence<T> sequence, TouchableNoteTypeSequenceDataItem item , QPointF pos) {
		this.sequence = sequence;
		this.pos = pos;
		this.item = item;	
	}
	
	@Override
	public boolean execute() {
		if(!this.item.isNoteOff()) {
			TouchableNoteTypeSequenceDataItem noteOff = this.item.noteOff();
			double noteOffOffset = noteOff.pos().x() - this.item.pos().x();
			
			Pair<Object> p = this.item.getOldTickValuePair();
			this.sequence.remove((Long)p.getFirst(), (T)p.getSecond());
			this.item.setPosition(this.pos);
			this.item.updateTickAndValueFromPosition(this.item.pos());
			Pair<Object> p2 = this.item.getTickValuePairFromPosition();
			this.sequence.insert((T) p2.getSecond(), (Long) p2.getFirst());
			
			Pair<Object> p3 = noteOff.getTickValuePairFromPosition();
			this.sequence.remove((Long)p3.getFirst(), (T)p3.getSecond());
			noteOff.setPosition(new QPointF(this.pos.x()+noteOffOffset, this.pos.y()));
			noteOff.updateTickAndValueFromPosition(noteOff.pos());
			Pair<Object> p4 = noteOff.getTickValuePairFromPosition();
			this.sequence.insert((T) p4.getSecond(), (Long) p4.getFirst());

		} else {
			Pair<Object> p = this.item.getOldTickValuePair();
			this.sequence.remove((Long)p.getFirst(), (T)p.getSecond());
			this.item.setPosition(this.pos);
			this.item.updateTickAndValueFromPosition(this.item.pos());
			Pair<Object> p2 = this.item.getTickValuePairFromPosition();
			this.sequence.insert((T) p2.getSecond(), (Long) p2.getFirst());
		}
		
		return true;
	}

}
