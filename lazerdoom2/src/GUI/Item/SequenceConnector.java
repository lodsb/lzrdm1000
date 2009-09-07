package GUI.Item;

import sparshui.common.Event;
import GUI.Multitouch.*;
import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSizeF;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QLineF;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPainterPath;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QTransform;
import com.trolltech.qt.gui.QWidget;

public class SequenceConnector extends TouchableGraphicsItem {

	private static QColor normalColor = new QColor(130,130,130); 
	private static QColor selectionColor = new QColor(255,2,104);

	private static int stdAlpha = 130;

	public static QRectF boundingRect = new QRectF(0,0,50,50);
	private static double centerX = boundingRect.width()/2;
	private static double height = boundingRect.height();
	private static double width = boundingRect.width();

//	private double zValue = -10.0;

	private QColor customColor = new QColor(200,200,200);

	public SequenceConnector() {
		//this.setFlag(GraphicsItemFlag.ItemIsSelectable,true);
		this.setZValue(10.0);

		//customColor.setAlpha(stdAlpha);
		
		
	}
	
	public QLineF baseToTopLineInScene() {

		double x1 = boundingRect.topLeft().x()+boundingRect.width()/2;
		double y1 = 0;

		double x2 = boundingRect.bottomLeft().x()+boundingRect.width()/2;
		double y2 = boundingRect.bottom();

		return new QLineF(mapToScene(x1, y1), mapToScene(x2,y2));
	}

	@Override
	public QRectF boundingRect() {
		return boundingRect;
	}

	public void rotateCentered(double angle) {
		QPointF p = this.mapToParent(new QPointF(width/2,height/2));
		this.setTransform(new QTransform().translate(-p.x()/2, -p.y()/2).rotate(angle).translate(-p.x(), -p.y()));
		//this.rotate(angle);
		//this.setTransform(new QTransform().translate(p.x(), p.y()));
	}

	@Override
	public void paint(QPainter painter, QStyleOptionGraphicsItem option, QWidget widget) {
		QPainterPath p = new QPainterPath();

		if(!this.isSelected()) {
			painter.setPen(normalColor);
			painter.setBrush(customColor);
		} else {
			painter.setPen(selectionColor);
			painter.setBrush(selectionColor);
		}

		p.moveTo(0,height-6);
		p.cubicTo(0, height/3, width/4, height*0.75, width/2, 0);
		p.cubicTo(width*0.75, height*0.75, width, height/3, width, height-6);
		p.cubicTo(width*0.75, height-13.5, width*0.25, height-13.5, 0,height-6);
		p.closeSubpath();
		painter.drawPath(p);
		painter.setBrush(QColor.red);
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
