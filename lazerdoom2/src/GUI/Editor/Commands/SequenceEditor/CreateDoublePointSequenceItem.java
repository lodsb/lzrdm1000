package GUI.Editor.Commands.SequenceEditor;

import Control.Types.BaseType;
import Control.Types.DoubleType;
import GUI.Editor.BaseEditorCommand;
import GUI.Editor.Editor;
import GUI.Item.Editor.SequenceDataEditor.TouchableDoubleTypeSequenceDataItem;
import Sequencer.EventPointsSequence;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.core.QPointF;

import edu.uci.ics.jung.graph.util.Pair;

public class CreateDoublePointSequenceItem<T extends BaseType> extends BaseEditorCommand {

	private EventPointsSequence<T> sequence;
	private QPointF pos;
	private QGraphicsScene scene;
	private Editor editor;
	private Object object;
	private String slot;
	
	public CreateDoublePointSequenceItem(EventPointsSequence<T> sequence, QPointF pos, Editor editor, QGraphicsScene scene, Object object, String slot) {
		this.sequence = sequence;
		this.pos = pos;
		this.scene = scene;
		this.editor = editor;
		this.object = object;
		this.slot = slot;
	}
	@Override
	public boolean execute() {
		TouchableDoubleTypeSequenceDataItem item = new TouchableDoubleTypeSequenceDataItem(this.editor);
		this.scene.addItem(item);
		item.setPosition(pos);
		Pair<Object> p = item.getTickValuePairFromPosition();
		
		(this.sequence).insert((T)p.getSecond(), (Long)p.getFirst());
		item.dragged.connect(this.object, this.slot);

		
		return true;
	}

}
