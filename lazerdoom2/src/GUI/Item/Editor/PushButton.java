package GUI.Item.Editor;

import java.util.LinkedList;
import java.util.List;

import sparshui.common.Event;
import sparshui.common.TouchState;
import sparshui.common.messages.events.TapEvent;
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
	
	private String enabledText;
	private String disabledText;
	private QBrush enabledBrush = new QBrush(QColor.darkGreen);
	private QBrush disabledBrush = new QBrush(QColor.darkRed);
	private boolean stateEnabled = false;
	
	public Signal1<TouchableGraphicsItem> buttonPressed = new Signal1<TouchableGraphicsItem>();
	
	public PushButton(Editor editor, String labelText) {
		super(editor);
		allowedGestures.add(GestureType.TOUCH_GESTURE.ordinal());
		allowedGestures.add(GestureType.TAP_GESTURE.ordinal());
		this.labelText = labelText;
		
		this.updateSize();

	}
	
	public PushButton(Editor editor, String enabledText, String disabledText, boolean stateEnabled) {
		super(editor);
		allowedGestures.add(GestureType.TOUCH_GESTURE.ordinal());
		allowedGestures.add(GestureType.TAP_GESTURE.ordinal());
		
		this.enabledText = enabledText;
		this.disabledText = disabledText;
		this.setStateEnabled(stateEnabled);
	}
	
	public void setStateEnabled(boolean enabled) {
		if(enabled) {
			this.normalBrush = enabledBrush;
			this.labelText = enabledText;
		} else {
			this.normalBrush = disabledBrush;
			this.labelText = disabledText;
		}
		this.stateEnabled = enabled;
		
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
		/*if(event instanceof TouchEvent) {
			TouchEvent e = (TouchEvent) event;
			
			//QPointF coordinate = mapFromScene(lazerdoom.View.getInstance().convertScreenPos(e.getX(),e.getY()));
			if(e.getState() == TouchState.BIRTH) {
				this.pressed = true;
				this.update();
			}	
			else if(e.getState() == TouchState.DEATH) {
					this.buttonPressed.emit(this);	
			//} else {
				this.pressed = false;
				this.update();
			}
			//this.update();
		}*/
		if(event instanceof TapEvent) {
			//System.err.println("!!!!!!!!TAP!!!!!!!!!!!!");
			this.buttonPressed.emit(this);	
			this.update();
		}
		//System.err.println(event);
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
