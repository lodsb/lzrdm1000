package patch.view.item;

import java.util.LinkedList;


import com.trolltech.qt.QSignalEmitter.Signal1;
import com.trolltech.qt.core.QEvent;
import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsItem;
import com.trolltech.qt.gui.QGraphicsProxyWidget;
import com.trolltech.qt.gui.QGraphicsSceneMouseEvent;
import com.trolltech.qt.gui.QGraphicsSceneMoveEvent;
import com.trolltech.qt.gui.QGraphicsSceneResizeEvent;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPainterPath;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QSizePolicy;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QGraphicsItem.GraphicsItemFlag;
import com.trolltech.qt.gui.QLayout.SizeConstraint;

public class PatchNodeItem extends QGraphicsProxyWidget {
	
	private final int marginSize = 5;
	public final double normalZLevel = -2.0;
	public final double raisedZLevel = 10.0;
	private final int tabWidth = 20;
	
	private QGridLayout layout = new QGridLayout();
	private QHBoxLayout inputLayout = new QHBoxLayout();
	private QLineEdit lineEdit = new QLineEdit();
	private QWidget contentWidget = new QWidget();
	private QWidget inputWidget = new QWidget();
	private QPushButton hideButton;
	
	private QWidget embeddedWidget;
	
	private double inletMarginX =  5 + Inlet.sizeRect.width();
	private double inletMarginY = -5 - Inlet.sizeRect.height();
	
	private double outletMarginX =  5 + Inlet.sizeRect.width();
	private double outletMarginY = 0;
	
	public Signal1<Outlet> connectionDragStarted = new Signal1<Outlet>();
	public Signal1<Outlet> connectionDragAccepted = new Signal1<Outlet>();
	public Signal2<PatchNodeItem, QPointF> patchNodeItemDraggingAtScenePos = new Signal2<PatchNodeItem, QPointF>();
	public Signal0 geometryChanged = new Signal0();
	
	private LinkedList<Inlet> inlets = new LinkedList<Inlet>();
	private LinkedList<Outlet> outlets = new LinkedList<Outlet>(); 
	
	private PatchNodeTab nodeTab;
	
	public PatchNodeItem() {
		super();

		this.setWindowFrameMargins(marginSize, marginSize, marginSize, marginSize);
		this.setFlag(GraphicsItemFlag.ItemIsMovable, true);
		this.setFlag(GraphicsItemFlag.ItemIsSelectable, true);

		inputWidget.setMaximumHeight(50);
		inputWidget.setLayout(inputLayout);
		inputLayout.addWidget(lineEdit);

		contentWidget.setLayout(layout);
		
		layout.addWidget(inputWidget);
		layout.setSizeConstraint(SizeConstraint.SetMinAndMaxSize);
		
		
		addInlet(QColor.green);
		addInlet(QColor.blue);
		
		addOutlet(QColor.green);
		addOutlet(QColor.blue);
		
		super.setWidget(contentWidget);
		adjustSize();
		
		nodeTab = new PatchNodeTab(this, new QRectF(0,0,tabWidth, this.boundingRect().height()-3*marginSize-marginSize));
		nodeTab.setPos(-tabWidth-marginSize,marginSize);
		nodeTab.patchNodeTabDraggingAtScenePos.connect(this,"patchNodeItemDraggingAtScenePos(QPointF)");
		
		
		this.setZValue(normalZLevel);
	}
	
	private void patchNodeItemDraggingAtScenePos(QPointF pos) {
		patchNodeItemDraggingAtScenePos.emit(this, pos);
	}
	
	public void updateNodeTabGeometry() {
		nodeTab.setRect(new QRectF(0,0,tabWidth, this.boundingRect().height()-3*marginSize-marginSize));
	}
	
	public void mousePressEvent(QGraphicsSceneMouseEvent event) {
		super.mousePressEvent(event);
	}
	
	public void resizeEvent(QGraphicsSceneResizeEvent event) {
		double yPos = outletMarginY+boundingRect().bottom();
		
		for(Inlet outlet: outlets) {
			outlet.setPos(outlet.pos().x(),yPos);
		}
		
		super.resizeEvent(event);
		if(nodeTab != null) {
			updateNodeTabGeometry();
		}
		
		geometryChanged.emit();
	}
	
	protected Inlet addInlet(QColor color) {
		Inlet inlet = new Inlet(this, color);
		
		double lastXInletPos = -inletMarginX;
		
		for(Inlet i: inlets) {
			lastXInletPos = i.pos().x();
		}
		
		inlets.add(inlet);
		
		inlet.setPos(lastXInletPos+inletMarginX, inletMarginY);
		
		inlet.dragAccepted.connect(connectionDragAccepted);
		
		
		return inlet;
	}

	protected Inlet addOutlet(QColor color) {
		Outlet outlet = new Outlet(this, color);
		
		double lastXInletPos = -inletMarginX;
		
		for(Outlet o: outlets) {
			lastXInletPos = o.pos().x();
		}
		
		outlets.add(outlet);
		
		outlet.setPos(lastXInletPos+outletMarginX, outletMarginY+boundingRect().bottom());
		
		outlet.dragStarted.connect(connectionDragStarted);
		
		return outlet;
	}

	
	public void hideEmbeddedWidget(boolean hide) {
		if(embeddedWidget != null && hide) {
			embeddedWidget.hide();
			adjustSize();
			this.setZValue(normalZLevel);
		} else {
			embeddedWidget.show();
			adjustSize();
			this.setZValue(raisedZLevel);
		}
	}
	
	public void embedWidget(QWidget w) {
		if(inputLayout.children().size() < 2) {
			hideButton = new QPushButton("compact");
			hideButton.setCheckable(true);
			hideButton.clicked.connect(this,"hideButtonClicked()");
			hideButton.setMaximumWidth(120);
			hideButton.setMaximumHeight(30);
			
			inputLayout.addWidget(hideButton);
		}
		
		layout.addWidget(w);
		this.adjustSize();
		
		embeddedWidget = w;
		
		this.setZValue(raisedZLevel);
	}
	
	private void hideButtonClicked() {
		hideEmbeddedWidget(hideButton.isChecked());
	}
	
	public void paint(QPainter painter, QStyleOptionGraphicsItem option, QWidget widget ) {		
	    QBrush color = new QBrush(new QColor(0, 0, 0, 64));
	    painter.setBrush(QColor.red);
	    painter.drawRoundedRect(boundingRect(), 2*marginSize, 2*marginSize);
	    
	    super.paint(painter, option, widget);
	}



}
