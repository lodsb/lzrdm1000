package GUI.Editor;

import com.trolltech.qt.gui.QUndoCommand;

public abstract class BaseEditorCommand extends QUndoCommand {
	public abstract boolean execute();
}
