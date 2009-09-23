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
import GUI.View.SequencerView;
import SceneItems.Util;

import com.trolltech.qt.core.QPoint;
import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRect;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.gui.QPainter;
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
	
	
	public TouchableGraphicsView(TouchableEditor editor) {
		/*allowedGestures.add(sparshui.gestures.GestureType.TOUCH_GESTURE.ordinal());
		allowedGestures.add(sparshui.gestures.GestureType.DELETE_GESTURE.ordinal());*/
		//this.setScene(new QGraphicsScene());
		this.setupViewport(new QGLWidget((QWidget)null, (QGLWidget)SequencerView.sharedGlWidget));
		this.setCacheMode(CacheModeFlag.CacheBackground);
		//this.setRenderHint(RenderHint.Antialiasing);
		//this.setViewportUpdateMode(ViewportUpdateMode.FullViewportUpdate);
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

	@Override
	public boolean processEvent(Event event) {
		
		if(event instanceof ExtendedGestureEvent) {
			ExtendedGestureEvent e = (ExtendedGestureEvent) event;
			QPointF itemCoordinates = editor.mapFromScene(e.getSceneLocation());
			e.setSceneLocation(this.mapToScene((int)itemCoordinates.x(), (int)itemCoordinates.y()));
			if(e instanceof DeleteEvent) {
				if(((DeleteEvent) e).getCrossPoint() != null) {
					QPointF crossPoint = editor.mapFromScene(((DeleteEvent)e).getSceneCrossPoint());
					((DeleteEvent) e).setSceneCrossPoint(this.mapToScene((int)crossPoint.x(), (int)crossPoint.y()));
				}
			}
			
			this.editor.getCurrentEditor().handleExtendedGestureEvent(e);

		}  
		
		if(event instanceof TouchEvent) {
			TouchEvent e = (TouchEvent) event;
			QPointF itemCoordinates = editor.mapFromScene(e.getSceneLocation());
			e.setSceneLocation(this.mapToScene((int)itemCoordinates.x(), (int)itemCoordinates.y()));
			this.editor.getCurrentEditor().handleTouchEvent(e);
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
		return false;
	}
}
