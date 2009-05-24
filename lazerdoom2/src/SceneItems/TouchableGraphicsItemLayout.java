package SceneItems;

import java.util.LinkedList;

import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSizeF;
import com.trolltech.qt.gui.QGraphicsItem;
import com.trolltech.qt.gui.QGraphicsItemGroup;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;

public abstract class TouchableGraphicsItemLayout extends QGraphicsItem implements Layout {
	private QRectF size = new QRectF(0,0,0,0);
	
	@Override
	public QRectF boundingRect() {
		return size;
	}

	@Override
	public void paint(QPainter painter, QStyleOptionGraphicsItem option,
			QWidget widget) {
		// TODO Auto-generated method stub
	}
	
	public abstract QSizeF getSize();
}
