package GUI.Scene.Editor;

import Control.Types.BaseType;
import GUI.Item.Editor.TouchableEditorItem;

import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.gui.QGraphicsScene;

public abstract class EditorScene<T extends BaseType> extends QGraphicsScene {
	
	public abstract QRectF getContentsRect();
	public abstract TouchableEditorItem addNewEditorItem(long tick, T value);
	public abstract void updateEditorItem(TouchableEditorItem item, T value);
}
