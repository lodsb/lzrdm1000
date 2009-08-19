package GUI.Item;

import java.util.LinkedList;
import java.util.List;

import com.trolltech.qt.core.QEvent;
import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSizeF;
import com.trolltech.qt.core.Qt.ItemSelectionMode;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QGraphicsLineItem;
import com.trolltech.qt.gui.QGraphicsSceneMouseEvent;
import com.trolltech.qt.gui.QLineF;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPainterPath;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QPolygonF;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.svg.QSvgRenderer;

import GUI.Multitouch.TouchableGraphicsItem;

public class EditorCursor extends TouchableGraphicsItem {
	private static QRectF boundingRect = new QRectF(-35, -50, 70, 100);
	private QPainterPath cursorPath;
	
	public Signal1<BaseSequenceViewItem> collidesWithBaseItem = new Signal1<BaseSequenceViewItem>();
	public Signal0 noCollision = new Signal0();
	
	private QPen pen = new QPen(new QColor(0xFF, 0x50,00));
	private QBrush brush = new QBrush(new QColor(0xFF, 0x50,00));
	
	private boolean isDocked = false;
	private BaseSequenceViewItem dockedItem = null;
	
	public EditorCursor() {
		this.setFlag(GraphicsItemFlag.ItemIsMovable, true);
		this.setFlag(GraphicsItemFlag.ItemIsSelectable, true);
		cursorPath = new QPainterPath();
		cursorPath.moveTo(-35, 50);
		cursorPath.lineTo(0,-50);
		cursorPath.lineTo(35, 50);
		cursorPath.lineTo(0, 20);
		cursorPath.closeSubpath();
	}
	
	public void mouseMoveEvent(QGraphicsSceneMouseEvent event) {
		if(setPosition(this.mapToScene(event.pos()))) {
			super.mouseMoveEvent(event);
		}
	}
	
	public boolean setPosition(QPointF nextPos) {
		boolean ret = true;
		
		QPointF pos = this.mapToScene(new QPointF(0,0));
		List<QGraphicsItemInterface> itemList = this.scene().items(pos.x()-200, pos.y()-200, 400, 400);
		
		QGraphicsItemInterface nearestItem = null;
		double currentDistance = Double.MAX_VALUE;
		
		for(QGraphicsItemInterface item: itemList) {
			if(item == this) {
				continue;
			} 
			
			if(item instanceof BaseSequenceViewItem) {
				QPointF iPos = item.mapToScene(item.boundingRect().width()/2, item.boundingRect().height()/2);
				double distance = ((pos.x()-iPos.x())*(pos.x()-iPos.x()))+((pos.y()-iPos.y())*(pos.y()-iPos.y()));
				if(distance < currentDistance) {
					currentDistance = distance;
					nearestItem = item;
				}
			}
		}
		
		if(nearestItem != null) {
			QPointF iPos = nearestItem.mapToScene(nearestItem.boundingRect().width()/2, nearestItem.boundingRect().height()/2);
			double xDist = iPos.x() - pos().x();
			double yDist = iPos.y() - pos().y();
			double distance = Math.sqrt(((pos.x()-iPos.x())*(pos.x()-iPos.x()))+((pos.y()-iPos.y())*(pos.y()-iPos.y())));
			double angle = 180 +  Math.acos(yDist/distance)/ 6.28 * 360 ;
			
			
			if(xDist != 0 && yDist != 0) {
				if(angle > 180 && xDist > 0) {
					angle = -angle;
				}
				this.resetTransform();	
				this.rotate(angle);
			}
			
			if(/*this.scene().itemAt(nextPos)!= this || */this.collidesWithItem(nearestItem, ItemSelectionMode.IntersectsItemShape) || this.collidesWithItem(nearestItem, ItemSelectionMode.ContainsItemShape)) {
				// do something here
				collidesWithBaseItem.emit((BaseSequenceViewItem) nearestItem);
				
				if(!isDocked) {
					BaseSequenceViewItem parent  = ((BaseSequenceViewItem) nearestItem);
					parent.dockCursor(this);
					this.dockedItem = parent;
					this.isDocked = true;
				}
				
				
				
				double nextDistance = Math.sqrt(((nextPos.x()-iPos.x())*(nextPos.x()-iPos.x()))+((nextPos.y()-iPos.y())*(nextPos.y()-iPos.y())));
				if(nextDistance < distance) {
					ret = false;
				}
			} else {
				if(isDocked) {
					this.dockedItem.undockCursor(this);
					this.isDocked = false;
					this.dockedItem = null;
				}
				currentDistance = distance;
				noCollision.emit();
			}
						
				//System.out.println(xDist+" "+yDist+" "+(yDist/xDist));
			//}
		}
	
		return ret;
	}
	
	public QPainterPath shape() {
		return cursorPath;
	}
	
	@Override
	public QRectF boundingRect() {
		// TODO Auto-generated method stub
		return boundingRect;
	}

	@Override
	public void paint(QPainter painter, QStyleOptionGraphicsItem option,
			QWidget widget) {
		painter.setPen(pen);
		painter.setBrush(brush);
		painter.drawPath(cursorPath);
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
