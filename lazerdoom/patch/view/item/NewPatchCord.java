package patch.view.item;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QGraphicsPathItem;
import com.trolltech.qt.gui.QGraphicsSceneMouseEvent;
import com.trolltech.qt.gui.QLineF;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPainterPath;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QGraphicsItem.GraphicsItemFlag;

public class NewPatchCord extends QGraphicsPathItem {
	
	private static QColor normalColor = QColor.black; 
	private static QColor selectionColor = new QColor(255,2,104);
	private static QColor textColor = new QColor(250,250,250);
	

	private static QBrush normalBrush = new QBrush(normalColor);
	private static QBrush selectionBrush = new QBrush(selectionColor);
	private static QPen normalPen = new QPen(normalColor);
	private static QPen selectionPen = new QPen(selectionColor);

	
	private QRectF boundingRect = new QRectF(0,0,100,30);
	
	private static int portMargin = 10;
	
	private QColor customColor = new QColor(38,50,62);
	private String labelText;
	private QRectF textRect = null;
	private static int textAlignVHFlag = 0x4|0x80;
	
	private double zValue = 10.0;
	
	private QFont font =  new QFont("Sans-Serif", 10);

	private QPointF p1;// = new QPointF(-100,120);
	private QPointF p2;// = new QPointF(-50,100);

	private QPointF p3;// = new QPointF(200,120);
	private QPointF p4;// = new QPointF(150,100);
	
	private NewConnector src;
	private NewConnector dst;
	
	public NewPatchCord(NewConnector src, NewConnector dst) {
		
		this.src = src;
		this.dst = dst;
		
		src.moved.connect(this, "updateGeometry()");
		dst.moved.connect(this, "updateGeometry()");
		
		updateGeometry();
		this.setBrush(normalBrush);
		this.setPen(normalPen);
		
		font.setBold(true);
		
		this.setZValue(zValue);
		
		this.setToolTip(src.getModuleName()+": "+src.getPortName()+" >> "+ dst.getModuleName()+": "+dst.getPortName());
		this.labelText = src.getPortName()+" >> "+ dst.getPortName();
		
		this.setFlag(GraphicsItemFlag.ItemIsMovable, true);
		this.setFlag(GraphicsItemFlag.ItemIsSelectable, true);
		
	}
	
	public void mouseMoveEvent(QGraphicsSceneMouseEvent e) {
//		System.out.println(boundingRect);
//		boundingRect.setTop(mapToScene(pos()).y());
		this.updateGeometry();
		super.mouseMoveEvent(e);
	}
	
	private void updateStartAndEndPoints() {
		QLineF srcLine = src.baseToTopLineInScene();
		QLineF dstLine = dst.baseToTopLineInScene();
		
		srcLine.setLength(-50);
		dstLine.setLength(-50);
		
		QPointF reference = this.mapToScene(0,0);
		
		if(srcLine.p1().x() <= dstLine.p1().x()) {
			p1 = srcLine.p1();
			p2 = srcLine.p2();
		
			p3 = dstLine.p1();
			p4 = dstLine.p2();
		} else {
			p1 = dstLine.p1();
			p2 = dstLine.p2();
		
			p3 = srcLine.p1();
			p4 = srcLine.p2();
		}
		
		p1 = p1.subtract(reference);
		p2 = p2.subtract(reference);
		p3 = p3.subtract(reference);
		p4 = p4.subtract(reference);
	}
	
	public void updateGeometry() {
		this.updateStartAndEndPoints();
		
		this.boundingRect.setLeft(p1.x()+50);
		this.boundingRect.setRight(p4.x()-50);
		//this.boundingRect.adjust(-(bx1 - p2.x()), 0, -(bx2 - p4.x()), 0);
		
		QPainterPath path = new QPainterPath();
		
		QPointF pRect1 = new QPointF(boundingRect.topLeft().x()-50, boundingRect.topLeft().y()); 
		QPointF pRect2 = new QPointF(boundingRect.bottomLeft().x()-50, boundingRect.bottomLeft().y());
		
		path.moveTo(boundingRect.topLeft());
		path.cubicTo(pRect1, p2, p1);
		path.cubicTo(p2,pRect2, boundingRect.bottomLeft());
		path.lineTo(boundingRect.bottomRight());
	
		QPointF pRect3 = new QPointF(boundingRect.topRight().x()+50, boundingRect.topRight().y()); 
		QPointF pRect4 = new QPointF(boundingRect.bottomRight().x()+50, boundingRect.bottomRight().y());
		
		path.cubicTo(pRect4, p4, p3);
		path.cubicTo(p4, pRect3, boundingRect.topRight());
		path.lineTo(boundingRect.topLeft());

////		
////		path.addEllipse(pRect1, 5, 5);
////		path.addEllipse(pRect2, 5, 5);
////
//		path.addEllipse(pRect3, 5, 5);
//		path.addEllipse(pRect4, 5, 5);
////		
//		path.addEllipse(p1, 10, 10);
//		path.addEllipse(p2, 10, 10);
//		path.addEllipse(p3, 10, 10);
//		path.addEllipse(p4, 10, 10);
//		
		this.setPath(path);
	}
	
	public void paint(QPainter painter, QStyleOptionGraphicsItem option, QWidget widget) {
		if(!isSelected()) {
			this.setBrush(normalBrush);
			this.setPen(normalPen);
		} else {
			this.setBrush(selectionBrush);
			this.setPen(selectionPen);
		}
		
		super.paint(painter, option, widget);
		painter.setPen(textColor);
		painter.setFont(font);
		painter.drawText(boundingRect,textAlignVHFlag, labelText);
	}
}
