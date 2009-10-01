package GUI.Editor.Commands;

import lazerdoom.LzrDmObjectInterface;

import com.trolltech.qt.gui.QGraphicsScene;

import GUI.Editor.BaseEditorCommand;
import GUI.Item.EditorCursor;
import GUI.Scene.Editor.EditorScene;

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
