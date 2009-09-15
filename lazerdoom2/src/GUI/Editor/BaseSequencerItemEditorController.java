package GUI.Editor;
import GUI.Item.*;
import GUI.Item.Editor.TouchableEditor;

import java.util.*;

import com.trolltech.qt.core.QObject;

public class BaseSequencerItemEditorController extends QObject {
	private HashMap<BaseSequencerItem, BaseSequencerItemEditor> sequencerItemEditors = new HashMap<BaseSequencerItem, BaseSequencerItemEditor>();
	
	public Signal1<Editor> editorDestroyed = new Signal1<Editor>();
	
	public BaseSequencerItemEditor getEditor(BaseSequencerItem item) {
		BaseSequencerItemEditor editor = null;
		
		if((editor = sequencerItemEditors.get(item)) == null) {
			if(item instanceof SynthesizerItem) {
				editor = new SynthesizerEditor();
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
