package gui.item.editor;

import gui.multitouch.TouchItemInterface;
import gui.multitouch.TouchableGraphicsItem;
import gui.view.SequencerView;

import java.util.LinkedList;
import java.util.List;

import lazerdoom.LzrDmObjectInterface;

import sceneitems.Util;
import sparshui.common.Event;
import sparshui.common.messages.events.DragEvent;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSizeF;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsItemGroup;
import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;

public class TouchableItemGroupItem extends TouchableGraphicsItem implements LzrDmObjectInterface {
	private int id = Util.getGroupID();
	private LinkedList<Integer> allowedGestures = new LinkedList<Integer>();
	private static QBrush brush = new QBrush(new QColor(255,255,0,120));
	private static QPen pen = new QPen(new QColor(130,130,130));
	private static double frameSize = 20.0;

	
	private LinkedList<TouchableGraphicsItem> items;
	private LinkedList<QPointF> childPositions = new LinkedList<QPointF>();
	
	private QRectF boundingRect;
	
	private double scale = 1.0;
	
	private class ZoomNode extends TouchableGraphicsItem {
		private QBrush brush = new QBrush(QColor.red);
		private QRectF boundingRect = new QRectF(-30,-30, 60, 60);
		
		public Signal2<ZoomNode, QPointF> dragged = new Signal2<ZoomNode, QPointF>();
		
		@Override
		public QRectF boundingRect() {
			return this.boundingRect;
		}

		@Override
		public void paint(QPainter painter, QStyleOptionGraphicsItem option,
				QWidget widget) {
			painter.setBrush(this.brush);
			painter.drawEllipse(this.boundingRect);
		}

		@Override
		public QSizeF getMaximumSize() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public boolean processEvent(Event event) {
			if(event instanceof DragEvent) {
				this.dragged.emit(this, event.getSceneLocation());
			}
			return super.processEvent(event);
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
		
	}
	
	private ZoomNode zoomNode1 = new ZoomNode();
	private ZoomNode zoomNode2 = new ZoomNode();
	
	private double zoomNodeDistance = 0.0;
	private double sideRatio = 1.0;
	private double currentScale = 1.0;
	
	QPointF centerPos = new QPointF(0,0);
	
	public TouchableItemGroupItem(LinkedList<TouchableGraphicsItem> itemsToAdd) {
		this.setZValue(-10.0);
		this.setParent(SequencerView.getInstance());
		//this.setFlag(GraphicsItemFlag.ItemIsMovable, true);
		//this.setFlag(GraphicsItemFlag.ItemIsSelectable, true);
		
		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;

		double maxX = Double.MIN_VALUE;
		double maxY = Double.MIN_VALUE;
		
		for(QGraphicsItemInterface item: itemsToAdd) {				
			QPointF topLeft = item.mapToScene(item.boundingRect()).boundingRect().topLeft();
			QPointF bottomRight = item.mapToScene(item.boundingRect()).boundingRect().bottomRight();
			if(minX > topLeft.x()) {
				minX = topLeft.x();
			}
			if(minY > topLeft.y()) {
				minY = topLeft.y();
			}

			if(maxX < bottomRight.x()) {
				maxX = bottomRight.x();
			}
			if(maxY < bottomRight.y()) {
				maxY = bottomRight.y();
			}
		}

		double width = maxX - minX + 2*frameSize;
		double height = maxY - minY + 2*frameSize;

		this.boundingRect = new QRectF(-width/2,-height/2, width, height);
		System.out.println(boundingRect);
		this.setPos(minX+width/2, minY+height/2);
		
		for(TouchableGraphicsItem item: itemsToAdd) {
			this.childPositions.add(this.mapFromItem(item, 0,0));
			
			item.setBelongsToGroup(this);
		}
		
		this.items = itemsToAdd;
		
		this.zoomNode1.setParent(this);
		this.zoomNode1.setPos(this.boundingRect.topLeft());
		this.zoomNode2.setParent(this);
		this.zoomNode2.setPos(this.boundingRect.bottomRight());
		
		this.zoomNodeDistance = (((zoomNode1.pos().x() - zoomNode2.pos().x())) * ((zoomNode1.pos().x() - zoomNode2.pos().x()))) + (((zoomNode1.pos().y() - zoomNode2.pos().y()))*((zoomNode1.pos().y() - zoomNode2.pos().y())));
		
		this.zoomNode1.dragged.connect(this, "zoomNodeDragged(GUI.Item.Editor.TouchableItemGroupItem$ZoomNode, com.trolltech.qt.core.QPointF)");
		this.zoomNode2.dragged.connect(this, "zoomNodeDragged(GUI.Item.Editor.TouchableItemGroupItem$ZoomNode, com.trolltech.qt.core.QPointF)");
		
		this.sideRatio = 1/(this.boundingRect.width()/this.boundingRect.height());
	}
	
	public void zoomNodeDragged(ZoomNode node, QPointF position) {
		QPointF pos = position;//new QPointF((position.x()+position.y())/2, position.x()+position.y()/2);
		
		ZoomNode opposedZoomNode = null;
		double distance;
		
		if(node == this.zoomNode1) {
			opposedZoomNode = this.zoomNode2;
		} else {
			opposedZoomNode = this.zoomNode1;
		}
		
		QPointF oppPos = opposedZoomNode.mapToScene(0,0);
		distance = ((oppPos.x()-pos.x())*(oppPos.x()-pos.x()))+((oppPos.y()-pos.y())-(oppPos.y()-pos.y()));
		//this.scale(distance/zoomNodeDistance, distance/zoomNodeDistance);
		//System.out.println("position "+position+" "+oppPos+" "+pos+" "+distance+" "+zoomNodeDistance+" "+(distance/zoomNodeDistance));
		
		QPointF mapPos = this.mapFromScene(pos);
		QPointF newNodePosition = new QPointF(mapPos.x(), mapPos.x()*this.sideRatio);
		
		QPointF p1; // topLeft
		QPointF p2; // bottomRight
		
		if(node == this.zoomNode1) {
			p1 = newNodePosition;
			p2 = boundingRect.bottomRight();
		} else {
			p1 = boundingRect.topLeft();
			p2 = newNodePosition;
		}
				
		distance = (((p1.x() - p2.x())) * ((p1.x() - p2.x()))) + (((p1.y() - p2.y()))*((p1.y() - p2.y())));
		double scale = distance/this.zoomNodeDistance;
		if(scale < 1.0 && scale > 0.02) {
			this.boundingRect.setTopLeft(p1);
			this.boundingRect.setBottomRight(p2);
			
			this.zoomNode1.setPos(p1);
			this.zoomNode2.setPos(p2);

			
			this.scaleContent(distance/this.zoomNodeDistance);
			this.update();
		}
	} 
	
	private void scaleContent(double scale) {
		QPointF contentsCenter = this.mapToScene(boundingRect.center());
		this.setContentPositions(contentsCenter);
		this.centerPos = contentsCenter;
		
		this.currentScale = scale;
		//this.setPos(contentsCenter);
	}
	
	@Override
	public QRectF boundingRect() {
		return this.boundingRect;
	}

	public void ungroupItems() {
		this.currentScale = 1.0;
		this.setContentPositions(this.pos());
		for(TouchableGraphicsItem item: this.items) {
			item.setBelongsToGroup(null);
		}
		
		this.items.clear();
		// FIXME: why doesnt remove work?
		this.setVisible(false);
	}
	
	@Override
	public void paint(QPainter painter, QStyleOptionGraphicsItem option,QWidget widget) {
		painter.setPen(pen);
		painter.setBrush(brush);
		
		painter.drawRoundedRect(boundingRect, 25,25);
	}
	
	@Override
	public boolean setPosition(QPointF pos) {
		this.setPos(pos.x()-this.boundingRect.center().x(), pos.y()-this.boundingRect.center().y());
		this.setContentPositions(pos);
		return true;
	}
	
	private void setContentPositions(QPointF pos) {
		double childPosX = 0;
		double childPosY = 0;
		
		int i = 0;
		for(TouchableGraphicsItem item: this.items) {
			QPointF childPos = this.childPositions.get(i);
			childPosX = childPos.x();//+this.centerPos.x();
			childPosY = childPos.y();//+this.centerPos.y();
			item.resetTransform();
			item.scale(this.currentScale, this.currentScale);
			item.setPos(pos.x()+childPosX*this.currentScale, pos.y()+childPosY*this.currentScale);
			i++;
		}
	}

	@Override
	public int getGroupID() {
		return id;
	}


	@Override
	public QSizeF getMaximumSize() {
		// TODO Auto-generated method stub
		return null;
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
}
