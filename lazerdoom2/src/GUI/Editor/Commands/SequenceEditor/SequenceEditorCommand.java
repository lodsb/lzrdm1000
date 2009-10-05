package GUI.Editor.Commands.SequenceEditor;

import GUI.Editor.BaseEditorCommand;

public abstract class SequenceEditorCommand extends BaseEditorCommand {
	public SequenceEditorCommand() {
		this.prohibitSaveToSessionFile();
	}

}
