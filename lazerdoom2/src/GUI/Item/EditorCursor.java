package GUI.Item;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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

import GUI.Editor.BaseSequencerItemEditor;
import GUI.Item.Editor.TouchableEditor;
import GUI.Multitouch.TouchableGraphicsItem;
import GUI.View.SequencerView;

public class EditorCursor extends TouchableGraphicsItem {
	private static QRectF boundingRect = new QRectF(-35, -50, 70, 100);
	private QPainterPath cursorPath;
	
	public Signal2<EditorCursor, BaseSequencerItem> openEditor = new Signal2<EditorCursor, BaseSequencerItem>();
	public Signal1<EditorCursor> closeEditor = new Signal1<EditorCursor>();
	//public Signal0 noCollision = new Signal0();
	
	private QPen pen;
	private QBrush brush; //= new QBrush(new QColor(0xFF, 0x50,00));
	
	private boolean isDocked = false;
	private BaseSequencerItem dockedItem = null;
	
	private QColor color;
	
	private void generateBrushAndPen() {
		Random random = new Random();
		this.color = new QColor();
		color.setHsvF(random.nextDouble(), random.nextDouble(), 1.0);
		this.pen = new QPen(color);
		this.brush = new QBrush(color);
	}
	
	public EditorCursor() {
		this.generateBrushAndPen();
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
	
	private TouchableEditor editor = null;
	
	private void editorSceneChanged() {
		if(this.editor != null) {
			this.editor.updateViewScene();
		}
	}
	
	public void showTouchableEditor(BaseSequencerItemEditor editor) {
		if(this.editor == null) {
			this.editor = new TouchableEditor();
			this.editor.setColor(this.color);
			this.editor.closeEditor.connect(this, "hideTouchableEditor()");
			this.editor.setCurrentEditor(editor);
			this.editor.setPos(this.pos());
			
			this.scene().addItem(this.editor);
			
			SequencerView.getInstance().registerEditor(this.editor);
			
		} else {
			this.editor.getCurrentEditor().sceneChanged.disconnect(this);
			this.editor.setCurrentEditor(editor);
			
			this.editor.setVisible(true);
		}
		
		this.editor.getCurrentEditor().sceneChanged.connect(this, "editorSceneChanged()");
		
	}
	
	// how to destroy it?!?
	public void hideTouchableEditor() {
		if(this.editor != null) {
			this.editor.setVisible(false);
		}
	}
	
	public void destroyEditor() {
		if(this.editor != null) {
			this.scene().removeItem(this.editor);
			this.editor = null;
		}
	}
	
	public void setUndocked() {
		if(this.isDocked) {
			this.closeEditor.emit(this);
			this.isDocked = false;
			this.dockedItem = null;
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
			
			if(item instanceof BaseSequencerItem) {
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
				//collidesWithBaseItem.emit(this, (BaseSequenceViewItem) nearestItem);
				
				if(!isDocked) {
					System.out.println("DICKING...");
					this.openEditor.emit(this, (BaseSequencerItem)nearestItem);
					BaseSequencerItem parent  = ((BaseSequencerItem) nearestItem);
					System.out.println(" ddd "+parent);
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
					this.closeEditor.emit(this);
				}
				currentDistance = distance;
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
	public void setGeometry(QRectF size) {
		// TODO Auto-generated method stub

	}

	@Override
	public QSizeF getMaximumSize() {
		// TODO Auto-generated method stub
		return null;
	}

}
