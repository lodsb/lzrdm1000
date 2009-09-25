package GUI.Editor.Commands.SequenceEditor;

import com.trolltech.qt.gui.QGraphicsScene;

import edu.uci.ics.jung.graph.util.Pair;

import Control.Types.BaseType;
import GUI.Editor.BaseEditorCommand;
import GUI.Item.Editor.SequenceDataEditor.TouchableDoubleTypeSequenceDataItem;
import GUI.Item.Editor.SequenceDataEditor.TouchableSequenceDataItem;
import Sequencer.EventPointsSequence;

public class RemoveSequenceDataItem<T extends BaseType> extends BaseEditorCommand {

	private QGraphicsScene scene;
	private EventPointsSequence<T> sequence;
	private TouchableSequenceDataItem item;
	
	public RemoveSequenceDataItem(EventPointsSequence<T> sequence, TouchableSequenceDataItem item, QGraphicsScene scene) {
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
