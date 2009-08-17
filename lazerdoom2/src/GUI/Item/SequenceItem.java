package GUI.Item;

import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSizeF;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;
import java.awt.Color;
import java.util.LinkedList;
import java.util.Random;

import com.trolltech.qt.core.QPoint;
import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRect;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.Qt.TransformationMode;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QFontMetrics;
import com.trolltech.qt.gui.QGraphicsItem;
import com.trolltech.qt.gui.QGraphicsItemGroup;
import com.trolltech.qt.gui.QGraphicsSceneMouseEvent;
import com.trolltech.qt.gui.QImage;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPainterPath;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QRadialGradient;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QPainter.RenderHint;

import SceneItems.TouchableGraphicsItem;

public class SequenceItem extends TouchableGraphicsItem {

	private static QColor normalColor = new QColor(130,130,130); 
	private static QColor actionColor = new QColor(211,120,0);
	private static QRectF boundingRect = new QRectF(0,0,200,200);

	private QColor customColor = new QColor(38,50,62);
	private QBrush gradientBrush;

	private QRectF contentsRect = new QRectF(39.5,39.5, 121, 121);
	
	private QImage mnemonic = createMnemonic(boundingRect, 10, 8, 40);

	public static QImage createMnemonic(QRectF size, int numberOfStrips, int penWidth, int minimumCircumference) {
		//dirty hack since painting with aliasing didnt work
		
		int upsampling = 4;
		int rectSizeWidth = (int)size.width()*upsampling;
		penWidth = penWidth*upsampling;
		
		QImage pixmap = new QImage(rectSizeWidth, rectSizeWidth, QImage.Format.Format_ARGB32);
		pixmap.fill(0);
		
		QPainter painter = new QPainter(pixmap);
		painter.setRenderHint(QPainter.RenderHint.HighQualityAntialiasing);
		
		Random random = new Random();
		QColor color = new QColor();
		for(int i = 0; i < numberOfStrips; i++) {
			QPen pen = new QPen();
			color.setHsvF(random.nextDouble(), random.nextDouble(), random.nextDouble());
			pen.setColor(new QColor(color));
			pen.setWidth(penWidth);
			painter.setPen(pen);
			int sizeDeg = minimumCircumference+Math.abs((random.nextInt()) % (rectSizeWidth-penWidth-minimumCircumference));
			QRectF rect = new QRectF((rectSizeWidth-sizeDeg)/2,(rectSizeWidth-sizeDeg)/2, sizeDeg, sizeDeg);
			painter.drawArc(rect, random.nextInt()%(16*360), random.nextInt()%(16*360));
		}
		painter.end();
		return pixmap.scaledToHeight(rectSizeWidth/upsampling, TransformationMode.SmoothTransformation);
	}
	
	public SequenceItem() {
		this.setFlag(GraphicsItemFlag.ItemIsMovable, true);
		this.setFlag(GraphicsItemFlag.ItemIsSelectable, true);

		this.updateGradientBrush();
		

		addPorts();
	}

	public void setCustomColor(QColor color) {
		customColor = color;
	}
	
	private void addPorts() {
		SequenceConnector connector = new SequenceConnector();
		//connector.scale(2.0, 2.0);
		connector.setZValue(0.23);
		connector.setParentItem(this);
		connector.rotate(90.0);
		connector.setPos(200,75);
		connector = new SequenceConnector();
		//connector.scale(2.0, 2.0);
		connector.setParentItem(this);
		connector.rotate(-90.0);
		connector.setPos(0,125);
		connector.setZValue(0.0);
		//blah.addToGroup(this);
		
		this.setZValue(1.0);
	}

	private void updateGradientBrush() {
		double rad = contentsRect.width();
		QRadialGradient gr = new QRadialGradient(rad, rad, rad, 3 * rad / 5, 3 * rad / 5);
		gr.setColorAt(0.0, new QColor(255, 255, 255, 120));
		gr.setColorAt(0.1, new QColor(155, 150, 150, 100));
		gr.setColorAt(0.9, new QColor(50, 50, 50, 30));
		gr.setColorAt(0.95, new QColor(0, 0, 0, 20));
		gr.setColorAt(1, new QColor(0, 0, 0, 0));

		gradientBrush = new QBrush(gr);
	}

	@Override
	public QRectF boundingRect() {
		return boundingRect;
	}

	@Override
	public void paint(QPainter painter, QStyleOptionGraphicsItem option, QWidget w) {
		QColor frameColor;
		
		if(!this.isSelected()) { 
			frameColor = normalColor;
		} else {
			frameColor = actionColor;
		}
		
		painter.drawImage(0,0, mnemonic);

		// outer circle
		painter.setPen(frameColor);
		painter.setBrush(QBrush.NoBrush);
		painter.drawEllipse(boundingRect);

		// inner circle + text
		painter.setBrush(customColor);
		painter.setPen(frameColor);
		painter.drawEllipse(contentsRect);
		
		painter.setBrush(actionColor);
		painter.drawPie(contentsRect, 16*90, -5000);
	
		painter.setBrush(gradientBrush);
		painter.setPen(frameColor);

		painter.drawEllipse(contentsRect);

	}

	@Override
	public QSizeF getPreferedSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSize(QSizeF size) {
		// TODO Auto-generated method stub

	}

}
