package GUI.Item.Editor.SequenceDataEditor;

import java.util.Map.Entry;

import com.trolltech.qt.core.QPointF;

import edu.uci.ics.jung.graph.util.Pair;
import Control.Types.BaseType;
import GUI.Editor.Editor;
import GUI.Item.Editor.TouchableEditorItem;

public abstract class TouchableSequenceDataItem<T extends BaseType> extends TouchableEditorItem {
	
	public TouchableSequenceDataItem(Editor editor) {
		super(editor);
		// TODO Auto-generated constructor stub
	}
	//public abstract void setValue(long tick, T value);
	//public abstract void setValueFromPosition(QPointF pos);
	
	public abstract void setPosition(QPointF pos);
	// 1st value is tick, 2nd value is T
	public abstract Pair<Object> getTickValuePairFromPosition();
	
	// 1st value is tick, 2nd value is T
	//public abstract Pair<Object> getValue();
}
