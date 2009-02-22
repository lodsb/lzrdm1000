package patch.view.item;


import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QDrag;
import com.trolltech.qt.gui.QGraphicsItem;
import com.trolltech.qt.gui.QGraphicsSceneDragDropEvent;
import com.trolltech.qt.gui.QGraphicsSceneHoverEvent;
import com.trolltech.qt.gui.QGraphicsSceneMouseEvent;
import com.trolltech.qt.gui.QMouseEvent;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QGraphicsItem.GraphicsItemFlag;

public class Inlet extends QGraphicsItem {

	private final int margin = 5;
	
	public static QRectF sizeRect = new QRectF(0,0,20,20);
	
	private final QRectF innerRect = sizeRect.adjusted(margin, margin, -margin, -margin);
	
	private QColor color;
	
	private QColor hoverColor = QColor.red;
	
	public Signal1<QGraphicsItem> dragStarted = new Signal1<QGraphicsItem>();
	public Signal1<QGraphicsItem> dragAccepted = new Signal1<QGraphicsItem>();
	
	public Signal0 isMovingInScene = new Signal0();
	
	private boolean hover = false;
	
	public Inlet(PatchNodeItem patchNodeItem, QColor color) {
		super(patchNodeItem);
		this.color = color;
		
		setAcceptDrops(true);
		setAcceptHoverEvents(true);
		
		patchNodeItem.patchNodeItemDraggingAtScenePos.connect(isMovingInScene);
		patchNodeItem.geometryChanged.connect(isMovingInScene);
	}
	
	public QRectF boundingRect() {
		return sizeRect;
	}
	
	public QPointF connectionPosCenter() {
		return new QPointF(sizeRect.width()/2, sizeRect.height()/2);
	}
	
	public void mousePressEvent(QGraphicsSceneMouseEvent event) {
System.out.println("sdjksdfkljfsd");
		dragStarted.emit(this);
		ConnectionMimeData data = new ConnectionMimeData();
		data.setColorData(color);

		QDrag drag = new QDrag(event.widget());
		drag.setMimeData(data);
		drag.exec();
	}
	
	public void dragEnterEvent(QGraphicsSceneDragDropEvent event) {
		event.accept();
	} 
	
	public void dropEvent(QGraphicsSceneDragDropEvent event) {
		event.accept();
		dragAccepted.emit(this);
	}
	
	public void dragLeaveEvent(QGraphicsSceneDragDropEvent event) {		
		event.ignore();
		
		update();
	} 
	
	public void hoverEnterEvent ( QGraphicsSceneHoverEvent event) {
		hover = true;
		update();
	} 

	public void hoverLeaveEvent ( QGraphicsSceneHoverEvent event) {
		hover = false;
		update();
	} 

	
	@Override
	public void paint(QPainter painter, QStyleOptionGraphicsItem option, QWidget w) {
		if(hover) {
			painter.setPen(hoverColor);
		}
		
		painter.drawEllipse(sizeRect);
		painter.setPen(color);
		painter.setBrush(new QBrush(color));
		painter.drawEllipse(innerRect);
	}

}
