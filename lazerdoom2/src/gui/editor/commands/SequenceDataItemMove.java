package gui.editor.commands;

import sequencer.EventPointsSequence;

import com.trolltech.qt.core.QPointF;

import control.types.BaseType;

import edu.uci.ics.jung.graph.util.Pair;
import gui.editor.BaseEditorCommand;
import gui.item.editor.sequencedataeditor.*;


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
		//FIXME
		System.err.println("sequenceitemmove was frigged, fixme!");
		//FIXME!!
		//sequence.remove((Long) (oldItemData.getFirst()));
		//sequence.insert((T) newItemData.getSecond(), (Long) (newItemData).getFirst());
		return true;
	}

}
