package gui.item;

import gui.view.SequencerView;

import java.util.LinkedList;
import java.util.List;

import lazerdoom.LzrDmObjectInterface;


import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSizeF;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPainterPath;
import com.trolltech.qt.gui.QRadialGradient;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;

public class ControllerItem extends BaseSequencerItem {
	private QRectF contentsRect = new QRectF(39.5,39.5, 121, 121);
	private static QColor normalColor = new QColor(130,130,130); 
	private static QColor actionColor = new QColor(211,120,0);
	private static QColor controlValueColor = new QColor(255,2,104); 
	private static QBrush gradientBrush;
	private QColor customColor = new QColor(38,50,62);
	
	private LinkedList<SynthOutConnector> outPorts;
	
	private double currentControlValue = 0.0;

	private static QRectF boundingRect = new QRectF(0,0,100,100);
	
	public ControllerItem() {
		this.setBrushes();

		this.setParent(SequencerView.getInstance());
		this.addPorts();
	}
	
	private int connectors = 4;
	
	private void addPorts() {
		if(this.scene() != null) {
			if(outPorts != null) {
				for(SynthOutConnector in: outPorts) {
					scene().removeItem(in);
				}	
			}
		}
		
		outPorts = new LinkedList<SynthOutConnector>();
		
		if(connectors == 0) {
			return;
		}
		
		int numberOfOuts = connectors;
		System.out.println("blen "+numberOfOuts);

		SynthOutConnector connector;

		QPainterPath path = new QPainterPath();
		QRectF adjustedRect = this.boundingRect().adjusted(10, 10, -10, -10).adjusted(-SynthConnector.boundingRect.width()/4, -SynthConnector.boundingRect.height()/4 , SynthConnector.boundingRect.width()*0.75, SynthConnector.boundingRect.height()*0.75);

		path.addEllipse(adjustedRect);

		double circumference = Math.PI*adjustedRect.width();

		double increment = ((circumference)/(double)numberOfOuts)/(circumference);
		double alignment = (0.5-(increment*(numberOfOuts-1)))/2;

		LinkedList<QPointF> ptList = new LinkedList<QPointF>();
		LinkedList<Double> rotList = new LinkedList<Double>();



		for(double i = alignment; i < 1.0; i+=increment) {
			if(i < 0.0) continue;
			QPointF p = path.pointAtPercent(1.0-i);
			ptList.add(p);
			rotList.add(path.angleAtPercent(1.0-i));
		}


		int i = 0;
		for(QPointF p: ptList) {
			connector = new SynthOutConnector("TESTOUT1");
			//connector.scale(2.0, 2.0);
			connector.setParent(this);
			connector.rotateCentered(-rotList.get(i));
			connector.setPos(p);
			outPorts.add(connector);
			//this.moved.connect(connector,"parentMoved()");
			i++;
		}
	}
	
	
	
	public void setCurrentControlValue(float controlValue) {
		controlValue = Math.abs(controlValue);
		if(controlValue > 1) {controlValue = 1;}
		this.currentControlValue = controlValue;
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

		//painter.drawImage(0,0, mnemonic);

		// outer circle
		painter.setPen(frameColor);
		painter.setBrush(QBrush.NoBrush);
		painter.drawEllipse(boundingRect);

		// inner circle + text
		painter.setBrush(customColor);
		painter.setPen(frameColor);
		painter.drawEllipse(boundingRect);

		//painter.setBrush(actionColor);
		//painter.drawPie(contentsRect, 16*90, -5000);
		/*if(currentControlValue > 0.0) {
			painter.setBrush(controlValueColor);
			double width = contentsRect.width()*currentControlValue;
			QRectF rect = new QRectF((boundingRect.width()-width)/2, (boundingRect.width()-width)/2, width, width);
			//painter.drawRoundedRect(rect, 25.0*currentControlValue,25.0*currentControlValue);
		} */

		painter.setBrush(gradientBrush);
		painter.setPen(frameColor);

		//painter.drawRoundedRect(contentsRect, 25.0,25.0);

	}

	@Override
	public QSizeF getPreferedSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGeometry(QRectF size) {
		// TODO Auto-generated method stub

	}

	public List<SynthOutConnector> getControlConnectors() {
		return outPorts;
	}

	@Override
	public QSizeF getMaximumSize() {
		// TODO Auto-generated method stub
		return null;
	}


	private boolean isInitialized = false;
	@Override
	public boolean isInitialized() {
		return this.isInitialized;
	}

	@Override
	public void setContentObject(LzrDmObjectInterface object) {
		
	}
}

