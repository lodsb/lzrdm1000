package patch.view.item;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.Qt.PenStyle;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsItem;
import com.trolltech.qt.gui.QLineF;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPainterPath;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QTransform;
import com.trolltech.qt.gui.QWidget;

public class NewConnector extends QGraphicsItem {

	private static QColor normalColor = new QColor(130,130,130); 
	private static QColor selectionColor = new QColor(255,2,104);
	private static QColor textColor = new QColor(250,250,250);
	
	private static int stdAlpha = 130;
	
	public static QRectF boundingRect = new QRectF(0,0,20,20);
	private static double centerX = boundingRect.width()/2;
	private static double height = boundingRect.height();
	private static double width = boundingRect.width();
	
	private String name;
	private int id;
	
	private String moduleName;
	private int moduleID;

	
	public Signal0 moved = new Signal0();
	
	private double zValue = -10.0;
	
	private QColor customColor = new QColor(220,200,200);
	
	private boolean isOutput;
	
	public NewConnector(boolean isOutput, int id, String name, int moduleID, String moduleName) {
		this.setFlag(GraphicsItemFlag.ItemIsSelectable,true);
		this.setZValue(zValue);
		
		this.setCustomColor(customColor);
		
		this.isOutput = isOutput;
		
		this.setToolTip(name);
		
		this.name = name;
		this.moduleName = moduleName;
		this.id = id;
		this.moduleID = moduleID;
	}

	public String getPortName() {
		return name;
	}
	
	public int getPortID() {
		return id;
	}
	
	public String getModuleName() {
		return moduleName;
	}
	
	public int getModuleID() {
		return moduleID; 
	}
	
	public QLineF baseToTopLineInScene() {
		
		double x1 = boundingRect.topLeft().x()+boundingRect.width()/2;
		double y1 = 0;
		
		double x2 = boundingRect.bottomLeft().x()+boundingRect.width()/2;
		double y2 = boundingRect.bottom();
		
		return new QLineF(mapToScene(x1, y1), mapToScene(x2,y2));
	}
	
	public boolean isOutput() {
		return isOutput;
	}
	
	public void parentMoved() {
		moved.emit();
	}
	
	@Override
	public QRectF boundingRect() {
		return boundingRect;
	}
	
	public void setCustomColor(QColor color) {
		customColor = color;
		customColor.setAlpha(stdAlpha);
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
		
		p.moveTo(0,height);
		p.cubicTo(0, height/3, width/4, height*0.75, width/2, 0);
		p.cubicTo(width*0.75, height*0.75, width, height/3, width, height);
		p.closeSubpath();
		painter.drawPath(p);
		painter.setBrush(QColor.red);
	}

}
