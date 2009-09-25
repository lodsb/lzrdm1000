package GUI.Editor.Commands.SequenceEditor;

import com.trolltech.qt.core.QPointF;

import edu.uci.ics.jung.graph.util.Pair;

import Control.Types.BaseType;
import Control.Types.DoubleType;
import GUI.Editor.BaseEditorCommand;
import GUI.Item.Editor.SequenceDataEditor.TouchableDoubleTypeSequenceDataItem;
import GUI.Item.Editor.SequenceDataEditor.TouchableSequenceDataItem;
import Sequencer.EventPointsSequence;

public class MoveSequenceDataItem<T extends BaseType> extends BaseEditorCommand {
	
	private EventPointsSequence<T> sequence;
	private QPointF pos;
	private TouchableSequenceDataItem item;
	
	public MoveSequenceDataItem(EventPointsSequence<T> sequence, TouchableSequenceDataItem item , QPointF pos) {
		this.sequence = sequence;
		this.pos = pos;
		this.item = item;	
	}
	
	@Override
	public boolean execute() {
		Pair<Object> p = this.item.getOldTickValuePair();
		System.out.println("PAIT PAIR "+p);
		
		this.sequence.remove((Long)p.getFirst(), (T)p.getSecond());
		this.item.setPosition(this.pos);
		this.item.updateTickAndValueFromPosition(this.item.pos());
		Pair<Object> p2 = this.item.getTickValuePairFromPosition();
		this.sequence.insert((T) p2.getSecond(), (Long) p2.getFirst());
		
		return true;
	}

}
