package GUI.Item;

import java.util.LinkedList;
import java.util.List;

import lazerdoom.ControlParameter;

import sparshui.common.Event;
import sparshui.common.TouchState;
import sparshui.common.messages.events.DblClkEvent;
import sparshui.common.messages.events.TouchEvent;
import sparshui.gestures.GestureType;
import sparshui.gestures.TouchGesture;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSizeF;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsLineItem;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPainterPath;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;
import GUI.Multitouch.*;

public class MulticontrolItem extends TouchableGraphicsItem {
	
	public enum Mode {
		Edit,
		Control
	}

	private QRectF size = new QRectF(-40,-40,80,80);
	private double width = 10;
	
	private LinkedList<ControlParameter> controlParameters;
	
	private LinkedList<ControlBlob> activeControlBlobs = new LinkedList<ControlBlob>();
	private LinkedList<Integer> allowedGestures = new LinkedList<Integer>();
	private QBrush fillBrush = new QBrush(QColor.blue);
	
	private Mode currentMode = Mode.Control;
	private LinkedList<QBrush> modeColors = new LinkedList<QBrush>();
	private QColor editColor = QColor.red;
	private QColor controlColor = QColor.green;
	
	private ControlRing innerRing;
	private ControlRing outerRing;
	
	private class ControlRing extends TouchableGraphicsItem {

		private QRectF size;
		private double diameter = 40.0;
		private QPainterPath shape;
		
		ControlRing(double diameter) {
			this.setDiameter(diameter);
			this.setZValue(-9.0);
		}
		
		private void setDiameter(double diameter) {
			this.diameter = diameter;
			
			this.prepareGeometryChange();
			this.size = new QRectF(-diameter/2,-diameter/2, diameter, diameter);
			this.size = this.size.adjusted(-5,-5, 5, 5);
			
			QPainterPath path1 = new QPainterPath();
			path1.addEllipse(size);
			QPainterPath path2 = new QPainterPath();
			path2.addEllipse(size.adjusted(width, width, -width, -width));
			
			shape = path1.subtracted(path2);
			//this.update();
				
		}
		
		public double getDiameter() {
			return diameter;
		}
		
		@Override
		public QPainterPath shape() {
			return shape;
		}
		
		@Override
		public QRectF boundingRect() {
			return size;
		}

		@Override
		public void paint(QPainter painter, QStyleOptionGraphicsItem option,
				QWidget widget) {
			painter.setBrush(fillBrush);
			painter.drawPath(shape);
			
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

		@Override
		public QSizeF getMaximumSize() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	private class ControlBlob extends TouchableGraphicsItem {

		private QRectF size = new QRectF(-25,-25,50,50); 
		private String label;
		private QPainterPath shape;
		
		private QBrush normalBrush = new QBrush(QColor.white);
		private QBrush highlightedBrush = new QBrush(QColor.red);
		
		private boolean highlighted = false;
		
		private QGraphicsLineItem line;
		private Signal1<Double> parameterChangeSignal;
		
		public ControlBlob(String label) {
			this.label = label;
			
			shape = new QPainterPath();
			shape.addEllipse(size);
			
		}
		
		public void setHighlighted(boolean highlighted) {
			this.highlighted = highlighted;
			this.update();
		}
		
		public boolean isHighlighted() {
			return this.highlighted;
		}
		
		@Override
		public QRectF boundingRect() {
			return size;
		}
		
		@Override
		public QPainterPath shape() {
			return shape;
			
		}

		@Override
		public void paint(QPainter painter, QStyleOptionGraphicsItem option,
				QWidget widget) {
			if(!highlighted) {
				painter.setBrush(normalBrush);
			} else {
				painter.setBrush(highlightedBrush);
			}
			painter.drawPath(shape);
			painter.drawText(size, 0x84, label);
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

		@Override
		public QSizeF getMaximumSize() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	
	public MulticontrolItem() {
		//allowedGestures.add(GestureType.DBLCLK_GESTURE.ordinal());
		allowedGestures.add(GestureType.TOUCH_GESTURE.ordinal());
		
		modeColors.add(new QBrush(editColor));
		modeColors.add(new QBrush(controlColor));
		
		//addControlBlob("test");
		//this.setControlBlobPosition(activeControlBlobs.get(0), 120,-70);
		
		innerRing = new ControlRing(100.0);
		innerRing.setParent(this);
		innerRing.setParentItem(this);

		outerRing = new ControlRing(150.0);
		outerRing.setParent(this);
		outerRing.setParentItem(this);
		
		LinkedList<ControlParameter> testParm = new LinkedList<ControlParameter>();
		

		this.addControlBlob("TEST");
		this.addControlBlob("TEST1");
		this.addControlBlob("TEST2");
		this.addControlBlob("TEST3");
		
		
		
		this.setControlParameters(testParm);
		//this.alignAndCreateControlBlobsFromControlParameters();

	}
	
	@Override
	public QRectF boundingRect() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public void paint(QPainter painter, QStyleOptionGraphicsItem option,
			QWidget widget) {
		painter.setBrush(modeColors.get(currentMode.ordinal()));
		painter.drawEllipse(size);
		
	}

	public void setControlParameters(LinkedList<ControlParameter> parameterList) {		
		this.controlParameters = parameterList;
	}
	
	private void alignAndCreateControlBlobsFromControlParameters() {
	
		for(ControlBlob cb: activeControlBlobs) {
			cb.scene().removeItem(cb.line);
			cb.scene().removeItem(cb);
			
		}
		
		activeControlBlobs.clear();

		
		if(this.controlParameters.size() == 0) {
			return;
		}
		
		double lengthOfAllBlobs = 0.0;
		double diameter = 150;
		
		for(ControlParameter cp: this.controlParameters) {
			ControlBlob cb = addControlBlob(cp.label);
			cb.parameterChangeSignal = cp.parameterChangeSignal;
			lengthOfAllBlobs += cb.boundingRect().width()+10;
		}
		
		//c=2piR
		
		double radius = lengthOfAllBlobs/(2*Math.PI);
		
		if(radius*2 < diameter) {
			radius = diameter/2;
		} 
		
		double radiantsIncrease = (2*Math.PI)/(this.controlParameters.size());
		
		double angleRadiants = 0;
		for(int i = 0; i < this.controlParameters.size(); i++) {
			this.setControlBlobPosition(activeControlBlobs.get(i), radius*Math.sin(angleRadiants),radius*Math.cos(angleRadiants));
			angleRadiants += radiantsIncrease;
		}
		
		diameter = 2*radius + activeControlBlobs.get(0).boundingRect().width()+10;
		outerRing.setDiameter(diameter);
		outerRing.update();
		
	}
	
	private ControlBlob addControlBlob(String label) {
		ControlBlob cb = new ControlBlob(label);
		QGraphicsLineItem lineItem = new QGraphicsLineItem();
		
		cb.setParent(this);
		cb.setParentItem(this);
		
		lineItem.setParentItem(this);
		
		activeControlBlobs.addLast(cb);
		cb.line = lineItem;
		lineItem.setZValue(-10);
		return cb;
	}
	
	private void setControlBlobPosition(ControlBlob cb, double posX, double posY) {
		cb.setPos(posX, posY);
		QPointF blobCenter = this.mapFromItem(cb, cb.boundingRect().center());
		
		double angle = 0.0;
		

		//if(Math.abs(posX)>=Math.abs(posY)) {
		//	angle = Math.acos(Math.abs(posX)/Math.abs(posY));
		/*	
			if(posX > posY) {
				angle += 90;
			} else {
				angle = 270+angle;
			}
			
		} else {
			angle = Math.asin(posX/posY)*180.0/Math.PI;
			
			if(posX > posY) {
				System.out.println(angle);
				angle = -angle;
			} else {
				angle = 180-angle;
			}
			
		}*/
		//System.out.println(angle);
		//cb.rotate(angle);

		double centerX = this.boundingRect().center().x();
		double centerY = this.boundingRect().center().y();
		
		cb.line.setLine(centerX, centerY, blobCenter.x(), blobCenter.y());
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
	
	public List<Integer> getAllowedGestures() {
		return allowedGestures;
	}
	
	public void setMode(Mode mode) {
		/*if(this.currentMode == Mode.Edit && mode == Mode.Control) {
			if(!activeControlBlobs.isEmpty()) {
				LinkedList<ControlBlob> cbList = new LinkedList<ControlBlob>();
				
				for(ControlBlob cb: activeControlBlobs) {
					if(cb.isHighlighted()) {
						cbList.add(cb);
					} else {
						cb.scene().removeItem(cb.line);
						cb.scene().removeItem(cb);
					}
				}
				
				activeControlBlobs = cbList;
			}
		} else if(this.currentMode == Mode.Control && mode == Mode.Edit) {
			alignAndCreateControlBlobsFromControlParameters();
		}
		*/
		this.currentMode = mode;
		
		update();
	}

	public boolean processEvent(Event event) {
		if(event instanceof TouchEvent) {
			//QPointF coordinate = mapFromScene(lazerdoom.View.getInstance().convertScreenPos(((TouchEvent) event).getX(),((TouchEvent) event).getY()));
			
			QPointF coordinate = event.getSceneLocation();
			//Blobs
			TouchItemInterface it; 
			if((it = event.getSource()) != null) {
				if(currentMode == Mode.Control) {
					if(it instanceof ControlBlob) {
						ControlBlob cb = (ControlBlob) it;
						double nextDistanceFromCenter = Math.sqrt(coordinate.x()*coordinate.x()+coordinate.y()*coordinate.y());

						if(nextDistanceFromCenter < (0.5*outerRing.getDiameter()) && nextDistanceFromCenter > (0.5*innerRing.getDiameter())) {
							this.setControlBlobPosition(cb, coordinate.x(), coordinate.y());

							if(cb.parameterChangeSignal != null) {
								cb.parameterChangeSignal.emit((outerRing.getDiameter()/2)/nextDistanceFromCenter);
							}
						} 

					} else if(it instanceof ControlRing) {
						ControlRing cr = (ControlRing) it;
						
						double diameter = 2*Math.sqrt(coordinate.x()*coordinate.x()+coordinate.y()*coordinate.y());
						
						double oldDistance = (outerRing.getDiameter()-innerRing.getDiameter())/2;
						double oldInnerRingRadius = innerRing.getDiameter()/2;
						
						cr.setDiameter(diameter);
						
						double newDistance = (outerRing.getDiameter()-innerRing.getDiameter())/2;
						
						double diameterIncreaseRatio = newDistance/oldDistance;
									
							for(ControlBlob cb : activeControlBlobs) {
								QPointF pos = cb.pos();

								double blobDistance = Math.sqrt(pos.x()*pos.x()+pos.y()*pos.y());
								//upper
								double t1 = blobDistance-oldInnerRingRadius;
								double t2 = t1*diameterIncreaseRatio;
								double t3 = (innerRing.getDiameter()/2)+t2;
								double stretch = t3/blobDistance;
						
								this.setControlBlobPosition(cb, pos.x()*stretch, pos.y()*stretch);
							}
					} 
				} else if(currentMode == Mode.Edit) {
					if(it instanceof ControlBlob && ((TouchEvent) event).getState() == TouchState.DEATH) {
						ControlBlob cb = (ControlBlob) it;
						cb.setHighlighted(!cb.isHighlighted());
					} 
				}
			} 
		} /*else if(event instanceof DblClkEvent){
			// center touched ...
			if(currentMode == Mode.Control) {
				this.setMode(Mode.Edit);
			} else if(currentMode == Mode.Edit) {
				this.setMode(Mode.Control);
			}
		}*/
		return true;
	}

	@Override
	public QSizeF getMaximumSize() {
		// TODO Auto-generated method stub
		return null;
	}

}
