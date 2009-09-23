package GUI.Editor.Commands.SequenceEditor;

import com.trolltech.qt.gui.QGraphicsScene;

import edu.uci.ics.jung.graph.util.Pair;

import Control.Types.BaseType;
import GUI.Editor.BaseEditorCommand;
import GUI.Item.Editor.SequenceDataEditor.TouchableDoubleTypeSequenceDataItem;
import Sequencer.EventPointsSequence;

public class RemoveDoublePointSequenceItem<T extends BaseType> extends BaseEditorCommand {

	private QGraphicsScene scene;
	private EventPointsSequence<T> sequence;
	private TouchableDoubleTypeSequenceDataItem item;
	
	public RemoveDoublePointSequenceItem(EventPointsSequence<T> sequence, TouchableDoubleTypeSequenceDataItem item, QGraphicsScene scene) {
		this.scene = scene;
		this.sequence = sequence;
		this.item = item;
	}
	@Override
	public boolean execute() {
		
		Pair<Object> p = item.getTickValuePairFromPosition();
		sequence.remove((Long)p.getFirst());
		scene.removeItem(item);
		
		return true;
	}

}
