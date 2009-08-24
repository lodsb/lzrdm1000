package GUI.Editor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import GUI.Editor.Commands.GroupCommand;
import GUI.Item.TouchPointCursor;
import GUI.Multitouch.TouchItemInterface;

import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QGraphicsPathItem;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.gui.QPainterPath;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QUndoStack;

import sparshui.common.TouchState;
import sparshui.common.messages.events.*;

public class Editor extends QObject {
	private QUndoStack undoStack = new QUndoStack();
	private QGraphicsScene scene;
	
	public Signal0 executedCommand = new Signal0();
	public Signal0 undoneCommand = new Signal0();
	public Signal0 redoneCommand = new Signal0();
	
	private boolean showTouchEvents = false;
	
	public void setShowTouchEvents(boolean showTouchEvents) {
		this.showTouchEvents = showTouchEvents;
	}
	
	public boolean showTouchEvents() {
		return this.showTouchEvents;
	} 
	
	public Editor(QGraphicsScene scene) {
		this.scene = scene;
	}
	
	public Editor(QGraphicsScene scene, boolean showTouchPoints) {
		this.scene = scene;
		this.setShowTouchEvents(showTouchPoints);
	}

	
	public void setScene(QGraphicsScene scene) {
		this.scene = scene;
	}
	
	public void undo() {
		if(undoStack.canUndo()) {
			undoStack.undo();
		}
	}
	
	public void redo() {
		if(undoStack.canRedo()) {
			undoStack.redo();
		}
	}
	
	private HashMap<Integer, TouchPointCursor> touchPointCursors = new HashMap<Integer, TouchPointCursor>();
	
	public void handleTouchEvent(TouchEvent event) {
		if(this.showTouchEvents) {
				TouchPointCursor tc = null;
				TouchEvent e = (TouchEvent) event;
				
				if(e.getState() == TouchState.BIRTH) {					
					tc = new TouchPointCursor();
					this.scene.addItem(tc);	
					
						
					touchPointCursors.put(e.getTouchID(), tc);
					tc.setPos(event.getSceneLocation());
					tc.setZValue(-100.0);
					
					tc.setVisible(true);
					
					tc.setHTMLText("<b>ID: </b>"+e.getTouchID()+"<br><b>Pos x/y: <b>"+event.getSceneLocation().x()+"/"+event.getSceneLocation().y());
					
				} else if(e.getState() == TouchState.MOVE) {
					tc = touchPointCursors.get(e.getTouchID());
					tc.setPos(event.getSceneLocation());
					tc.setHTMLText("<b>ID: </b>"+e.getTouchID()+"<br><b>Pos x/y: <b>"+event.getSceneLocation().x()+"/"+event.getSceneLocation().y());
				} else if(e.getState() == TouchState.DEATH) {
					tc = touchPointCursors.get(e.getTouchID());
					touchPointCursors.remove(tc);
					this.scene.removeItem(tc);
					tc.setVisible(false);
				}
				
		}
	}
	
	public void handleExtendedGestureEvent(ExtendedGestureEvent event) {
		if(event instanceof GroupEvent) {
			GroupEvent e = (GroupEvent) event;
			this.handleGroupEvent(e);
		}
	}
	
	private HashMap<Integer, QGraphicsPathItem> groupEventsMap = new HashMap<Integer, QGraphicsPathItem>();
	
	protected void handleGroupEvent(GroupEvent event) {
		QGraphicsPathItem p;
		if(event.isOngoing()) {
			QPointF point = event.getSceneLocation();

			if((p = groupEventsMap.get(event.getTouchID())) == null) {
				p = new QGraphicsPathItem();
				this.scene.addItem(p);
				//p.setPos(point);
				//this.scene().addEllipse(point.x(), point.y(), 100,100);
				QPen pen = new QPen(QColor.yellow);
				pen.setWidth(8);
				p.setPen(pen);
				QPainterPath path = new QPainterPath();
				path.addEllipse(point, 50,50);
				path.moveTo(point);
				p.setPath(path);
				groupEventsMap.put(event.getTouchID(), p);
			} 

			QPainterPath path = new QPainterPath(p.path());
			path.lineTo(point);
			p.setPath(path);
		} else {
			if((p = groupEventsMap.get(event.getTouchID())) != null) {
				this.scene.removeItem(groupEventsMap.get(event.getTouchID()));
				this.groupEventsMap.remove(event.getTouchID());
				
				if(event.isSuccessful()) {
					this.executeCommand(new GroupCommand(p.path(), this.scene));
				}
			}
		}
	}
	
	protected void handleDragEvent(DragEvent event) {
		
	}
	
	public boolean executeCommand(BaseEditorCommand command) {
		boolean ret = false;
		if(command.execute()) {
			undoStack.push(command);
		}
		return ret;
	}
}
