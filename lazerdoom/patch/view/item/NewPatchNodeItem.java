package patch.view.item;

import java.awt.Color;
import java.util.LinkedList;

import com.trolltech.qt.core.QPoint;
import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRect;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QFontMetrics;
import com.trolltech.qt.gui.QGraphicsItem;
import com.trolltech.qt.gui.QGraphicsSceneMouseEvent;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPainterPath;
import com.trolltech.qt.gui.QRadialGradient;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;

public class NewPatchNodeItem extends QGraphicsItem {

	private static QColor normalColor = new QColor(130,130,130); 
	private static QColor selectionColor = new QColor(255,2,104);
	private static QColor textColor = new QColor(250,250,250);
	private static QRectF boundingRect = new QRectF(0,0,200,200);
	
	private static int portMargin = 10;
	
	private QColor customColor = new QColor(38,50,62);
	private QBrush gradientBrush;
	
	private static int textAlignVHFlag = 0x4|0x80;
	
	private String labelText = "Arpeggiator";
	private QRectF textRect = null;
	
	private QFont font =  new QFont("Sans-Serif", 12);
	
	private Signal0 moved = new Signal0();
	
	public NewPatchNodeItem() {
		this.setFlag(GraphicsItemFlag.ItemIsMovable, true);
		this.setFlag(GraphicsItemFlag.ItemIsSelectable, true);
		
		font.setBold(true);
		this.updateTextRect(labelText);
		this.updateGradientBrush();
		
		addOutPorts();
	}
	public void mouseMoveEvent(QGraphicsSceneMouseEvent event) {
		moved.emit();
		super.mouseMoveEvent(event);
	}
	
	private void updateTextRect(String text) {
		QFontMetrics fontMetrics = new QFontMetrics(font);
		int textWidth = fontMetrics.width("_"+text+"_");
		double textXOffset = (boundingRect.width()-textWidth)/2;
		double textYOffset = (boundingRect.height()-textWidth)/2;
		textRect = new QRectF(textXOffset, textYOffset, textWidth, textWidth);
	}
	
	public void setCustomColor(QColor color) {
		customColor = color;
	}
	
	private void addOutPorts() {
		
		int numberOfOuts = 4;
		int numberOfIns = 5;
		
		NewConnector connector;
		
		QPainterPath path = new QPainterPath();
		QRectF adjustedRect = textRect.adjusted(-NewConnector.boundingRect.width()/4, -NewConnector.boundingRect.height()/4 , NewConnector.boundingRect.width()*0.75, NewConnector.boundingRect.height()*0.75);
		
		path.addEllipse(adjustedRect);
		
		double circumference = Math.PI*adjustedRect.width();
		double widthOfOutConnectionsWithMargin = (numberOfOuts+2)*NewConnector.boundingRect.width();
		double widthOfInConnectionsWithMargin = (numberOfOuts+2)*NewConnector.boundingRect.width();
		
		double minimumCircumference = widthOfInConnectionsWithMargin+widthOfOutConnectionsWithMargin;
		
		if(minimumCircumference < circumference) {
			//FIXME: adjust ellipse here
			System.out.println("minimumCircumference < circumference)");
			//return;
		}
		
		double increment = ((circumference/2)/(double)numberOfOuts)/(circumference);
		double alignment = (0.5-(increment*(numberOfOuts-1)))/2;
		
		LinkedList<QPointF> ptList = new LinkedList<QPointF>();
		LinkedList<Double> rotList = new LinkedList<Double>();
		
		
		
		for(double i = alignment; i <= 0.5; i+=increment) {
			QPointF p = path.pointAtPercent(i);
			ptList.add(p);
			rotList.add(path.angleAtPercent(i));
		}

		
		int i = 0;
		for(QPointF p: ptList) {
			connector = new NewConnector(true, i, "doom", 0, labelText);
			connector.setParentItem(this);
			connector.rotateCentered(-rotList.get(i));
			connector.setPos(p);
			this.moved.connect(connector,"parentMoved()");
			i++;
		}
		
		increment = ((circumference/2)/(double)numberOfIns)/(circumference);
		alignment = (0.5-(increment*(numberOfIns-1)))/2;
		
		ptList = new LinkedList<QPointF>();
		rotList = new LinkedList<Double>();
		
		
		
		for(double j = alignment+0.5; j <= 1.0; j+=increment) {
			QPointF p = path.pointAtPercent(j);
			ptList.add(p);
			rotList.add(path.angleAtPercent(j));
		}

		
		int j = 0;
		for(QPointF p: ptList) {
			connector = new NewConnector(false, j, "lazer", 0, labelText);
			connector.setParentItem(this);
			connector.rotateCentered(-rotList.get(j));
			connector.setPos(p);
			this.moved.connect(connector,"parentMoved()");
			j++;
		}
		
	} 
	
	private void updateGradientBrush() {
		double rad = textRect.width();
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
			frameColor = selectionColor;
		}
		
		// outer circle
		painter.setPen(frameColor);
		painter.drawEllipse(boundingRect);
		
		// inner circle + text
        painter.setBrush(customColor);
		painter.setPen(frameColor);
		painter.drawEllipse(textRect);

		painter.setPen(textColor);
		painter.setFont(font);
		painter.drawText(boundingRect,textAlignVHFlag, labelText);
		

        painter.setBrush(gradientBrush);
		painter.setPen(frameColor);

		painter.drawEllipse(textRect);

	}

}
