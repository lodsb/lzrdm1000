package GUI.Editor;

import java.util.List;

import GUI.Item.BaseSequencerItem;
import GUI.Item.Editor.TouchableGraphicsView;
import GUI.Scene.Editor.EditorScene;

public abstract class BaseSequencerItemEditor extends Editor {

	public abstract void setItem(BaseSequencerItem item);
	public abstract boolean allowViewpointChange();
	public abstract List<Integer> getAllowedGestures();
	public abstract Integer getGroupID();

}
