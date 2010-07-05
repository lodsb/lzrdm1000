package gui.editor.commands.sequenceeditor;

import sequencer.EventPointsSequence;

import com.trolltech.qt.gui.QGraphicsScene;

import control.types.BaseType;

import edu.uci.ics.jung.graph.util.Pair;
import gui.editor.BaseEditorCommand;
import gui.item.editor.sequencedataeditor.TouchableDoubleTypeSequenceDataItem;
import gui.item.editor.sequencedataeditor.TouchableSequenceDataItem;


public class RemoveSequenceDataItem<T extends BaseType> extends SequenceEditorCommand {

	private QGraphicsScene scene;
	private EventPointsSequence<T> sequence;
	private TouchableSequenceDataItem item;
	
	public RemoveSequenceDataItem(EventPointsSequence<T> sequence, TouchableSequenceDataItem item, QGraphicsScene scene) {
		super();
		this.scene = scene;
		this.sequence = sequence;
		this.item = item;
	}
	@Override
	public boolean execute() {
		
		Pair<Object> p = item.getTickValuePairFromPosition();
		sequence.remove((Long)p.getFirst(), (T)p.getSecond());
		scene.removeItem(item);
		
		return true;
	}

}
