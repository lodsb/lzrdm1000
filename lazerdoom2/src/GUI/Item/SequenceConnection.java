package GUI.Item;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSizeF;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QGraphicsSceneMouseEvent;
import com.trolltech.qt.gui.QLineF;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPainterPath;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QGraphicsItem.GraphicsItemFlag;
import GUI.Multitouch.*;
import GUI.View.SequencerView;

public class SequenceConnection extends TouchableGraphicsItem {

	
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


	
	
	public void paint(QPainter painter, QStyleOptionGraphicsItem option, QWidget widget) {
		this.updatePath();
		painter.setPen(pen);
		painter.drawPath(this.path);
	}
	
	private static QPen pen;
	private int chordWidth = 4;
	private SequenceConnector src;
	private SequenceConnector dst;
	private QPainterPath path;
	
	public SequenceConnection(SequenceConnector src, SequenceConnector dst) {
		pen = new QPen(new QColor(200,200,200));
		pen.setWidth(chordWidth);
		
		this.src = src;
		this.dst = dst;
		this.setZValue(-110.0);
		this.updatePath();
		
		this.setParent(SequencerView.getInstance());
		
		this.src.addConnection(this);
		this.dst.addConnection(this);
	}
	
	public void remove() {
		this.src.removeConnection(this);
		this.src.removeConnection(this);
	}
	
	public SequenceConnector getSource() {
		return this.src;
	}
	
	public SequenceConnector getDestination() {
		return this.dst;
	}
	
	private void updatePath() {
		QPainterPath path = new QPainterPath();
		
		QLineF srcLine = src.baseToTopLineInScene();
		QLineF dstLine = dst.baseToTopLineInScene();
		
		srcLine.setLength(-75);
		dstLine.setLength(-75);
		
		QPointF reference = this.mapToScene(0,0);
		
		if(srcLine.p1().x() <= dstLine.p1().x()) {
			p1 = srcLine.p1();
			p2 = srcLine.p2();
		
			p4 = dstLine.p1();
			p3 = dstLine.p2();
		} else {
			p1 = dstLine.p1();
			p2 = dstLine.p2();
		
			p4 = srcLine.p1();
			p3 = srcLine.p2();
		}
		
		p1 = p1.subtract(reference);
		p2 = p2.subtract(reference);
		p3 = p3.subtract(reference);
		p4 = p4.subtract(reference);
		
		path.moveTo(p1);
		path.cubicTo(p2, p3, p4);
		//path.lineTo(p3);
		//path.lineTo(p4);
		
		this.prepareGeometryChange();
		this.path = path;
	}
	
	@Override
	public QRectF boundingRect() {
		return path.controlPointRect();
	}
	
	@Override
	public QPainterPath shape() {
		return this.path;
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
