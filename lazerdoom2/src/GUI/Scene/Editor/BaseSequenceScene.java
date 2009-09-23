package GUI.Scene.Editor;

import sparshui.common.Event;
import sparshui.common.messages.events.DragEvent;
import GUI.Multitouch.TouchableGraphicsItem;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSizeF;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsLineItem;
import com.trolltech.qt.gui.QLineF;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPainterPath;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QGraphicsItem.GraphicsItemFlag;

import edu.uci.ics.jung.graph.util.Pair;

public class BaseSequenceScene extends EditorScene {
	public static final double valueMultiplyer = 1000;
	public static final double tickMultiplyer = 1.0;
	
	public Signal2<Long, Boolean> startMoved = new Signal2<Long, Boolean>();
	public Signal2<Long, Boolean> lengthMoved = new Signal2<Long, Boolean>();
	
	private QGraphicsLineItem cursor = new QGraphicsLineItem();
	QPen cursorPen = new QPen(QColor.red);
	
	public class StartCursor extends TouchableGraphicsItem {

		public Signal2<Long, Boolean> moved = new Signal2<Long, Boolean>();
		
		QRectF boundingRect = new QRectF(0,-1000, 50, 2000);
		QColor color = new QColor(QColor.green);
		QPen pen = new QPen(color);
		QBrush brush = new QBrush(color);
		QPainterPath path;
		
		public StartCursor() {
			this.setPath();
			this.setZValue(1000.0);
			
		}
		
		public boolean processEvent(Event e) {
			if(e instanceof DragEvent) {
				this.moved.emit((long) (e.getSceneLocation().x()/tickMultiplyer), ((DragEvent) e).isSuccessful());
			}
			return true;
		}
		
		private void setPath() {
			path = new QPainterPath();
			path.moveTo(0,-1000);
			path.lineTo(0,1000);
			path.moveTo(0,-25);
			path.lineTo(50,0);
			path.lineTo(0,25);
			path.lineTo(0,-25);
		}
		
		@Override
		public QRectF boundingRect() {
			return this.boundingRect;
		}

		@Override
		public void paint(QPainter painter, QStyleOptionGraphicsItem option,
				QWidget widget) {
			
			painter.setPen(this.pen);
			painter.setBrush(this.brush);
			painter.drawPath(this.path);
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
	
	public class LengthCursor extends TouchableGraphicsItem {

		QRectF boundingRect = new QRectF(0,-1000, 50, 2000);
		QColor color = new QColor(QColor.blue);
		QPen pen = new QPen(color);
		QBrush brush = new QBrush(color);
		QPainterPath path;
		
		Signal2<Long, Boolean> moved = new Signal2<Long, Boolean>();
		
		public LengthCursor() {
			this.setPath();
			this.setZValue(1000.0);
		}
		
		private void setPath() {
			path = new QPainterPath();
			path.moveTo(50,-1000);
			path.lineTo(50,1000);
			path.moveTo(50,-25);
			path.lineTo(0,0);
			path.lineTo(50,25);
			path.lineTo(50,-25);
		}
		
		public boolean processEvent(Event e) {
			if(e instanceof DragEvent) {
				this.moved.emit((long)(e.getSceneLocation().x()/ tickMultiplyer), ((DragEvent) e).isSuccessful());
			}
			return true;
		}
		
		@Override
		public QRectF boundingRect() {
			return this.boundingRect;
		}

		@Override
		public void paint(QPainter painter, QStyleOptionGraphicsItem option,
				QWidget widget) {
			
			painter.setPen(this.pen);
			painter.setBrush(this.brush);
			painter.drawPath(this.path);
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

	private StartCursor startCursor = new StartCursor();
	private LengthCursor lengthCursor = new LengthCursor();
	
	public BaseSequenceScene() {
		this.cursor.setLine(new QLineF(0,-1000,0,1000));
		this.cursor.setPos(0, 0);
		this.cursorPen.setWidth(5);
		this.cursor.setPen(this.cursorPen);
		this.addItem(cursor);
		this.setSceneRect(new QRectF(0,-1000,1000,2000));
		
		this.addItem(startCursor);
		this.addItem(lengthCursor);
		
		this.startCursor.moved.connect(this.startMoved);
		this.lengthCursor.moved.connect(this.lengthMoved);
				
	}
	
	public void setPlayCursor(long tick) {
		this.cursor.setPos(tickMultiplyer*tick, 0);
	}
	
	public void setStartOffsetCursor(long tick) {
		System.out.println(tick+" ss");
		this.startCursor.setPos(tickMultiplyer*tick, 0);
	}
	
	public void setLengthCursor(long tick) {
		this.lengthCursor.setPos(tickMultiplyer*tick-lengthCursor.boundingRect.width(), 0);
	}
}
