package gui.item.editor;

import gui.editor.Editor;
import gui.multitouch.TouchableGraphicsItem;

import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSizeF;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QFontMetrics;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;


public class TextLabel extends TouchableEditorItem {

	private String labelText;
	private QRectF size = null;
	private QBrush normalBrush = new QBrush(QColor.lightGray);
	private QFont font = new QFont("Helvetica [Cronyx]", 12);
	
	private int padding = 5;
	
	public Signal1<TouchableGraphicsItem> buttonPressed = new Signal1<TouchableGraphicsItem>();
	
	public TextLabel(Editor editor, String labelText) {
		super(editor);
		this.labelText = labelText;
		
		this.updateSize();

	}
	
	public void setText(String text) {
		this.labelText = text;
		this.updateSize();
	}
	
	private void updateSize() {
		QFontMetrics metrics = new QFontMetrics(font); 
		int textWidth = metrics.width(labelText);
		int labelWidth = 2*padding+textWidth;
		int textHeight = metrics.height();
		int labelHeight= 2*padding+textHeight;
		
		this.prepareGeometryChange();
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
		painter.setPen(QPen.NoPen);
		painter.setBrush(normalBrush);
		painter.drawRect(size);
		painter.setPen(QColor.black);
		painter.drawText(size, 0x84, labelText);
	}

	@Override
	public void setGeometry(QRectF size) {
		this.size = size;
		this.prepareGeometryChange();
		this.update();
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