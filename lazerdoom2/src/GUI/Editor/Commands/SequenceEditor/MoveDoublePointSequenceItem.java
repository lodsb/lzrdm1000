package GUI.Editor.Commands.SequenceEditor;

import com.trolltech.qt.core.QPointF;

import edu.uci.ics.jung.graph.util.Pair;

import Control.Types.BaseType;
import Control.Types.DoubleType;
import GUI.Editor.BaseEditorCommand;
import GUI.Item.Editor.SequenceDataEditor.TouchableDoubleTypeSequenceDataItem;
import Sequencer.EventPointsSequence;

public class MoveDoublePointSequenceItem<T extends BaseType> extends BaseEditorCommand {
	
	private EventPointsSequence<T> sequence;
	private QPointF pos;
	private TouchableDoubleTypeSequenceDataItem item;
	
	public MoveDoublePointSequenceItem(EventPointsSequence<T> sequence, TouchableDoubleTypeSequenceDataItem item , QPointF pos) {
		this.sequence = sequence;
		this.pos = pos;
		this.item = item;	
	}
	
	@Override
	public boolean execute() {
		this.sequence.remove((Long)this.item.getOldTickValuePair().getFirst());
		this.item.setPosition(this.pos);
		Pair<Object> p = this.item.getTickValuePairFromPosition();
		this.sequence.insert((T) p.getSecond(), (Long) p.getFirst());
		
		return true;
	}

}
