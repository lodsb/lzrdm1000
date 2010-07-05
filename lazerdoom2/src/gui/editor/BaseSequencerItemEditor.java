package gui.editor;

import gui.item.BaseSequencerItem;
import gui.item.editor.TouchableGraphicsView;
import gui.scene.editor.EditorScene;

import java.util.List;

import sparshui.common.messages.events.ExtendedGestureEvent;
import sparshui.common.messages.events.TouchEvent;


public abstract class BaseSequencerItemEditor extends Editor {

	public Signal0 sceneChanged = new Signal0();
	
	public abstract void setItem(BaseSequencerItem item);
	public abstract boolean allowViewpointChange();
	public abstract List<Integer> getAllowedGestures();
	public abstract Integer getGroupID();
	
	public void handleTouchEvent(TouchEvent event, int vSnap, int hSnap) {
		this.handleTouchEvent(event);
	}
	

	public void handleExtendedGestureEvent(ExtendedGestureEvent event, int vSnap, int hSnap) {
		this.handleExtendedGestureEvent(event);
	}
}
