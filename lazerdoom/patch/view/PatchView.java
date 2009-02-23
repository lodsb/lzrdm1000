package patch.view;

import patch.scene.PatchScene;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.gui.QDragMoveEvent;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.gui.QMouseEvent;
import com.trolltech.qt.opengl.QGLWidget;

public class PatchView extends QGraphicsView {
	public Signal1<QPointF> mouseAtScenePos = new Signal1<QPointF>();
	public Signal1<QPointF> dragAtScenePos = new Signal1<QPointF>(); 
	
	public PatchView(PatchScene patchScene) {
		super(patchScene);
		//this.setupViewport(new QGLWidget());
	}

	public void mouseMoveEvent(QMouseEvent event) {
		mouseAtScenePos.emit(this.mapToScene(event.pos()));
		super.mouseMoveEvent(event);
	} 
	
	public void dragMoveEvent(QDragMoveEvent event) {
		dragAtScenePos.emit(this.mapToScene(event.pos()));
		super.dragMoveEvent(event);
	}
}
