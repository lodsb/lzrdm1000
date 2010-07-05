package gui.editor.commands.sequenceeditor;

import gui.editor.BaseEditorCommand;

public abstract class SequenceEditorCommand extends BaseEditorCommand {
	public SequenceEditorCommand() {
		this.prohibitSaveToSessionFile();
	}

}
