package patch.scene;

import java.util.LinkedList;
import java.util.List;

import org.mozilla.javascript.tools.debugger.Main;

import patch.view.PatchView;
import patch.view.item.Inlet;
import patch.view.item.NewConnector;
import patch.view.item.NewPatchCord;
import patch.view.item.NewPatchNodeItem;
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
import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QGraphicsLineItem;
import com.trolltech.qt.gui.QGraphicsProxyWidget;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.gui.QGraphicsWidget;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QRadioButton;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QGraphicsItem.GraphicsItemFlag;
import com.trolltech.qt.gui.QPainter.RenderHint;

public class PatchScene extends QGraphicsScene {
   
	private boolean connectionDragStarted = false;
	private PatchCord patchCord;
	private QPointF dragStart = null;
	
	private LinkedList<NewConnector> selectedOutputs = new LinkedList<NewConnector>();
	private LinkedList<NewConnector> selectedInputs = new LinkedList<NewConnector>();
	
	private Outlet connectionStartDragOutlet;
	
	public static void main(String args[]) {
        QApplication.initialize(args);
        PatchScene patchScene = new PatchScene();
        
        PatchView view = new PatchView(patchScene);
        view.setCacheMode(QGraphicsView.CacheModeFlag.CacheBackground);
        view.setRenderHint(QPainter.RenderHint.Antialiasing);
        view.setTransformationAnchor(QGraphicsView.ViewportAnchor.AnchorUnderMouse);
        view.setResizeAnchor(QGraphicsView.ViewportAnchor.AnchorViewCenter);
        
        view.setMinimumSize(800,800);
        view.show();
        
        //patchScene.addNode(new QPointF(200,200));
        //patchScene.addNode(new QPointF(400,400));
        
        patchScene.addItem(new NewPatchNodeItem());
        patchScene.addItem(new NewPatchNodeItem());
        
        //NewPatchCord patch = new NewPatchCord();
        //patchScene.addItem(patch);
        //patch.setPos(100,-200);
      
       // PatchNodeTab pt = new PatchNodeTab(new QRectF(0,0,50,200));
        //pt.setPos(400,400);
        //patchScene.addItem(pt);
        
        view.createConnectionWithCurrentSelection.connect(patchScene,"createConnectionWithCurrentSelection()");
        
        QApplication.exec();
    }
	
	public PatchScene() {
        setSceneRect(-200, -200, 400, 400);
	}
	
	private void createConnectionWithCurrentSelection() {
		List<QGraphicsItemInterface> items = selectedItems();
		
		selectedInputs.clear();
		selectedOutputs.clear();
		
		for(QGraphicsItemInterface item: items) {
			if(item instanceof NewConnector ) {
				if(((NewConnector) item).isOutput()) {
					selectedOutputs.add(((NewConnector) item));
				} else {
					selectedInputs.add(((NewConnector) item));
				}
			}
		}
		
		if(selectedInputs.size() != 0 && selectedOutputs.size() != 0) {
			this.addItem(new NewPatchCord(selectedInputs.getFirst(), selectedOutputs.getFirst()));
		}
	}
	
	private void connectionDragStarted(Outlet item) {
		connectionDragStarted = true;
		
		dragStart = item.scenePos();
		
		System.out.println("ksdflksdfjkl");
		
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
	
	private void mouseReleasedAtScenePos(QPointF pos) {
		if(connectionDragStarted && patchCord != null) {
			this.removeItem(patchCord);
			patchCord = null;
			connectionDragStarted = false;
		}
	}
	
	public void setView(PatchView view) {
        view.dragAtScenePos.connect(this, "dragAtScenePosition(QPointF)");
        view.mouseReleasedAtScenePos.connect(this,"mouseReleasedAtScenePos(QPointF)");
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
