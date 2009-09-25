package GUI.Item.Editor;

import java.awt.Paint;
import java.util.LinkedList;
import java.util.List;

import sparshui.common.Event;
import sparshui.common.messages.events.DeleteEvent;
import sparshui.common.messages.events.DragEvent;
import sparshui.common.messages.events.ExtendedGestureEvent;
import sparshui.common.messages.events.TouchEvent;
import GUI.Item.EditorCursor;
import GUI.Item.SequenceConnection;
import GUI.Item.SequenceItem;
import GUI.Item.SequencePlayerItem;
import GUI.Item.SynthConnection;
import GUI.Item.SynthesizerItem;
import GUI.Item.TouchPointCursor;
import GUI.Multitouch.TouchItemInterface;
import GUI.Multitouch.TouchableGraphicsItem;
import GUI.Scene.Editor.EditorScene;
import GUI.View.SequencerView;
import SceneItems.Util;

import com.trolltech.qt.core.QPoint;
import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRect;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.gui.QColor;
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

public class TouchableGraphicsView extends QGraphicsView implements TouchItemInterface {
	private TouchItemInterface parent = null;
	private int id = Util.getGroupID();
	private LinkedList<Integer> allowedGestures = new LinkedList<Integer>();
	private boolean enableTouchEvents = true;
	private TouchableEditor editor;
	
	private double verticalScale = 1.0;
	private double horizontalScale = 1.0;
	
	private EditorScene currentEditorScene = null;
	
	public void setEditorScene(EditorScene scene) {
		this.currentEditorScene = scene;
		this.verticalScale = 1.0;
		this.horizontalScale = 1.0;
		//this.scale(2.0,2.0);
		this.setScene(scene);
		this.setSceneRect(scene.sceneRect());
		this.update();
		
		this.scale(horizontalScale, verticalScale);
	}
	
	@Override
	public void drawBackground(QPainter painter, QRectF rect) {
		painter.setBrush(QColor.white);
		painter.setPen(QPen.NoPen);
		painter.drawRect(rect);
		
		if(currentEditorScene != null) {
			this.currentEditorScene.drawHorizontalGrid(painter, rect, this.horizontalScale);
			this.currentEditorScene.drawVerticalGrid(painter, rect, this.verticalScale);
		}
	}
	
	@Override
	public void drawForeground(QPainter painter, QRectF rect) {
		if(currentEditorScene != null) {
			this.currentEditorScene.drawVerticalGridCaption(painter, rect, this.mapToScene(this.viewport().width()-100, this.viewport().height()-20), this.verticalScale);
			this.currentEditorScene.drawHorizontalGridCaption(painter, rect, this.mapToScene(100, 50), this.verticalScale);
		}
	}
	
	public TouchableGraphicsView(TouchableEditor editor) {
		/*allowedGestures.add(sparshui.gestures.GestureType.TOUCH_GESTURE.ordinal());
		allowedGestures.add(sparshui.gestures.GestureType.DELETE_GESTURE.ordinal());*/
		//this.setScene(new QGraphicsScene());
		this.setupViewport(new QGLWidget((QWidget)null, (QGLWidget)SequencerView.sharedGlWidget));
		//this.setCacheMode(CacheModeFlag.CacheBackground);
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

		}  
		
		if(event instanceof TouchEvent) {
			TouchEvent e = (TouchEvent) event;
			QPointF itemCoordinates = editor.mapFromScene(e.getSceneLocation());
			e.setSceneLocation(this.mapToScene((int)itemCoordinates.x(), (int)itemCoordinates.y()));
			this.editor.getCurrentEditor().handleTouchEvent(e, vGridRes, hGridRes);
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
		//this.update();
		return true;
	}
}
