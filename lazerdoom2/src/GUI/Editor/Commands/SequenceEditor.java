package GUI.Editor.Commands;

import java.util.LinkedList;
import java.util.List;

import GUI.Editor.BaseSequencerItemEditor;
import GUI.Item.BaseSequencerItem;
import GUI.Item.SequencePlayerItem;
import GUI.Scene.Editor.EditorScene;
import GUI.Scene.Editor.SequenceInitScene;
import GUI.Scene.Editor.SequencePlayerScene;
import SceneItems.Util;

public class SequenceEditor extends BaseSequencerItemEditor {
	private int id = Util.getGroupID();
	private SequenceInitScene initEditorScene = new SequenceInitScene(this);
	private EditorScene sequenceEditor = null;
	
	private LinkedList<Integer> allowedGestures = new LinkedList<Integer>();
	
	private String[] sequenceTypeArray = new String[] {
			"Pause",
			"EventPoints",
	};


	int currentInitSequenceTypeIndex = 0;
	
	public SequenceEditor() {
		super();
		this.setScene(initEditorScene);
		allowedGestures.add(sparshui.gestures.GestureType.TOUCH_GESTURE.ordinal());
		
		this.initEditorScene.nextPressed.connect(this, "nextPressed()");
		this.initEditorScene.prevPressed.connect(this, "prevPressed()");
		
		this.initEditorScene.enablePrevButton(false);
	
	}
	
	private void nextPressed() {
		if(currentQuantizationIndex < measureArray.length-1) {
			currentQuantizationIndex++;
			this.setPlayerQuantization();
			
			if(currentQuantizationIndex == measureArray.length-1) {
				this.editorScene.enableNextButton(false);
			} 
		}
		this.editorScene.enablePrevButton(true);
	}
	
	private void prevPressed() {
		if(currentQuantizationIndex > 0) {
			currentQuantizationIndex--;
			this.setPlayerQuantization();
			
			if(currentQuantizationIndex == 0) {
				this.editorScene.enablePrevButton(false);
			}
		}
		this.editorScene.enableNextButton(true);
	}
	

	@Override
	public boolean allowViewpointChange() {
		return false;
	}

	@Override
	public List<Integer> getAllowedGestures() {
		return this.allowedGestures;
	}

	@Override
	public Integer getGroupID() {
		return this.id;
	}

	SequencePlayerItem player = null;
	@Override
	public void setItem(BaseSequencerItem item) {
		if(item instanceof SequencePlayerItem) {
			this.player = (SequencePlayerItem) item;
		}

	}
	
	@Override
	public void handleTouchEvent(TouchEvent e) {
		this.setShowTouchEvents(true);
		QGraphicsItemInterface item;
		if((item = editorScene.itemAt(e.getSceneLocation())) instanceof TouchableGraphicsItem) {
			((TouchableGraphicsItem) item).processEvent(e);
		}
	}

}
