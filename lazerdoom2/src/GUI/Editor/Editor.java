package GUI.Editor;

import com.trolltech.qt.gui.QUndoStack;

public class Editor {
	private QUndoStack undoStack = new QUndoStack();
	
	public void undo() {
		if(undoStack.canUndo()) {
			undoStack.undo();
		}
	}
	
	public void redo() {
		if(undoStack.canRedo()) {
			undoStack.redo();
		}
	}
	
	public boolean executeCommand(BaseEditorCommand command) {
		boolean ret = false;
		if(command.execute()) {
			undoStack.push(command);
		}
		return ret;
	}
}
