package gui.multitouch;

import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSizeF;
import com.trolltech.qt.core.Qt.SizeHint;
import com.trolltech.qt.gui.QGraphicsItem;
import com.trolltech.qt.gui.QGraphicsLayoutItem;

public class TouchableGraphicsItemContainer extends QGraphicsLayoutItem {

	private TouchableGraphicsItem item;
	
	public void setItem(TouchableGraphicsItem item) {
		this.item = item;
		this.setGraphicsItem(item);
	}
	
	public void setGeometry(QRectF size) {
		this.item.setGeometry(size);
	}
	
	@Override
	public QSizeF sizeHint(SizeHint hint, QSizeF arg1) {
		QSizeF size;
		
		if(hint == SizeHint.MinimumSize || hint == SizeHint.PreferredSize) {
			size = item.getPreferedSize();
			if(size == null) {
				size = new QSizeF(item.boundingRect().width(), item.boundingRect().height());
			}
		} else {
			size = item.getMaximumSize();
			if(size == null) {
				size = new QSizeF(item.boundingRect().width(), item.boundingRect().height());
			}			
		}
		return size;
	}

}
