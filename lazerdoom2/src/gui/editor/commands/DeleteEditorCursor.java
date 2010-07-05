package gui.editor.commands;

import gui.editor.BaseEditorCommand;
import gui.item.EditorCursor;
import gui.scene.editor.EditorScene;
import lazerdoom.LzrDmObjectInterface;

import com.trolltech.qt.gui.QGraphicsScene;


public class DeleteEditorCursor extends BaseEditorCommand {
	LzrDmObjectInterface scene;
	LzrDmObjectInterface cursor;

	public DeleteEditorCursor(EditorCursor cursor, EditorScene scene) {
		this.scene = scene;
		this.cursor = cursor;
	} 
	
	@Override
	public boolean execute() {
		((EditorCursor) cursor).destroyEditor();
		((EditorScene) this.scene).removeItem((EditorCursor)cursor);
		
		return true;
	}

}
