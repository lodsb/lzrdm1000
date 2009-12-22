package gui.editor.commands.sequenceeditor;

import sequencer.EventPointsSequence;

import com.trolltech.qt.core.QPointF;

import control.types.BaseType;
import control.types.DoubleType;

import edu.uci.ics.jung.graph.util.Pair;
import gui.editor.BaseEditorCommand;
import gui.item.editor.sequencedataeditor.TouchableDoubleTypeSequenceDataItem;
import gui.item.editor.sequencedataeditor.TouchableSequenceDataItem;


public class MoveSequenceDataItem<T extends BaseType> extends SequenceEditorCommand {
	
	private EventPointsSequence<T> sequence;
	private QPointF pos;
	private TouchableSequenceDataItem item;
	
	public MoveSequenceDataItem(EventPointsSequence<T> sequence, TouchableSequenceDataItem item , QPointF pos) {
		super();
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
