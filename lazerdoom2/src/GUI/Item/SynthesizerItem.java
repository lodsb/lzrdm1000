package GUI.Item;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSizeF;
import com.trolltech.qt.core.Qt.TransformationMode;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QImage;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPainterPath;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QRadialGradient;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;
import GUI.Multitouch.*;

public class SynthesizerItem extends BaseSequenceViewItem implements ConnectableSynthInterface {
	private QRectF contentsRect = new QRectF(39.5,39.5, 121, 121);
	private static QColor normalColor = new QColor(130,130,130); 
	private static QColor actionColor = new QColor(211,120,0);
	private static QColor controlValueColor = new QColor(255,2,104); 
	private static QBrush gradientBrush;
	private QColor customColor = new QColor(38,50,62);
	
	private LinkedList<SynthInConnector> inPorts;
	private LinkedList<SynthOutConnector> outPorts;
	
	private double currentControlValue = 0.0;

	private static QRectF boundingRect = new QRectF(0,0,200,200);

	private QImage mnemonic = createMnemonic(boundingRect, 10, 8, 40);

	public SynthesizerItem(String[] inPortNames) {
		this.setBrushes();
		
		this.setFlag(GraphicsItemFlag.ItemIsMovable, true);
		this.setFlag(GraphicsItemFlag.ItemIsSelectable, true);
		
		this.addPorts(inPortNames);
	}
	
	private void addPorts(String[] inPortNames) {
		inPorts = new LinkedList<SynthInConnector>();
		outPorts = new LinkedList<SynthOutConnector>();
		
		if(inPortNames == null) {
			return;
		}
		
		int numberOfIns = inPortNames.length;

		SynthInConnector connector;

		QPainterPath path = new QPainterPath();
		QRectF adjustedRect = this.boundingRect().adjusted(10, 10, -10, -10).adjusted(-SynthConnector.boundingRect.width()/4, -SynthConnector.boundingRect.height()/4 , SynthConnector.boundingRect.width()*0.75, SynthConnector.boundingRect.height()*0.75);

		path.addEllipse(adjustedRect);

		double circumference = Math.PI*adjustedRect.width();

		double increment = ((circumference)/(double)numberOfIns)/(circumference);
		double alignment = (0.5-(increment*(numberOfIns-1)))/2;

		LinkedList<QPointF> ptList = new LinkedList<QPointF>();
		LinkedList<Double> rotList = new LinkedList<Double>();



		for(double i = alignment; i < 1.0; i+=increment) {
			if(i <= 0.0) continue;
			QPointF p = path.pointAtPercent(i);
			ptList.add(p);
			rotList.add(path.angleAtPercent(i));
		}


		int i = 0;
		for(QPointF p: ptList) {
			connector = new SynthInConnector(inPortNames[i]);
			//connector.scale(2.0, 2.0);
			connector.setParentItem(this);
			connector.rotateCentered(-rotList.get(i));
			connector.setPos(p);
			inPorts.add(connector);
			//this.moved.connect(connector,"parentMoved()");
			i++;
		}
	}
	
	
	
	public void setCurrentControlValue(double controlValue) {
		this.currentControlValue = 0.5;
		this.update();
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

	@Override
	public QRectF boundingRect() {
		// TODO Auto-generated method stub
		return boundingRect;
	}

	@Override
	public void paint(QPainter painter, QStyleOptionGraphicsItem option,
			QWidget widget) {
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
		painter.drawRoundedRect(contentsRect, 25.0,25.0);

		//painter.setBrush(actionColor);
		//painter.drawPie(contentsRect, 16*90, -5000);
		if(currentControlValue > 0.0) {
			painter.setBrush(controlValueColor);
			double width = contentsRect.width()*currentControlValue;
			QRectF rect = new QRectF((boundingRect.width()-width)/2, (boundingRect.width()-width)/2, width, width);
			painter.drawRoundedRect(rect, 25.0*currentControlValue,25.0*currentControlValue);
		} 

		painter.setBrush(gradientBrush);
		painter.setPen(frameColor);

		painter.drawRoundedRect(contentsRect, 25.0,25.0);

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
		return inPorts;
	}

	@Override
	public List<SynthOutConnector> getSynthOutConnectors() {
		return outPorts;
	}

}
