package GUI.Item;

import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSizeF;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;
import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
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

public class SequenceItem extends TouchableGraphicsItem implements ConnectableSequenceInterface, ConnectableSynthInterface {

	private static QColor normalColor = new QColor(130,130,130); 
	private static QColor actionColor = new QColor(211,120,0);
	private static QColor pauseColor = new QColor(0,0,0);
	private static QRectF boundingRect = new QRectF(0,0,200,200);
	private QPen pausePen;
	
	private LinkedList<SynthInConnector> synthInPorts;
	private LinkedList<SynthOutConnector> synthOutPorts;

	private QColor customColor = new QColor(38,50,62);
	private QBrush gradientBrush;

	private QRectF contentsRect = new QRectF(39.5,39.5, 121, 121);
	
	private QImage mnemonic = createMnemonic(boundingRect, 10, 8, 40);

	private SequenceConnector outConnector;
	private SequenceConnector inConnector;
	private boolean isPause = false;
	
	public boolean isPause() {
		return this.isPause;
	}
	
	public SequenceConnector getSequenceOutConnector() {
		return this.outConnector;
	}
	
	public SequenceConnector getSequenceInConnector() {
		return this.inConnector;
	}
	
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
	
	public SequenceItem(boolean isPause) {
		this.setFlag(GraphicsItemFlag.ItemIsMovable, true);
		this.setFlag(GraphicsItemFlag.ItemIsSelectable, true);

		this.setBrushes();

		this.isPause = isPause;

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
		
		this.outConnector = connector;
		
		connector = new SequenceConnector();
		//connector.scale(2.0, 2.0);
		connector.setParentItem(this);
		connector.rotate(-90.0);
		connector.setPos(0,125);
		connector.setZValue(0.0);
		//blah.addToGroup(this);
		
		this.setZValue(1.0);
		
		this.inConnector = connector;
		
		synthInPorts = new LinkedList<SynthInConnector>();
		synthOutPorts = new LinkedList<SynthOutConnector>();
	
		if(!isPause) {
			SynthInConnector synthConnector = new SynthInConnector("Record");
			//connector.scale(2.0, 2.0);
			synthConnector.setZValue(0.23);
			synthConnector.setParentItem(this);
			synthConnector.rotate(0.0);
			synthConnector.setPos(75,-40);
			synthInPorts.add(synthConnector);
			
			SynthOutConnector synthOutConnector = new SynthOutConnector("Sequence");
			//connector.scale(2.0, 2.0);
			synthOutConnector.setZValue(0.23);
			synthOutConnector.setParentItem(this);
			synthOutConnector.rotate(180.0);
			synthOutConnector.setPos(125,240);
			synthOutPorts.add(synthOutConnector);
		}
		
	}

	private void setBrushes() {
		double rad = contentsRect.width();
		QRadialGradient gr = new QRadialGradient(rad, rad, rad, 3 * rad / 5, 3 * rad / 5);
		gr.setColorAt(0.0, new QColor(255, 255, 255, 120));
		gr.setColorAt(0.1, new QColor(155, 150, 150, 100));
		gr.setColorAt(0.9, new QColor(50, 50, 50, 30));
		gr.setColorAt(0.95, new QColor(0, 0, 0, 20));
		gr.setColorAt(1, new QColor(0, 0, 0, 0));

		gradientBrush = new QBrush(gr);
		
		this.pausePen = new QPen(pauseColor);
		this.pausePen.setWidth(12);
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
		
		if(isPause) {
			painter.setBrush(QBrush.NoBrush);
			painter.setPen(this.pausePen);
			
			painter.drawLine(90,80, 90, 120);
			painter.drawLine(110,80, 110, 120);
		}

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

	@Override
	public List<SynthInConnector> getSynthInConnectors() {
		return synthInPorts;
	}

	@Override
	public List<SynthOutConnector> getSynthOutConnectors() {
		return synthOutPorts;
	}

}
