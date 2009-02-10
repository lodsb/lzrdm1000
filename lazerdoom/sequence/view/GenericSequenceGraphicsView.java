package sequence.view;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.gui.QKeyEvent;
import com.trolltech.qt.gui.QMouseEvent;

public class GenericSequenceGraphicsView extends QGraphicsView {
	private GenericSequenceViewWidget parentWidget;
	
	public Signal1<QPointF> createItemAtScenePos = new Signal1<QPointF>();
	public Signal1<QPointF> mouseAtScenePos = new Signal1<QPointF>();
	
	GenericSequenceGraphicsView(GenericSequenceViewWidget parent) {
		super(parent);
		
		parentWidget = parent;
	}
	
	protected void mouseMoveEvent(QMouseEvent e) {
		mouseAtScenePos.emit(this.mapToScene(e.pos()));
		super.mouseMoveEvent(e);
	}
	
	protected void mouseDoubleClickEvent(QMouseEvent e) {
		createItemAtScenePos.emit(this.mapToScene(e.pos()));
		super.mouseDoubleClickEvent(e);
	}
	
	protected void mousePressEvent(QMouseEvent e) {
		super.mousePressEvent(e);
	}
	
	protected void mouseReleaseEvent(QMouseEvent e) {
		super.mouseReleaseEvent(e);
	}

	
	protected void keyPressEvent(QKeyEvent arg__1) {
		
	}
}
