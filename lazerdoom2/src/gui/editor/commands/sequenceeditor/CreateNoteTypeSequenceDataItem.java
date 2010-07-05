package gui.editor.commands.sequenceeditor;

import sequencer.EventPointsSequence;

import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.core.QPointF;

import control.types.BaseType;
import control.types.DoubleType;
import control.types.NoteType;

import edu.uci.ics.jung.graph.util.Pair;
import gui.editor.BaseEditorCommand;
import gui.editor.Editor;
import gui.item.editor.sequencedataeditor.TouchableDoubleTypeSequenceDataItem;
import gui.item.editor.sequencedataeditor.TouchableNoteTypeSequenceDataItem;

public class CreateNoteTypeSequenceDataItem<T extends NoteType> extends SequenceEditorCommand {

	private EventPointsSequence<T> sequence;
	private QPointF pos;
	private QGraphicsScene scene;
	private Editor editor;
	private Object object;
	private String slot;
	private int vSnap;
	
	public CreateNoteTypeSequenceDataItem(EventPointsSequence<T> sequence, QPointF pos, Editor editor, int vSnap, QGraphicsScene scene, Object object, String slot) {
		super();
		this.sequence = sequence;
		this.pos = pos;
		this.scene = scene;
		this.editor = editor;
		this.object = object;
		this.slot = slot;
		this.vSnap = vSnap;
	}
	@Override
	public boolean execute() {
		TouchableNoteTypeSequenceDataItem item = new TouchableNoteTypeSequenceDataItem(this.editor, null); // note-on
		this.scene.addItem(item);
		item.setPosition(pos);
		item.updateTickAndValueFromPosition(pos);
		Pair<Object> p = item.getTickValuePairFromPosition();
		
		
		(this.sequence).insert((T)p.getSecond(), (Long)p.getFirst());
		item.dragged.connect(this.object, this.slot);
		
		TouchableNoteTypeSequenceDataItem noteOffItem = new TouchableNoteTypeSequenceDataItem(this.editor, item); // note-off
		this.scene.addItem(noteOffItem);
		QPointF noteOffPos = new QPointF(pos.x()+vSnap, pos.y());
		noteOffItem.setPosition(noteOffPos);
		noteOffItem.updateTickAndValueFromPosition(noteOffPos);
		Pair<Object> p2 = noteOffItem.getTickValuePairFromPosition();
		
		
		(this.sequence).insert((T)p2.getSecond(), (Long)p2.getFirst());
		noteOffItem.dragged.connect(this.object, this.slot);
		
		((T)p.getSecond()).setNoteOff((T)p2.getSecond());
		((T)p2.getSecond()).setIsNoteOff();
		((T)p.getSecond()).setLength((Long)p2.getFirst()-(Long)p.getFirst());


		
		return true;
	}

}
