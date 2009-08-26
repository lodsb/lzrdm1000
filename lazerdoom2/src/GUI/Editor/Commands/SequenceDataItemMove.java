package GUI.Editor.Commands;

import com.trolltech.qt.core.QPointF;

import edu.uci.ics.jung.graph.util.Pair;

import Control.Types.BaseType;
import GUI.Editor.BaseEditorCommand;
import GUI.Item.Editor.SequenceDataEditor.*;
import Sequencer.EventPointsSequence;

public class SequenceDataItemMove<T extends BaseType> extends BaseEditorCommand {

	TouchableSequenceDataItem<T> item = null;
	EventPointsSequence<T> sequence;
	Pair<Object> oldItemData;
	Pair<Object> newItemData;
	
	public SequenceDataItemMove(TouchableSequenceDataItem<T> item, Pair<Object> oldItemData,Pair<Object> newItemData , EventPointsSequence<T> sequence) {
		this.item = item;
		this.sequence = sequence;
		
		this.oldItemData = oldItemData;
		this.newItemData = newItemData;
	}
	
	@Override
	public boolean execute() {
		sequence.remove((Long) (oldItemData.getFirst()));
		sequence.insert((T) newItemData.getSecond(), (Long) (newItemData).getFirst());
		return true;
	}

}
