package GUI.Editor.Commands;

import java.util.Random;

import lazerdoom.LzrDmObjectInterface;

import GUI.Editor.BaseEditorCommand;
import GUI.Editor.SequencerEditor;
import GUI.Item.EditorCursor;
import GUI.Scene.Editor.EditorScene;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.gui.QGraphicsScene;

public class CreateEditorCommand extends BaseEditorCommand {
	private QPointF sceneLocation;
	private LzrDmObjectInterface scene;
	private LzrDmObjectInterface editor;

	public CreateEditorCommand(QPointF sceneLocation, EditorScene scene, SequencerEditor editor) {
		this.scene = scene;
		this.sceneLocation = sceneLocation;
		this.editor = editor;
	}

	@Override
	public boolean execute() {
		EditorCursor cursor = new EditorCursor();
		System.out.println("new editor cursor: "+cursor);
		((EditorScene)this.scene).addItem(cursor);
		cursor.setPos(this.sceneLocation);
		cursor.openEditor.connect(this.editor, "openEditor(EditorCursor, BaseSequencerItem)");
		cursor.closeEditor.connect(this.editor, "closeEditor(EditorCursor)");
		
		return true;
	}

}
