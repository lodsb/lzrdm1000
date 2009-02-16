package sequence.view;

import sequence.GenericSequenceController;
import sequence.scene.AbstractGenericSequenceScene;
import sequence.scene.types.DoublePointSequence;
import sequence.scene.types.DoublePointSequenceScene;
import sequence.view.item.types.DoublePoint;
import sequence.view.item.types.Node;

import com.trolltech.qt.QSignalEmitter.Signal1;
import com.trolltech.qt.core.QFile;
import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.svg.QGraphicsSvgItem;
import com.trolltech.qt.svg.QSvgRenderer;

public class GenericSequenceViewWidget<P,T> extends QWidget implements GenericSequenceViewInterface<P, T> {

	private GenericSequenceGraphicsView gView;
	private HorizontalRuler<P> hRuler = null;
	private VerticalRuler<P> vRuler   = null;
	
	private double zoomFactorH = 1.0;
	private double zoomFactorV = 1.0;
	
	
	private AbstractGenericSequenceScene<P, T> sequenceScene = null;
	
	private QGridLayout layout;
	
	public GenericSequenceViewWidget(QWidget parent) {
		super(parent);
		
		layout = new QGridLayout();
		
		gView = new GenericSequenceGraphicsView(this);
		
		layout.setColumnMinimumWidth(0, 20);
		layout.setRowMinimumHeight(0, 20);
		layout.addWidget(gView, 1,1);
				
		this.setLayout(layout);
		
		
		
		gView.createItemAtScenePos.connect(this, "createItemAtScenePos(QPointF)");
		gView.mouseAtScenePos.connect(this, "mouseAtScenePos(QPointF)");
		
		gView.viewChanged.connect(this, "viewUpdated()");
	}
	
	protected void viewUpdated() {
		QRectF viewBounds = gView.visibleRect();
		
		if(vRuler != null) {
			vRuler.updateVisibleRange(viewBounds.top(), viewBounds.bottom());
		}
		
		if(hRuler != null) {
			hRuler.updateVisibleRange(viewBounds.left(), viewBounds.right());
		}
		
	}
	
	private void createItemAtScenePos(QPointF pos) {
		if(sequenceScene != null) {
			sequenceScene.createNewEntryFromScenePos(pos);
		}
	}
	
	private void mouseAtScenePos(QPointF pos) {
		// TODO add info widget with mapping coordinates
	}
	
	public void setSequenceScene(AbstractGenericSequenceScene<P,T> scene) {
		sequenceScene = scene;
		
		gView.setScene(scene);
		
		scene.changed.connect(this, "viewUpdated()");
	}
	
	@Override
	public void addHorizontalRuler(HorizontalRuler<P> ruler) {
		if(hRuler != null) {
			layout.removeWidget(hRuler);
		}
		
		hRuler = ruler;
		
		layout.addWidget(hRuler, 0,1);
		
		this.viewUpdated();
		
		//layout.addWidget(new QLabel("sdfsdf"),0,0);
	}
	@Override
	public void addVerticalRuler(VerticalRuler<P> ruler) {
		if(vRuler != null) {
			layout.removeWidget(vRuler);
		}
		
		vRuler = ruler;
		layout.addWidget(vRuler, 1,0);

		this.viewUpdated();
	}

	@Override
	public void decZoomH() {
		if(hRuler != null) {
			hRuler.decZoomH();
		}
		
		setZoom(zoomFactorH/2.0, zoomFactorV);
	}

	@Override
	public void decZoomV() {
		if(vRuler != null) {
			vRuler.decZoomV();
		}		
		
		setZoom(zoomFactorH, zoomFactorV/2.0);
	}

	@Override
	public void incZoomH() {
		if(hRuler != null) {
			hRuler.incZoomH();
		}
		
		setZoom(zoomFactorH*2.0, zoomFactorV);
	}

	@Override
	public void incZoomV() {
		if(vRuler != null) {
			vRuler.incZoomV();
		}		
	
		setZoom(zoomFactorH, zoomFactorV*2.0);
	}

	@Override
	public void resetZoomH() {
		if(hRuler != null) {
			hRuler.resetZoomH();
		}
		
		setZoom(1.0, zoomFactorV);
	}

	@Override
	public void resetZoomV() {
		if(vRuler != null) {
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
