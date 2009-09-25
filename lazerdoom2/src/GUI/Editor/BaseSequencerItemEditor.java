package GUI.Editor;

import java.util.List;

import sparshui.common.messages.events.ExtendedGestureEvent;
import sparshui.common.messages.events.TouchEvent;

import GUI.Item.BaseSequencerItem;
import GUI.Item.Editor.TouchableGraphicsView;
import GUI.Scene.Editor.EditorScene;

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
