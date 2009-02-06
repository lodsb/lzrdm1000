package sequence.view;

import sequence.scene.AbstractGenericSequenceScene;

import com.trolltech.qt.QSignalEmitter.Signal1;
import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QWidget;

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
		
		layout.addWidget(gView, 1,1);
		this.setLayout(layout);
		
		
		gView.createItemAtScenePos.connect(this, "createItemAtScenePos(QPointF)");
		gView.mouseAtScenePos.connect(this, "mouseAtScenePos(QPointF)");
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
	}
	
	@Override
	public void addHorizontalRuler(HorizontalRuler<P> ruler) {
		if(hRuler != null) {
			layout.removeWidget(hRuler);
		}
		
		hRuler = ruler;
		layout.addWidget(hRuler, 0,0);
	}
	@Override
	public void addVerticalRuler(VerticalRuler<P> ruler) {
		if(vRuler != null) {
			layout.removeWidget(vRuler);
		}
		
		vRuler = ruler;
		layout.addWidget(vRuler, 1,0);
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
