package GUI.Item;

import java.util.LinkedList;

import sparshui.gestures.TouchGesture;

import GUI.Multitouch.*;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSizeF;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.svg.QGraphicsSvgItem;
import com.trolltech.qt.svg.QSvgRenderer;

public class SequenceButton extends TouchableGraphicsItem {
	// botton-icons.svg includes:
	// "delete"
	// "addSynth"
	// "addSequence"
	
	private static String svgFileName = System.getProperty("user.dir")+"/src/GUI/Item/SVG/button-icons.svg";

	private static QSvgRenderer sharedRenderer = new QSvgRenderer(svgFileName);
	private String elementID;
	
	private QRectF boundingRect = new QRectF(-50.0, -50.0, 100,100);
	
	
	public String elementID() {
		return elementID;
	}
	
	public SequenceButton(String elementID) {
		super();
		this.elementID = elementID;
	}

	@Override
	public QRectF boundingRect() {
		return boundingRect;
	}

	@Override
	public void paint(QPainter painter, QStyleOptionGraphicsItem option, QWidget widget) {
		sharedRenderer.render(painter, this.elementID, this.boundingRect());	
	}

	@Override
	public QSizeF getPreferedSize() {
		QRectF rect = boundingRect();
		return new QSizeF(rect.width(), rect.height());
	}

	@Override
	public void setGeometry(QRectF size) {
	}

	@Override
	public QSizeF getMaximumSize() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
