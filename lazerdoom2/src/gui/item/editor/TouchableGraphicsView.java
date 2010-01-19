package gui.item.editor;

import java.awt.Paint;
import java.util.LinkedList;
import java.util.List;

import sceneitems.Util;
import sequencer.Sequencer;
import sparshui.common.Event;
import sparshui.common.messages.events.DeleteEvent;
import sparshui.common.messages.events.DragEvent;
import sparshui.common.messages.events.ExtendedGestureEvent;
import sparshui.common.messages.events.TapEvent;
import sparshui.common.messages.events.TouchEvent;

import com.trolltech.qt.core.QPoint;
import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRect;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.Qt.ScrollBarPolicy;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsEllipseItem;
import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QPainter.RenderHint;
import com.trolltech.qt.opengl.QGLWidget;

import edu.uci.ics.jung.graph.util.Pair;
import gui.item.EditorCursor;
import gui.item.SequenceConnection;
import gui.item.SequenceItem;
import gui.item.SequencePlayerItem;
import gui.item.SynthConnection;
import gui.item.SynthesizerItem;
import gui.item.TouchPointCursor;
import gui.multitouch.TouchItemInterface;
import gui.multitouch.TouchableGraphicsItem;
import gui.scene.editor.BaseSequenceScene;
import gui.scene.editor.EditorScene;
import gui.view.SequencerView;

public class TouchableGraphicsView extends QGraphicsView implements TouchItemInterface {
	private TouchItemInterface parent = null;
	private int id = Util.getGroupID();
	private LinkedList<Integer> allowedGestures = new LinkedList<Integer>();
	private boolean enableTouchEvents = true;
	private TouchableEditor editor;
	
	private double defaultHorizontalScale = 1.0;//(double)350/ (double)(Sequencer.PPQ*4);
	private double verticalScale = 1.0;
	private double horizontalScale = defaultHorizontalScale;
	
	private EditorScene currentEditorScene = null;
	
	/*public TouchableGraphicsView() {
		System.out.println("######################################################################################################");
		this.setHorizontalScrollBarPolicy(ScrollBarPolicy.ScrollBarAlwaysOff);
		this.setVerticalScrollBarPolicy(ScrollBarPolicy.ScrollBarAlwaysOff);
	}*/
	
	double currentCenterX = 400;
	double currentCenterY = 0;
	
	double currentZoomX = 1.0;
	double currentZoomY = 1.0;
	
	public void centerAndInvalidate() {
		this.setSceneRect(this.scene().sceneRect());
		this.scene().invalidate();
		this.resetMatrix();
		this.centerOn(currentCenterX, currentCenterY);
		this.scale(horizontalScale, verticalScale);
		this.update();
		this.repaint();
		//System.err.println("WHAAAT");
	}
	
	public void setEditorScene(EditorScene scene) {
		this.currentEditorScene = scene;
		this.verticalScale = 1.0;

		//System.err.println("dsddsd "+scene);
		//FXIME: quick hack, add this properly to the basesequencescene
		if(scene instanceof BaseSequenceScene) {
			this.horizontalScale = 0.4;
		} else {
			this.horizontalScale = defaultHorizontalScale;
		}
		
		//this.scale(2.0,2.0);
		this.setScene(scene);
		//QGraphicsEllipseItem ei = new QGraphicsEllipseItem();
		//scene.addItem(ei);
		//ei.setPos(100, 0);
		this.setSceneRect(scene.sceneRect());
		//System.out.println(scene.sceneRect()+"");
		//this.centerOn(5000,0);
		this.update();
		//this.scroll(100, 100);
		currentCenterX = 400;
		currentCenterY = 0;
		
		this.resetMatrix();
		//this.scale(currentZoomX, currentZoomY);
		
		this.scene().invalidate();
		//this.centerOn(currentCenterX, currentCenterY);
		//this.scale(horizontalScale, verticalScale);
		
		//this.zoomTo(0.5, 2.456);
		
		this.repaint();
		this.updateEditorCaption();
		
		//this.scene().invalidate();
		this.centerOn(currentCenterX, currentCenterY);
		this.scale(horizontalScale, verticalScale);
		
/* else {
			this.scale(horizontalScale, verticalScale);
		}*/

	}
	
	public void zoomTo(double x, double y) {
		//y = x;
		//his.scale(1.0/currentZoomX, 1.0/currentZoomY);
		if((x >= 0.25 && y >= 0.25) && (x <= 3.0 && y <= 3.0)) {
			this.resetMatrix();
			this.scale(x, y);
			System.out.println("zx zy "+x+" "+y);

			this.verticalScale = x;
			this.horizontalScale = y;

			this.updateEditorCaption();
		}
	}
	
	private void updateEditorCaption() {
		if(this.currentEditorScene != null) {
			this.editor.updateInfoCaption(this.currentEditorScene.getHorizontalGridCaption(this.horizontalScale)+" "+this.currentEditorScene.getVerticalGridCaption(this.verticalScale));
		}
	}
	
	public void scrollBy(double x, double y) {
		/*this.scrollContentsBy((int)x, (int)y);
		this.scroll((int)x, (int)y);
		System.out.println("WHWHWHWH wHWH WHW whw lkj lkj lkkj piu ");
		*/
		if(currentCenterX+x > 0) {
			this.currentCenterX = this.currentCenterX +x;
		}
		this.currentCenterY = this.currentCenterY +y;
		this.centerOn(this.currentCenterX+1000, this.currentCenterY);
		System.err.println("cx cy" + this.currentCenterX+" "+this.currentCenterY);
		this.update();
	}
	
	@Override
	public void drawBackground(QPainter painter, QRectF rect) {
		painter.setBrush(QColor.white);
		painter.setPen(QPen.NoPen);
		painter.drawRect(rect);
		System.out.println("drawBG !!!!!");
		
		if(currentEditorScene != null) {
			this.currentEditorScene.drawHorizontalGrid(painter, rect, this.horizontalScale);
			this.currentEditorScene.drawVerticalGrid(painter, rect, this.verticalScale);
		}
	}
	
	/*@Override
	public void drawForeground(QPainter painter, QRectF rect) {
		if(currentEditorScene != null) {
			this.currentEditorScene.drawVerticalGridCaption(painter, rect, this.mapToScene(this.viewport().width()-100, this.viewport().height()-20), this.verticalScale);
			this.currentEditorScene.drawHorizontalGridCaption(painter, rect, this.mapToScene(100, 50), this.verticalScale);
		}
	}*/
	
	public TouchableGraphicsView(TouchableEditor editor) {
		/*allowedGestures.add(sparshui.gestures.GestureType.TOUCH_GESTURE.ordinal());
		allowedGestures.add(sparshui.gestures.GestureType.DELETE_GESTURE.ordinal());*/
		//this.setScene(new QGraphicsScene());
		this.setupViewport(new QGLWidget());
		//this.setCacheMode(CacheModeFlag.CacheNone);
		//this.setRenderHint(RenderHint.Antialiasing);
		this.setViewportUpdateMode(ViewportUpdateMode.FullViewportUpdate);
		this.editor = editor;
	}
	
	public Pair<Object> getGroupIDViewCoordinates(QPointF pos) {
		/*QPointF itemCoordinates = editor.mapFromScene(pos);
		QPointF posM = this.mapToScene((int)itemCoordinates.x(), (int)itemCoordinates.y());
		System.out.println(pos+" "+posM+" "+itemCoordinates);
		QGraphicsItemInterface item;
		
		if((item = editor.getCurrentEditor().getScene().itemAt(posM)) != null && item instanceof TouchableGraphicsItem) {
			return new Pair<Object>(((TouchableGraphicsItem) item).getGroupID(), item);
		}*/
		
		return new Pair<Object>(this.id, this);
	}
	
	public void enableTouchEvents(boolean enableTouchEvents) {
		this.enableTouchEvents = enableTouchEvents;
	}
	
	@Override
	public List<Integer> getAllowedGestures() {
		return this.editor.getCurrentEditor().getAllowedGestures();
	}

	@Override
	public int getGroupID() {
		return this.id;
	}

	private int vGridRes = -1;
	private int hGridRes = -1;
	
	private double oldVSc = -12223.0;
	private double oldHSc = -12222.0;
	private EditorScene oldEd = null;
	
	@Override
	public boolean processEvent(Event event) {
		if(oldVSc != this.verticalScale || oldEd != this.currentEditorScene) {
			vGridRes = currentEditorScene.verticalSnapToGridResolution(this.verticalScale);
			oldVSc = this.verticalScale;
		}
		
		//System.out.println(this.currentEditorScene);
		
		if(oldHSc != this.horizontalScale || oldEd != this.currentEditorScene) {
			hGridRes = currentEditorScene.horizontalSnapToGridResolution(this.horizontalScale);
			System.out.println(hGridRes+" HGRID " + currentEditorScene.horizontalSnapToGridResolution(1.0));
			oldHSc = this.horizontalScale;
		}
		
		if(oldEd != this.currentEditorScene) {
			oldEd = this.currentEditorScene;
		}
		
		if(event instanceof ExtendedGestureEvent) {
			ExtendedGestureEvent e = (ExtendedGestureEvent) event;
			QPointF itemCoordinates = editor.mapFromScene(e.getSceneLocation());
	
			int xPos = (int)itemCoordinates.x();
			int yPos = (int)itemCoordinates.y();
	
			e.setSceneLocation(this.mapToScene(xPos, yPos));
			if(e instanceof DeleteEvent) {
				if(((DeleteEvent) e).getCrossPoint() != null) {
					QPointF crossPoint = editor.mapFromScene(((DeleteEvent)e).getSceneCrossPoint());
					((DeleteEvent) e).setSceneCrossPoint(this.mapToScene((int)crossPoint.x(), (int)crossPoint.y()));
				}
			}
			
			this.editor.getCurrentEditor().handleExtendedGestureEvent(e, vGridRes, hGridRes);
			this.update();

		}  
		
		if(event instanceof TouchEvent) {
			TouchEvent e = (TouchEvent) event;
			QPointF itemCoordinates = editor.mapFromScene(e.getSceneLocation());
			e.setSceneLocation(this.mapToScene((int)itemCoordinates.x(), (int)itemCoordinates.y()));
			this.editor.getCurrentEditor().handleTouchEvent(e, vGridRes, hGridRes);
			this.update();
			//System.out.println("WHA?");
		} 
		/*if(event instanceof DragEvent) {
			DragEvent de = (DragEvent) event;
			
			TouchableGraphicsItem tii;
			
			if((tii = (TouchableGraphicsItem) de.getSource()) != null && tii.getParent() == this) {
				System.out.println(">***");
				System.out.println(de);
				System.out.println(de.getTouchID());
				System.out.println(de.isDrop());
				System.out.println("***>");
			}
		}*/
		/*this.scene().invalidate();
		this.repaint();
		this.viewport().repaint();*/
		return true;
	}
}
