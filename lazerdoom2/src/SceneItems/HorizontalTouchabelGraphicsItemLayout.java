package SceneItems;

import java.util.LinkedList;

import com.trolltech.qt.core.QSizeF;

public class HorizontalTouchabelGraphicsItemLayout extends
		TouchableGraphicsItemLayout {

	private QSizeF size = new QSizeF(0,0);
	
	private LinkedList<TouchableGraphicsItem> items = new LinkedList<TouchableGraphicsItem>();
	
	@Override
	public void addTouchableItem(TouchableGraphicsItem it) {
		items.addLast(it);
		it.setParentItem(this);
		
		//layout primitively
		double maxHeight = 0.0;
		double currentX = 0.0;
		
		for(TouchableGraphicsItem item: items) {
			QSizeF itemSize = item.getPreferedSize();
			if(itemSize.height() > maxHeight) {
				maxHeight = itemSize.height();
			}
			
			item.setPos(currentX, 0);
			currentX = currentX + itemSize.width();
		}
		
		for(TouchableGraphicsItem item: items) {
			QSizeF itemSize = item.getPreferedSize();
			if(itemSize.height() < maxHeight) {
				item.setSize(new QSizeF(maxHeight, itemSize.width()));
			}
		}

		
	}

	@Override
	public QSizeF getSize() {
		// TODO Auto-generated method stub
		return size;
	}

}
