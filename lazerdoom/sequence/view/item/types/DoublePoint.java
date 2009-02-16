package sequence.view.item.types;

import sequence.SequenceInterface;
import sequence.scene.SequenceSceneItemInterface;
import types.*;

import com.trolltech.qt.QVariant;
import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QFontMetrics;
import com.trolltech.qt.gui.QGraphicsItem;
import com.trolltech.qt.gui.QGraphicsLineItem;
import com.trolltech.qt.gui.QGraphicsSceneHoverEvent;
import com.trolltech.qt.gui.QGraphicsSceneMouseEvent;
import com.trolltech.qt.gui.QGraphicsTextItem;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;

public class DoublePoint extends QGraphicsItem implements SequenceSceneItemInterface<Double>{
	Signal2<SequenceSceneItemInterface, Double> modified = new Signal2<SequenceSceneItemInterface, Double>();
	Signal2<SequenceSceneItemInterface, Double> modifying = new Signal2<SequenceSceneItemInterface, Double>();
	
	private Node headNode = new Node(Node.NodeType.pointNode, Node.NodeType.pointNodeEditing);
	private QGraphicsTextItem textLabel = new QGraphicsTextItem(this); 
	private QGraphicsLineItem entryLine = new QGraphicsLineItem(this);
	private QGraphicsLineItem bottomLine = new QGraphicsLineItem(this);
	
	private QFontMetrics fontMetric = new QFontMetrics(textLabel.font());
	private int fontHeight = fontMetric.height();
	
	
	
	private Double currentValue;
	
	private boolean marked = false;
	
	private TypeHandlerInterface<? extends Double> typeHandler = types.TypeSystem.doubleTypeHandler;
	
	private QPen selectionPen = new QPen();
	private QPen markedPen = new QPen();
	private QPen normalPen = new QPen(QColor.black);
	
	
	public DoublePoint(Double entry) {
		headNode.setParentItem(this);
		updateEntry(entry);
		
		this.setFlag(QGraphicsItem.GraphicsItemFlag.ItemIsSelectable, true);
		
		headNode.hoverEnterEvent.connect(this,"nodeHoverEnterEvent()" );
		headNode.hoverLeaveEvent.connect(this,"nodeHoverLeaveEvent()" );
		
		headNode.mousePressEvent.connect(this,"nodeMousePressEvent()" );
		headNode.mouseReleaseEvent.connect(this,"nodeMouseReleaseEvent()" );
		headNode.mouseMoveEvent.connect(this,"nodeMouseMoveEvent(QPointF)" );
		
		selectionPen.setWidth(2);
		selectionPen.setColor(QColor.red);
		selectionPen.setStyle(Qt.PenStyle.DotLine);
		
		markedPen.setColor(QColor.red);		
		markedPen.setWidth(2);
		
		
		QRectF headBound = headNode.boundingRect();
		bottomLine.setPos(-headBound.width()/2,0);
		entryLine.setPos(0,0);
		
		bottomLine.setZValue(-1.0);
		entryLine.setZValue(-1.0);
		
		bottomLine.setLine(0,0, headBound.width(), 0);
		
		bottomLine.setPen(markedPen);
		entryLine.setPen(markedPen);

		
	}
	
	public void setTypeHandler(TypeHandlerInterface<? extends Double> handler) {
		typeHandler = handler;
	}
	
	private void nodeMouseMoveEvent(QPointF pos) {
		double val = typeHandler.createNewFromScenePos(this.mapFromItem(headNode, pos.x(), pos.y()+headNode.boundingRect().height()/2));
		this.updateEntry(val);
		
		modifying.emit(this, val);
	}
	
	private void nodeMousePressEvent() {
		System.out.println("n mpe");
		this.setSelected(true);
		
		bottomLine.setPen(selectionPen);
		entryLine.setPen(selectionPen);
	}
	
	private void nodeMouseReleaseEvent() {
		modified.emit(this, currentValue);
	}
	
	private void nodeHoverEnterEvent() {
		System.out.println("he");
		marked = true;
		headNode.setEditing(true);
		
		bottomLine.setPen(markedPen);
		entryLine.setPen(markedPen);
	}
	
	private void nodeHoverLeaveEvent() {
		System.out.println("hl");
		marked = false;
		headNode.setEditing(false);
		
		bottomLine.setPen(normalPen);
		entryLine.setPen(normalPen);
		
	}
	
	public void mousePressEvent( QGraphicsSceneMouseEvent  event ) {
		System.out.println("WWOWOW0011100O");
	} 
	
	@Override
	public QRectF boundingRect() {
		QRectF headBound = headNode.boundingRect();
		
		QRectF ret;
		
		if(currentValue >= 0.0) {
			ret = new QRectF(headBound.left()-headBound.width()/2-2,headBound.top()+2,headBound.width()+4, -currentValue-headBound.height()/2-4);
		} else {
			ret = new QRectF(headBound.left()-headBound.width()/2-2,headBound.top()-2,headBound.width()+4, -currentValue+headBound.height()/2+4);
		}
		
		return ret;
	}

	public void paint(QPainter painter, QStyleOptionGraphicsItem option, QWidget widget) {
	
	}

	@Override
	public Signal2<SequenceSceneItemInterface, Double> getModifiedSignal() {
		return modified;
	}

	@Override
	public Signal2<SequenceSceneItemInterface, Double> getModifyingSignal() {
		return modifying;
	}

	@Override
	public QGraphicsItem getRepresentation() {
		return this;
	}

	@Override
	public void updateEntry(Double entry) {
		currentValue = entry;
		
		QPointF pos = this.pos();
		QRectF headBound = headNode.boundingRect();
		
		this.prepareGeometryChange();
		
		headNode.setPos(-headBound.width()/2, -entry-(headBound.height()/2));
		
		textLabel.setPlainText(entry.toString());
		
		if(entry >= 0) {
			textLabel.setPos(-fontMetric.width(entry.toString())/2,-entry-(headBound.height()/2+fontHeight));
		} else {
			textLabel.setPos(-fontMetric.width(entry.toString())/2,-entry+(headBound.height()/2));
		}

		entryLine.setLine(0,0,headNode.x()+headBound.width()/2, headNode.y()+headBound.height()/2);
		
	}

}
