package GUI.Item.Editor;

import java.util.List;

import sparshui.common.Event;
import GUI.Multitouch.TouchItemInterface;

import com.trolltech.qt.gui.QGraphicsItemGroup;

public class TouchableItemGroupItem extends QGraphicsItemGroup implements
		TouchItemInterface {

	@Override
	public List<Integer> getAllowedGestures() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getGroupID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean processEvent(Event event) {
		// TODO Auto-generated method stub
		return false;
	}

}
