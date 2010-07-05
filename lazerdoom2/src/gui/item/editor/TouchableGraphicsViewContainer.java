package gui.item.editor;

import gui.multitouch.TouchItemInterface;
import gui.multitouch.TouchableGraphicsItemContainer;

import java.util.LinkedList;
import java.util.List;

import sceneitems.Util;
import sparshui.common.Event;

import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSizeF;
import com.trolltech.qt.core.Qt.SizeHint;
import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QGraphicsLayoutItem;
import com.trolltech.qt.gui.QGraphicsProxyWidget;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;


public class TouchableGraphicsViewContainer extends QGraphicsLayoutItem {
	private TouchableGraphicsView view;
	private QSizeF fixedSize = null;
	
	TouchableProxy w;
	
	public TouchableGraphicsViewContainer(TouchableGraphicsView view, QSizeF fixedSize) {
		this.fixedSize = fixedSize;
		this.view = view;
		w = new TouchableProxy();
		w.setWidget(view);
		this.setGraphicsItem(w);
		System.out.println("container "+this);
		
	}

	public void setGeometry(QRectF size) {
		this.w.setGeometry(size);
	}

	
	@Override
	public QSizeF sizeHint(SizeHint hint, QSizeF arg1) {
		return fixedSize;
	}
	
}
