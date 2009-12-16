package gui.item.Editor;

import gui.multitouch.TouchableGraphicsItem;

import java.util.LinkedList;
import java.util.List;

import sparshui.common.Event;
import sparshui.common.TouchState;
import sparshui.common.messages.events.DragEvent;
import sparshui.common.messages.events.TouchEvent;
import sparshui.gestures.GestureType;


import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRect;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSizeF;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;

public class Slider extends TouchableGraphicsItem {
	private QRectF size = new QRectF(0,0,40,100);
	private QRectF preferedSize = size;
	
	public Signal2<TouchableGraphicsItem, Double> valueChanged = new Signal2<TouchableGraphicsItem, Double>();

	private LinkedList<Integer> allowedGestures = new LinkedList<Integer>();
	
	private double currentValue = 0.5;
	private boolean editing = false;
	private QPointF editingStartPoint;
	private QRect currentValueRect;
	
	public Slider() {
		allowedGestures.add(GestureType.TOUCH_GESTURE.ordinal());
		this.updateValueRect();
	}
	
	@Override
	public QRectF boundingRect() {
		return size;
	}

	@Override
	public void paint(QPainter painter, QStyleOptionGraphicsItem option,
			QWidget widget) {
		painter.setBrush(QColor.red);
		painter.drawRect(currentValueRect);
		painter.setBrush(QBrush.NoBrush);
		painter.drawRect(size);
	}

	@Override
	public List<Integer> getAllowedGestures() {
		return allowedGestures;
	}

	private void updateValueRect() {
		System.out.println(currentValue);
		double lowerY = size.bottom();
		double height = size.height();
		double y = currentValue*height;
		currentValueRect = new QRect((int)size.left(),(int)size.bottom(),(int)size.width(),(int)-y);
		this.prepareGeometryChange();
		this.update();
	}
	
	@Override
	public boolean processEvent(Event event) {		
		if(event instanceof TouchEvent) {
			TouchEvent e = (TouchEvent) event;
			
			if(e.getState() == TouchState.DEATH) {
				editing = false;
			}
			
			QPointF coordinate = mapFromScene(e.getSceneLocation());
			if(this.shape().contains(coordinate)) {
				if(e.getState() == TouchState.BIRTH) {
					editing = true;
					editingStartPoint = coordinate;
				}
				
				if(editing) {
					double height = size.height();
					double y = height-coordinate.y();
					currentValue = y/height;
					updateValueRect();
					this.valueChanged.emit(this, currentValue);
				}
			}
		}
		return false;
	}

	@Override
	public void setGeometry(QRectF size) {
		this.size = size;
		this.updateValueRect();
	}

	@Override
	public QSizeF getPreferedSize() {
		return preferedSize.size();
	}

	@Override
	public QSizeF getMaximumSize() {
		return new QSizeF(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}


}
