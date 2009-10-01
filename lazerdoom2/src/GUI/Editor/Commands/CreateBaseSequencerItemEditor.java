package GUI.Editor.Commands;

import lazerdoom.LzrDmObjectInterface;
import GUI.Editor.BaseEditorCommand;
import GUI.Editor.BaseSequencerItemEditor;
import GUI.Editor.SequenceEditor;
import GUI.Editor.SequencePlayerEditor;
import GUI.Editor.SynthesizerEditor;
import GUI.Item.BaseSequencerItem;
import GUI.Item.SequenceItem;
import GUI.Item.SequencePlayerItem;
import GUI.Item.SynthesizerItem;
import GUI.View.SequencerView;

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
				ret = true;
			} else if(item instanceof SequencePlayerItem) {
				editor = new SequencePlayerEditor();
				editor.setItem((BaseSequencerItem) item);
				SequencerView.getInstance().getItemEditorController().registerEditor((BaseSequencerItem) item, editor);
				ret = true;
			} else if(item instanceof SequenceItem) {
				editor = new SequenceEditor();
				editor.setItem((BaseSequencerItem) item);
				SequencerView.getInstance().getItemEditorController().registerEditor((BaseSequencerItem) item, editor);
				ret = true;
			}
			
			
		return ret;
	}

}
