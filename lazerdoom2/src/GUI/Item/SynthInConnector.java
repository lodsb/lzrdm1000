package GUI.Item;

import java.util.LinkedList;

import sparshui.common.Event;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPainterPath;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;

import lazerdoom.LzrDmObjectInterface;
import Session.SessionHandler;
import Synth.SynthInstance;
import Control.ParameterControlBus;

public class SynthInConnector extends SynthConnector implements LzrDmObjectInterface {
	private String parameterName;
	private ParameterControlBus parameterControlBus;
	private SynthInstance synthInstance;
	
	//private LinkedList<Integer> allowedGestures = new LinkedList();
	
	public SynthInConnector(String parameterName, ParameterControlBus controlBus, SynthInstance synth) {
		// TODO: add register-process to command
		SessionHandler.getInstance().registerObject(this);
		this.parameterName = parameterName;
		this.parameterControlBus = controlBus;
		this.synthInstance = synth;
		super.setIsInPort(true);
		
		//allowedGestures.add(sparshui.gestures.GestureType.TOUCH_GESTURE.ordinal());
		
		this.captionPath = new QPainterPath();
		this.captionPath.addEllipse(this.boundingRect().adjusted(-50, -50, 50, 50));
		this.captionPath.closeSubpath();
		this.captionFont = new QFont("Helvetica [Cronyx]", 24);
		this.captionFont.setBold(true);
		this.captionTimer = new QTimer();
		this.captionTimer.timeout.connect(this, "hideCaption()");
		this.captionTimer.setInterval(2000);
	}

	private void hideCaption() {
		this.showCaption = false;
		this.update();
	}
	
	private QFont captionFont;
	private QPainterPath captionPath;
	private boolean showCaption = false;
	private QTimer captionTimer;
	
	@Override
	public boolean processEvent(Event event) {
		//this.captionTimer.stop();
		this.captionTimer.start();
		this.showCaption = true;
		this.update();
		return super.processEvent(event);
	}
	
	private void createCaptionPath(QPainter painter) {
		QPainterPath path = this.captionPath;
		
		painter.setFont(captionFont);
		String label = this.parameterName+"-";

		QPen pen = new QPen();
		pen.setColor(this.normalColor);
		painter.setPen(pen);

		double percentIncrease =  1.0/(30.0);
		double percent = 0;

		int j = 0;
		for ( int i = 0; percent < 1.0; i++ ) {

			if((((int)(1.0/percentIncrease)) - i < label.length()) && j == label.length()-1) {
				break;
			}

			percent += percentIncrease;
			if(percent > 1.0) {
				break;
			}

			QPointF point = path.pointAtPercent(percent);
			double angle = path.angleAtPercent(percent);


			String textChar;
			if(j >= label.length()) {
				j = 0;
			}

			textChar = label.substring(j, j+1);
			j++;


			painter.save();
			painter.translate(point);
			painter.rotate(-angle);
			painter.drawText(0,0, textChar);
			painter.restore();
		}
	}
	
	public void paint(QPainter painter, QStyleOptionGraphicsItem option, QWidget widget) {
		super.paint(painter, option, widget);
		if(showCaption) {
			this.createCaptionPath(painter);
		}
	}
	
	public String getParameterName() {
		return this.parameterName;
	}
	
	public ParameterControlBus getParameterControlBus() {
		return this.parameterControlBus;
	}
	
	public SynthInstance getSynthInstance() {
		return this.synthInstance;
	}
}
