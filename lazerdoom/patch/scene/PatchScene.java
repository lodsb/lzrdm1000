package patch.scene;

import org.mozilla.javascript.tools.debugger.Main;

import patch.view.PatchView;
import patch.view.item.Inlet;
import patch.view.item.Outlet;
import patch.view.item.PatchCord;
import patch.view.item.PatchNodeItem;
import patch.view.item.PatchNodeTab;

import sequence.view.form.CursorToolBox;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRect;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.Qt.WindowFlags;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsEllipseItem;
import com.trolltech.qt.gui.QGraphicsItem;
import com.trolltech.qt.gui.QGraphicsLineItem;
import com.trolltech.qt.gui.QGraphicsProxyWidget;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.gui.QGraphicsWidget;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QRadioButton;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QGraphicsItem.GraphicsItemFlag;
import com.trolltech.qt.gui.QPainter.RenderHint;

public class PatchScene extends QGraphicsScene {
   
	private boolean connectionDragStarted = false;
	private PatchCord patchCord;
	private QPointF dragStart = null;
	
	private Outlet connectionStartDragOutlet;
	
	public static void main(String args[]) {
        QApplication.initialize(args);
        PatchScene patchScene = new PatchScene();
        
        PatchView view = new PatchView(patchScene);
        
        view.dragAtScenePos.connect(patchScene, "dragAtScenePosition(QPointF)");
        
        
        view.setMinimumSize(800,800);
        view.show();
        view.setRenderHint(RenderHint.Antialiasing);
        
        patchScene.addNode(new QPointF(200,200));
        patchScene.addNode(new QPointF(400,400));
      
       // PatchNodeTab pt = new PatchNodeTab(new QRectF(0,0,50,200));
        //pt.setPos(400,400);
        //patchScene.addItem(pt);
        
        QApplication.exec();
    }
	
	private void connectionDragStarted(Outlet item) {
		connectionDragStarted = true;
		
		dragStart = item.scenePos();
		
		patchCord = new PatchCord();
		patchCord.setPos(dragStart.x()+item.boundingRect().width()/2, dragStart.y()+item.boundingRect().height()/2);
		this.addItem(patchCord);
		
		connectionStartDragOutlet = item;
		
	}
	
	private void connectionDragAccepted(Inlet item) {
		if(connectionStartDragOutlet != null && patchCord != null) {
			patchCord.setInletAndOutlet(connectionStartDragOutlet, item);
		}
		
		connectionDragStarted = false;
		connectionStartDragOutlet = null;
		patchCord = null;
	}
	
	private void dragAtScenePosition(QPointF pos) {
		if(connectionDragStarted && patchCord != null) {
			patchCord.setDestination(pos);
		}
	}
	
	private void patchNodeDraggingAtScenePos(PatchNodeItem item, QPointF pos) {
		item.setPos(pos);
	}
	
	public void addNode(QPointF pos) {
        PatchNodeItem w = new PatchNodeItem();
        
        w.connectionDragStarted.connect(this, "connectionDragStarted(Outlet)");
        w.connectionDragAccepted.connect(this, "connectionDragAccepted(Inlet)");
        w.patchNodeItemDraggingAtScenePos.connect(this, "patchNodeDraggingAtScenePos(PatchNodeItem, QPointF)");
    
        //view.setBackgroundBrush(new QBrush(QColor.black));
        w.embedWidget(new CursorToolBox());
        w.setPos(pos);
        this.addItem(w);
	}

}
