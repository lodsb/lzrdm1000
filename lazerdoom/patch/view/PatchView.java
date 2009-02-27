package patch.view;

import patch.scene.PatchScene;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QDragMoveEvent;
import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QGradient;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.gui.QKeyEvent;
import com.trolltech.qt.gui.QLinearGradient;
import com.trolltech.qt.gui.QMouseEvent;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QRadialGradient;
import com.trolltech.qt.opengl.QGLWidget;

public class PatchView extends QGraphicsView {
	public Signal1<QPointF> mouseAtScenePos = new Signal1<QPointF>();
	public Signal1<QPointF> mouseReleasedAtScenePos = new Signal1<QPointF>();
	public Signal1<QPointF> dragAtScenePos = new Signal1<QPointF>(); 
	
	public Signal0 createConnectionWithCurrentSelection = new Signal0();
	
	private QColor bgColor1 = new QColor(38,50,62);
	private QColor bgColor2 = new QColor(bgColor1.lighter(155));
	
	public PatchView(PatchScene patchScene) {
		super(patchScene);
		patchScene.setView(this);
		//this.setupViewport(new QGLWidget());
	}

	public void mouseMoveEvent(QMouseEvent event) {
		mouseAtScenePos.emit(this.mapToScene(event.pos()));
		super.mouseMoveEvent(event);
	} 
	
	public void mouseReleaseEvent(QMouseEvent event) {
		mouseReleasedAtScenePos.emit(mapToScene(event.pos()));
		super.mouseReleaseEvent(event);
	}
	
	public void dragMoveEvent(QDragMoveEvent event) {
		dragAtScenePos.emit(this.mapToScene(event.pos()));
		super.dragMoveEvent(event);
	}
	
	/*placeholder for keyboard management*/
	public void keyPressEvent(QKeyEvent e) {
		if(e.text().equals("c")) {
			createConnectionWithCurrentSelection.emit();
			e.accept();
		} 
		
		super.keyPressEvent(e);
	} 
	
	
    protected void drawBackground(QPainter painter, QRectF rect) {
     

        // Fill
        QRadialGradient gradient = new QRadialGradient(0,0,rect.width());
        gradient.setColorAt(0, bgColor2);
        gradient.setColorAt(1, bgColor1);
        painter.fillRect(rect, new QBrush(gradient));
    }
	
}
