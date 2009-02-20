package sequence.view;

import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QTimeLine;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsItem;
import com.trolltech.qt.gui.QGraphicsLineItem;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.svg.QGraphicsSvgItem;
import com.trolltech.qt.svg.QSvgRenderer;

public class Cursor extends QGraphicsLineItem {

	private GenericSequenceGraphicsView viewport;
	private QGraphicsSvgItem topArrow = new QGraphicsSvgItem();
	private QGraphicsSvgItem bottomArrow = new QGraphicsSvgItem();
	
	
	private QColor cursorColor = new QColor(QColor.blue);
	private QPen cursorPen = new QPen(cursorColor);
	
	private static int frames = 50;
	private static int time = 4000;
	
	private QTimeLine blinkerTimeline = new QTimeLine(time);
	
	private double viewTop = 0;
	private double viewBottom = 0;
	
	private static String resourceFile = new String("classpath:sequence/view/icons/cursor.svg");
	private static QSvgRenderer renderer = new QSvgRenderer(resourceFile);
	
	public Cursor(GenericSequenceGraphicsView view) {
		viewport = view;
		view.viewChanged.connect(this, "updateGeometries()");
		
		topArrow.setSharedRenderer(renderer);
		bottomArrow.setSharedRenderer(renderer);
		
		topArrow.setParentItem(this);
		topArrow.setElementId("g4998");
		
		bottomArrow.setParentItem(this);
		bottomArrow.setElementId("g3971");
		
		updateGeometries();
		
		this.setPos(100, 0);
		
		blinkerTimeline.setFrameRange(0, frames);
		
		this.setPen(cursorPen);
		
		blinkerTimeline.frameChanged.connect(this, "updateBlinker(int)");
		blinkerTimeline.finished.connect(this,"restartBlinker()");
		blinkerTimeline.start();
		blinkerTimeline.setCurveShape(QTimeLine.CurveShape.CosineCurve);
		
		cursorPen.setWidth(1);
	}
	
	private void restartBlinker() {
		blinkerTimeline.start();
	}
	
	private void updateBlinker(int frame) {
		cursorColor.setRedF(blinkerTimeline.valueForTime(time/(frame+1)));
		cursorPen.setColor(cursorColor);
		this.setPen(cursorPen);
	}

	
	private void updateGeometries() {
		if(viewTop != viewport.visibleRect().top() || viewBottom != viewport.visibleRect().bottom()) {
			
			viewTop = viewport.visibleRect().top();
			viewBottom = viewport.visibleRect().bottom();
			
			this.setLine(this.pos().x(),viewTop+1,this.pos().x(),viewBottom-20);
			topArrow.setPos(this.pos().x()-topArrow.boundingRect().width()/2,viewTop);
			bottomArrow.setPos(this.pos().x()-bottomArrow.boundingRect().width()/2,viewBottom-bottomArrow.boundingRect().height()-20);
		}
	}
}
