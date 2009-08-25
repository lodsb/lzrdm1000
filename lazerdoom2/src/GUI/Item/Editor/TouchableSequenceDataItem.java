package GUI.Item.Editor;

import com.trolltech.qt.core.QPointF;

import edu.uci.ics.jung.graph.util.Pair;
import Control.Types.BaseType;

public abstract class TouchableSequenceDataItem<T extends BaseType> extends TouchableEditorItem {
	
	public abstract void setValue(long tick, T value);
	public abstract void setValueFromPosition(QPointF pos);
	
	// 1st value is tick, 2nd value is T
	public abstract Pair<Object> getValue();
}
