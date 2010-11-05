package gui.editor;

import gui.editor.SequenceEditor;
import gui.item.*;
import gui.item.editor.TouchableEditor;

import java.util.*;

import com.trolltech.qt.core.QObject;

public class BaseSequencerItemEditorController extends QObject {
	private HashMap<BaseSequencerItem, BaseSequencerItemEditor> sequencerItemEditors = new HashMap<BaseSequencerItem, BaseSequencerItemEditor>();
	
	public Signal1<Editor> editorDestroyed = new Signal1<Editor>();
	
	public boolean editorExists(BaseSequencerItem item) {
		return this.sequencerItemEditors.containsKey(item);
	}
	
	public void registerEditor(BaseSequencerItem item, BaseSequencerItemEditor editor) {
		this.sequencerItemEditors.put(item, editor);
	}
	
	public BaseSequencerItemEditor getEditor(BaseSequencerItem item) {
		BaseSequencerItemEditor editor = null;
		
		if((editor = sequencerItemEditors.get(item)) == null) {
			if(item instanceof SynthesizerItem) {
				editor = new SynthesizerEditor();
				editor.setItem(item);
			} 
			
			if(item instanceof SequencePlayerItem) {
				editor = new SequencePlayerEditor();
				editor.setItem(item);
			}
			
			if(item instanceof SequenceItem) {
				editor = new SequenceEditor();
				editor.setItem(item);
			}
			
			sequencerItemEditors.put(item, editor);
		}
		
		return editor;
	} 
	
	public void destroyEditor(BaseSequencerItem item) {
		this.editorDestroyed.emit(null);
	}
}
