package patch.view.item;

import java.awt.geom.Path2D;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsRectItem;
import com.trolltech.qt.gui.QGraphicsSceneMouseEvent;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPainterPath;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;

public class PatchNodeTab extends QGraphicsRectItem {
	
	private final int margin = 10;
	
	boolean mouseClicked = false;
	
	public Signal1<QPointF> patchNodeTabDraggingAtScenePos = new Signal1<QPointF>();
	
	public PatchNodeTab(PatchNodeItem parent, QRectF rect) {
		super(parent);
		this.setRect(rect);
	}
	
	public void mousePressEvent(QGraphicsSceneMouseEvent event) {
		mouseClicked = true;
	}
	
	public void mouseReleaseEvent(QGraphicsSceneMouseEvent event) {
		mouseClicked = false;
	}

	public void mouseMoveEvent(QGraphicsSceneMouseEvent event) {
		patchNodeTabDraggingAtScenePos.emit(this.mapToScene(event.pos()));
	}

	
	public void paint(QPainter painter, QStyleOptionGraphicsItem option, QWidget w) {
		QRectF rect = this.rect();
		
		QPainterPath path = new QPainterPath();
		
		path.moveTo(rect.topRight());
		
		path.cubicTo(rect.topRight(), new QPointF(rect.topRight().x(), rect.topRight().y()+margin),new QPointF(rect.right()-rect.width()/5, rect.topLeft().y()+margin) );
		path.cubicTo(new QPointF(rect.left()+rect.width()/5, rect.topLeft().y()+margin), new QPointF(rect.left(),rect.topLeft().y()+margin),new QPointF(rect.left(), rect.topLeft().y()+2*margin));
		path.lineTo(new QPointF(rect.left(), rect.bottomLeft().y()-2*margin));
		
		path.moveTo(rect.topRight());
		path.lineTo(rect.bottomRight());
		path.cubicTo(rect.bottomRight(), new QPointF(rect.bottomRight().x(), rect.bottomRight().y()-margin),new QPointF(rect.right()-rect.width()/5, rect.bottomLeft().y()-margin) );
		path.cubicTo(new QPointF(rect.left()+rect.width()/5, rect.bottomLeft().y()-margin), new QPointF(rect.left(),rect.bottomLeft().y()-margin),new QPointF(rect.left(), rect.bottomLeft().y()-2*margin));
		

		painter.setBrush(new QBrush(QColor.red));
		
		
		painter.drawPath(path);
		//painter.drawRect(this.rect());
		
	}
}
