package gui.editor.commands;

import session.SessionHandler;
import gui.editor.BaseEditorCommand;
import gui.editor.BaseSequencerItemEditor;
import gui.editor.SequenceEditor;
import gui.editor.SequencePlayerEditor;
import gui.editor.SynthesizerEditor;
import gui.item.BaseSequencerItem;
import gui.item.SequenceItem;
import gui.item.SequencePlayerItem;
import gui.item.SynthesizerItem;
import gui.view.SequencerView;
import lazerdoom.LzrDmObjectInterface;

public class CreateBaseSequencerItemEditor extends BaseEditorCommand {

	LzrDmObjectInterface item;
	
	public CreateBaseSequencerItemEditor(BaseSequencerItem item) {
		this.item = item;
	}

	@Override
	public boolean execute() {
		boolean ret = false;
			BaseSequencerItemEditor editor;
			
			if(item instanceof SynthesizerItem) {
				editor = new SynthesizerEditor();
				editor.setItem((BaseSequencerItem) item);
				SequencerView.getInstance().getItemEditorController().registerEditor((BaseSequencerItem) item, editor);
				SessionHandler.getInstance().registerObject(editor);
				ret = true;
			} else if(item instanceof SequencePlayerItem) {
				editor = new SequencePlayerEditor();
				editor.setItem((BaseSequencerItem) item);
				SequencerView.getInstance().getItemEditorController().registerEditor((BaseSequencerItem) item, editor);
				SessionHandler.getInstance().registerObject(editor);
				ret = true;
			} else if(item instanceof SequenceItem) {
				editor = new SequenceEditor();
				editor.setItem((BaseSequencerItem) item);
				SequencerView.getInstance().getItemEditorController().registerEditor((BaseSequencerItem) item, editor);
				SessionHandler.getInstance().registerObject(editor);
				ret = true;
			}
			
			if(ret) {
				SessionHandler.getInstance().registerObject(item);
			}
			
		return ret;
	}

}
