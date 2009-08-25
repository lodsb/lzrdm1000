package GUI.Editor.Commands;

import com.trolltech.qt.core.QPointF;

import GUI.Editor.BaseEditorCommand;
import GUI.Item.Editor.TouchableSequenceDataItem;

public class SequenceDataItemMove extends BaseEditorCommand {

	TouchableSequenceDataItem item = null;
	QPointF oldPosition = null;
	
	public SequenceDataItemMove(TouchableSequenceDataItem item, QPointF oldPosition) {
		this.item = item;
		this.oldPosition = oldPosition;
	}
	
	@Override
	public boolean execute() {
		// TODO Auto-generated method stub
		return false;
	}

}
