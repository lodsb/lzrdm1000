package GUI.Item.Editor;

import java.util.LinkedList;
import java.util.List;

import sparshui.common.Event;
import sparshui.common.TouchState;
import sparshui.common.messages.events.TouchEvent;
import sparshui.gestures.GestureType;

import GUI.Editor.Editor;
import GUI.Multitouch.TouchableGraphicsItem;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRect;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSizeF;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QFontMetrics;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QRadialGradient;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;

public class PushButton extends TouchableEditorItem {

	private LinkedList<Integer> allowedGestures = new LinkedList<Integer>();
	private String labelText;
	private QRectF size = null;
	private QBrush normalBrush = new QBrush(QColor.lightGray);
	private QBrush pressedBrush= new QBrush(QColor.gray);
	private QFont font = new QFont("Helvetica [Cronyx]", 12);
	
	private int padding = 5;
	
	private boolean pressed = false;
	
	public Signal1<TouchableGraphicsItem> buttonPressed = new Signal1<TouchableGraphicsItem>();
	
	public PushButton(Editor editor, String labelText) {
		super(editor);
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
	
	private boolean isEnabled = true;
	
	public void setButtonEnabled(boolean enabled) {
		this.isEnabled = enabled;
		this.update();
	}
	
	public boolean isButtonEnabled() {
		return this.isEnabled;
	}
	
	@Override
	public QRectF boundingRect() {
		return size;
	}

	@Override
	public void paint(QPainter painter, QStyleOptionGraphicsItem option,
			QWidget widget) {
		//painter.setPen(QPen.NoPen);
		//painter.setBrush(normalBrush);
		//painter.drawRect(size);
		if(pressed) {
			painter.setBrush(pressedBrush);
		} else {
			painter.setBrush(normalBrush);
		}
		
		if(this.isEnabled) {
			painter.setPen(QColor.black);
		} else {
			painter.setPen(QColor.darkGray);
		}
		
		painter.drawRoundedRect(size, 25,25);
		painter.drawText(size, 0x84, labelText);
	}

	@Override
	public void setGeometry(QRectF size) {
		this.size = size;
		this.prepareGeometryChange();
		this.update();
	}

	public List<Integer> getAllowedGestures() {
		return allowedGestures;
	}
	
	@Override
	public boolean processEvent(Event event) {		
		if(event instanceof TouchEvent) {
			TouchEvent e = (TouchEvent) event;
			
			//QPointF coordinate = mapFromScene(lazerdoom.View.getInstance().convertScreenPos(e.getX(),e.getY()));
			if(e.getState() == TouchState.BIRTH) {
				this.pressed = true;
				this.update();
			}	
			else if(e.getState() == TouchState.DEATH) {
					this.buttonPressed.emit(this);	
			/*} else {*/
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

	@Override
	public QSizeF getMaximumSize() {
		return new QSizeF(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}
}
