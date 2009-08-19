package GUI.View;

import com.trolltech.qt.core.QPoint;
import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.QEvent.Type;
import com.trolltech.qt.core.Qt.KeyboardModifier;
import com.trolltech.qt.core.Qt.MouseButton;
import com.trolltech.qt.core.Qt.MouseButtons;
import com.trolltech.qt.gui.QGraphicsItem;
import com.trolltech.qt.gui.QGraphicsProxyWidget;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.gui.QGraphicsSceneMouseEvent;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.gui.QMouseEvent;
import com.trolltech.qt.gui.QPushButton;

public class SynthesizerView extends QGraphicsView {
	private QGraphicsScene scene;
	private QGraphicsProxyWidget item;
	
	public SynthesizerView() {
		scene = new QGraphicsScene();
		this.setScene(scene);
		QPushButton widget = new QPushButton("TEST");
		item = scene.addWidget(widget);
		item.setPos(200,200);
	}
	
	public void mousePressEvent(QMouseEvent event) {
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			QMouseEvent e = new QMouseEvent(Type.MouseButtonPress, new QPoint(421,339), MouseButton.LeftButton, null, KeyboardModifier.NoModifier);
			System.out.println("click");
			super.mousePressEvent(e);
		}
		//System.out.println(event+" "+event.pos());
		/*System.out.println(scene.itemAt(this.mapToScene(event.pos().x(), event.pos().y())));
		System.out.println(item);
		if(((QGraphicsProxyWidget) scene.itemAt(this.mapToScene(event.pos().x(), event.pos().y()))) == item) {
			System.out.println("check!!");
			
		}*/
		//super.mousePressEvent(event);
	}
}
