package sequence.view;

import java.util.LinkedList;

import sequence.GenericSequenceController;
import sequence.scene.AbstractGenericSequenceScene;
import sequence.scene.types.DoublePointSequence;
import sequence.scene.types.DoublePointSequenceScene;
import sequence.view.form.EditWidget;
import sequence.view.form.ViewInfo;
import sequence.view.item.types.DoublePoint;
import sequence.view.item.types.Node;

import com.trolltech.qt.QSignalEmitter.Signal1;
import com.trolltech.qt.core.QFile;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.opengl.QGLWidget;
import com.trolltech.qt.svg.QGraphicsSvgItem;
import com.trolltech.qt.svg.QSvgRenderer;

public class GenericSequenceViewWidget<P,T> extends QWidget implements GenericSequenceViewInterface<P, T> {

	private GenericSequenceGraphicsView gView;
	private LinkedList<HorizontalRuler<P>> hRulers = new LinkedList<HorizontalRuler<P>>();
	private LinkedList<VerticalRuler<P>> vRulers   = new LinkedList<VerticalRuler<P>>();
	
	private double zoomFactorH = 1.0;
	private double zoomFactorV = 1.0;
	
	public Signal1<QPointF> mouseAtScenePos = new Signal1<QPointF>();
	public Signal1<QPointF> placeCursorAtScenePos = new Signal1<QPointF>();
	
	private AbstractGenericSequenceScene<P, T> sequenceScene = null;
	
	private QGridLayout layout = new QGridLayout();
	
	private QWidget hRulerWidget = new QWidget(this);
	private QWidget vRulerWidget = new QWidget(this);
	private QVBoxLayout hRulerLayout = new QVBoxLayout();
	private QHBoxLayout vRulerLayout = new QHBoxLayout();
	
	private int currentVRulerMinWidth = 0;
	private int currentHRulerMinHeight = 0;
	
	private ViewInfo viewInfoWidget = new ViewInfo(this);
	private EditWidget editWidget = new EditWidget(this);
	
	private CursorInterface<P> cursor;
	
	public GenericSequenceViewWidget(QWidget parent) {
		super(parent);
		
		layout.setColumnMinimumWidth(0, 20);
		layout.setRowMinimumHeight(0, 30);
				
		this.setLayout(layout);
		hRulerWidget.setLayout(hRulerLayout);
		hRulerLayout.setContentsMargins(0,0,0,0);
		vRulerWidget.setLayout(vRulerLayout);
		vRulerLayout.setContentsMargins(0,0,0,0);
		
		gView = new GenericSequenceGraphicsView(this);
		layout.addWidget(gView, 1,1);
		gView.createItemAtScenePos.connect(this, "createItemAtScenePos(QPointF)");
		gView.placeCursorAtScenePos.connect(placeCursorAtScenePos);
		gView.mouseAtScenePos.connect(this, "mouseAtScenePos(QPointF)");
		gView.viewChanged.connect(this, "viewUpdated()");
		
		
		layout.addWidget(editWidget,2,1);
		layout.addWidget(viewInfoWidget,3,1);
	}
	
	public GenericSequenceGraphicsView getGraphicsView() {
		return gView;
	}
	
	protected void placeCursorAtScenePos(QPointF pos) {
		placeCursorAtScenePos.emit(pos);
	} 
	
	public void setCompactView(boolean compact) {
		if(compact) {
			vRulerWidget.hide();
			hRulerWidget.hide();
			viewInfoWidget.hide();
			editWidget.hide();
			
			layout.setRowMinimumHeight(0, 0);
			layout.setColumnMinimumWidth(0, 0);
			
		} else {
			vRulerWidget.show();
			hRulerWidget.show();
			viewInfoWidget.show();
			editWidget.show();
			
			layout.setRowMinimumHeight(0, currentHRulerMinHeight);
			layout.setColumnMinimumWidth(0, currentVRulerMinWidth);
			
		}
	}
	
	public ViewInfo getViewInfoWidget() {
		return viewInfoWidget;
	}
	
	public EditWidget getEditWidget() {
		return editWidget;
	}
	
	protected void viewUpdated() {
		QRectF viewBounds = gView.visibleRect();
		
		for(VerticalRuler<P> vRuler: vRulers) {
			vRuler.updateVisibleRange(viewBounds.top(), viewBounds.bottom());
		}
		
		
		for(HorizontalRuler<P> hRuler: hRulers) {
			hRuler.updateVisibleRange(viewBounds.left(), viewBounds.right());
		}
		
	}
	
	private void createItemAtScenePos(QPointF pos) {
		if(sequenceScene != null) {
			sequenceScene.createNewEntryFromScenePos(pos);
		}
	}
	
	private void mouseAtScenePos(QPointF pos) {
		mouseAtScenePos.emit(pos);
	}
	
	public void setSequenceScene(AbstractGenericSequenceScene<P,T> scene) {
		sequenceScene = scene;
		
		gView.setScene(scene);
		
		scene.changed.connect(this, "viewUpdated()");
	}
	
	@Override
	public void addHorizontalRuler(HorizontalRuler<P> ruler) {
		
		if(hRulers.size() == 0) {
			layout.addWidget(hRulerWidget, 0 ,1);
		}
		hRulers.add(ruler);
		
		int minimumRowHeight = 0;
		
		for(HorizontalRuler<P> hRuler: hRulers) {
			minimumRowHeight += hRuler.sizeHint().height();
		}
		
		layout.setRowMinimumHeight(0, minimumRowHeight);
		
		currentHRulerMinHeight = minimumRowHeight;
		
		hRulerLayout.addWidget(ruler);
		
		this.viewUpdated();
	}
	@Override
	public void addVerticalRuler(VerticalRuler<P> ruler) {
		
		if(vRulers.size() == 0) {
			layout.addWidget(vRulerWidget, 1 ,0);
		}
		vRulers.add(ruler);
		
		int minimumColumnWidth = 0;
		
		for(VerticalRuler<P> vRuler: vRulers) {
			minimumColumnWidth += vRuler.sizeHint().width();
		}
		
		layout.setColumnMinimumWidth(0, minimumColumnWidth);
		
		currentVRulerMinWidth = minimumColumnWidth;
		
		vRulerLayout.addWidget(ruler);
		
		this.viewUpdated();
	}

	@Override
	public void decZoomH() {
		for(HorizontalRuler<P> hRuler: hRulers) {
			hRuler.decZoomH();
		}
		
		setZoom(zoomFactorH/2.0, zoomFactorV);
	}

	@Override
	public void decZoomV() {
		for(VerticalRuler<P> vRuler: vRulers) {
			vRuler.decZoomV();
		}
		
		setZoom(zoomFactorH, zoomFactorV/2.0);
	}

	@Override
	public void incZoomH() {
		for(HorizontalRuler<P> hRuler: hRulers) {
			hRuler.incZoomH();
		}
		
		setZoom(zoomFactorH*2.0, zoomFactorV);
	}

	@Override
	public void incZoomV() {
		for(VerticalRuler<P> vRuler: vRulers) {
			vRuler.incZoomV();
		}
		
		setZoom(zoomFactorH, zoomFactorV*2.0);
	}

	@Override
	public void resetZoomH() {
		for(HorizontalRuler<P> hRuler: hRulers) {
				hRuler.resetZoomH();
		}
		
		setZoom(1.0, zoomFactorV);
	}

	@Override
	public void resetZoomV() {
		for(VerticalRuler<P> vRuler: vRulers) {
			vRuler.resetZoomV();
		}
		
		setZoom(zoomFactorH, 1.0);
		
	}
	
	private void setZoom(double zh, double zv) {
		zoomFactorV = zv;
		zoomFactorH = zh;
		
		this.gView.scale(zh, zv);
	}

}
