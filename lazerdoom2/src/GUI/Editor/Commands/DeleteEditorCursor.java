package GUI.Editor.Commands;

import com.trolltech.qt.gui.QGraphicsScene;

import GUI.Editor.BaseEditorCommand;
import GUI.Item.EditorCursor;

public class DeleteEditorCursor extends BaseEditorCommand {
	QGraphicsScene scene;
	EditorCursor cursor;

	public DeleteEditorCursor(EditorCursor cursor, QGraphicsScene scene) {
		this.scene = scene;
		this.cursor = cursor;
	} 
	
	@Override
	public boolean execute() {
		cursor.destroyEditor();
		this.scene.removeItem(cursor);
		
		return true;
	}

}
