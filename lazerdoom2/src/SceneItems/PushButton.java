package SceneItems;

import java.util.LinkedList;
import java.util.List;

import sparshui.common.Event;
import sparshui.common.TouchState;
import sparshui.common.messages.events.TouchEvent;
import sparshui.gestures.GestureType;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRect;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSizeF;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QFontMetrics;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;

public class PushButton extends TouchableGraphicsItem {

	private LinkedList<Integer> allowedGestures = new LinkedList<Integer>();
	private String labelText;
	private QRectF size = null;
	private QBrush normalBrush = new QBrush(QColor.lightGray);
	private QBrush pressedBrush= new QBrush(QColor.gray);
	private QFont font = new QFont("Helvetica [Cronyx]", 12);
	
	private int padding = 5;
	
	private boolean pressed = false;
	
	public Signal1<TouchableGraphicsItem> buttonPressed = new Signal1<TouchableGraphicsItem>();
	
	public PushButton(String labelText) {
		allowedGestures.add(GestureType.TOUCH_GESTURE.ordinal());
		this.labelText = labelText;
		
		this.updateSize();

	}
	
	private void updateSize() {
		QFontMetrics metrics = new QFontMetrics(font); 
		int textWidth = metrics.width(labelText);
		int labelWidth = 2*padding+textWidth;
		int textHeight = metrics.height();
		int labelHeight= 2*padding+textHeight;
		
		if(this.size == null || ((size.width() < labelWidth) || (size.height() < labelHeight))) {
			this.size = new QRectF(0,0,labelWidth, labelHeight);
		}
		
		this.update();
	}
	
	@Override
	public QRectF boundingRect() {
		return size;
	}

	@Override
	public void paint(QPainter painter, QStyleOptionGraphicsItem option,
			QWidget widget) {
		
		if(pressed) {
			painter.setBrush(pressedBrush);
		} else {
			painter.setBrush(normalBrush);
		}
		
		painter.drawRect(size);
		painter.drawText(size, 0x84, labelText);
	}

	@Override
	public void setSize(QSizeF size) {
		this.size.setSize(size);
		this.update();
	}

	public List<Integer> getAllowedGestures() {
		return allowedGestures;
	}
	
	@Override
	public boolean processEvent(Event event) {		
		if(event instanceof TouchEvent) {
			TouchEvent e = (TouchEvent) event;
			
			QPointF coordinate = mapFromScene(lazerdoom.View.getInstance().convertScreenPos(e.getX(),e.getY()));
			if(this.shape().contains(coordinate)) {
				this.pressed = true;
				this.update();
				
				if(e.getState() == TouchState.DEATH) {
					this.buttonPressed.emit(this);
				}
				
			} else {
				this.pressed = false;
				update();
			}
		}
		return false;
	}

	@Override
	public QSizeF getPreferedSize() {
		return size.size(); 
	}
}
