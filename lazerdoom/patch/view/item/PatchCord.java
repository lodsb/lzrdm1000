package patch.view.item;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.Qt.FillRule;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsLineItem;
import com.trolltech.qt.gui.QGraphicsPathItem;
import com.trolltech.qt.gui.QGraphicsSceneHoverEvent;
import com.trolltech.qt.gui.QLine;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPainterPath;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QGraphicsItem.GraphicsItemFlag;
import com.trolltech.qt.gui.QPainter.RenderHint;


public class PatchCord extends QGraphicsPathItem {

	private final int inOutLetMargin = 100;
	private final int connectorSize = 10;
	
	private final double normalZLevel = 1.0;
	private final double raisedZLevel = 20.0;
	
	private static QPen pen = new QPen(QColor.black);
	
	private double chordWidth = 2.0;
	
	private double inletCenterOffsetX = Inlet.sizeRect.width()/2;
	private double inletCenterOffsetY = Inlet.sizeRect.width()/2;
	
	
	private double outletCenterOffsetX = Outlet.sizeRect.width()/2;
	private double outletCenterOffsetY = Outlet.sizeRect.width()/2;

	
	private Inlet dst;
	private Outlet src;
	
	public PatchCord() {
		super();
		
		this.setZValue(raisedZLevel);
		
		pen.setWidthF(chordWidth);
		this.setPen(pen);
		
		this.setAcceptHoverEvents(true);
	}
	
	public void hoverEnterEvent ( QGraphicsSceneHoverEvent event) {
		this.setZValue(raisedZLevel);
	} 

	public void hoverLeaveEvent ( QGraphicsSceneHoverEvent event) {
		this.setZValue(normalZLevel);
	} 
	
	private void updateSrcAndDst() {
		if(this.src != null && this.dst != null) {
			this.setPos(src.scenePos().x()+inletCenterOffsetX, src.scenePos().y()+inletCenterOffsetY);
			this.setDestination(new QPointF(dst.scenePos().x()+outletCenterOffsetX, dst.scenePos().y()+outletCenterOffsetY));
		}
	}
	
	public void setInletAndOutlet(Outlet src, Inlet dst) {
		this.src = src;
		this.dst = dst;
		
		src.isMovingInScene.connect(this, "updateSrcAndDst()");
		dst.isMovingInScene.connect(this, "updateSrcAndDst()");
		
		this.updateSrcAndDst();
		
		this.setToolTip(src+"+"+dst);
	}
	
	public void setDestination(QPointF pos) {
		QPainterPath path = new QPainterPath();
		
		double targetX = (pos.x()-this.scenePos().x());
		double targetY = (pos.y()-this.scenePos().y());
		
		
		path.addEllipse(0-connectorSize/2,0-connectorSize/2, connectorSize, connectorSize);
		path.moveTo(0,connectorSize/2);
		path.cubicTo(0,0, 0,inOutLetMargin ,(targetX)/2, (targetY)/2);
		path.cubicTo((targetX)/2, (targetY)/2, targetX, targetY-inOutLetMargin, targetX, targetY-connectorSize/2);
		path.addEllipse(targetX-connectorSize/2,targetY-connectorSize/2, connectorSize, connectorSize);
		
		this.setPath(path);
	}
	
	public void paint(QPainter painter, QStyleOptionGraphicsItem option, QWidget w) {
		painter.setRenderHint(RenderHint.Antialiasing);
		super.paint(painter, option, w);
	}
}
