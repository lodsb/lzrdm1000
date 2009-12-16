package sceneitems;

import java.util.List;

import sparshui.common.Event;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsEllipseItem;
import com.trolltech.qt.gui.QGraphicsTextItem;

public class TouchPointCursor extends QGraphicsEllipseItem {
	QTimer switchOffTimer = new QTimer();
	QGraphicsTextItem text = new QGraphicsTextItem();
	
	public TouchPointCursor() {
		super(new QRectF(0,0,20,20));
		this.setBrush(new QBrush(new QColor((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255))));
		text.setParentItem(this);
		
		text.setPos(0,30);
	}
	
	public void setHTMLText(String text) {
		this.text.setHtml(text);
	}
	
	
}
