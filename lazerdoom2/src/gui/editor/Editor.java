package gui.editor;

import gui.editor.commands.GroupCommand;
import gui.item.TouchPointCursor;
import gui.multitouch.TouchItemInterface;
import gui.scene.editor.EditorScene;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import lazerdoom.LzrDmObjectInterface;


import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsEllipseItem;
import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QGraphicsPathItem;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.gui.QPainterPath;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QUndoStack;
import com.trolltech.qt.gui.QGraphicsItem.GraphicsItemFlag;

import session.SessionHandler;
import sparshui.common.TouchState;
import sparshui.common.messages.events.*;

public class Editor extends QObject implements LzrDmObjectInterface {
	//private QUndoStack undoStack = new QUndoStack();
	
	private LinkedList<BaseEditorCommand> undoStack = SessionHandler.getInstance().getCommandStack(this);
	protected EditorScene scene;
	
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
	
	protected Editor() {
		//SessionHandler.getInstance().registerObject(this);
	}
	
	protected void setScene(EditorScene scene) {
		this.scene = scene;
	}
	
	public Editor(EditorScene scene) {
		this.scene = scene;
		this.setShowTouchEvents(false);
	}
	
	public Editor(EditorScene scene, boolean showTouchPoints) {
		this.scene = scene;
		this.setShowTouchEvents(showTouchPoints);
	}

	
	public EditorScene getScene() {
		return this.scene;
	}
	
	public void undo() {
/*		if(undoStack.canUndo()) {
			undoStack.undo();
		}*/
	}
	
	public void redo() {
/*		if(undoStack.canRedo()) {
			undoStack.redo();
		}*/
	}
	
	private HashMap<Integer, TouchPointCursor> touchPointCursors = new HashMap<Integer, TouchPointCursor>();
	
	public void handleTouchEvent(TouchEvent event) {
		if(!event.isFocused() && this.showTouchEvents) {
			//System.out.println("trueeeeeeeee");
			
				TouchPointCursor tc = null;
				TouchEvent e = (TouchEvent) event;
				
				//System.out.println(e.getState()+"+++++++++");
				
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
		this.updateGestureVisualization(event);
		if(event instanceof GroupEvent) {
			GroupEvent e = (GroupEvent) event;
			this.handleGroupEvent(e);
		} else if(event instanceof DeleteEvent) {
			DeleteEvent e = (DeleteEvent) event;
			this.handleDeleteEvent(e);
		} else if(event instanceof DragEvent) {
			DragEvent e = (DragEvent) event;
			this.handleDragEvent(e);
		}
	}
	
	private QGraphicsEllipseItem deleteEllipse = null;
	private QTimer deleteEllipseTimer = new QTimer();
	protected void handleDeleteEvent(DeleteEvent event) {
		
		if(event.isSuccessful()) {
			System.out.println("Delete successful");
			if(deleteEllipse == null) {
				deleteEllipse = new QGraphicsEllipseItem(-20,-20,40,40);
				deleteEllipse.setPen(new QPen(QColor.red));
				deleteEllipse.setBrush(new QBrush(QColor.red));
				this.scene.addItem(deleteEllipse);
				
				deleteEllipseTimer.timeout.connect(this, "deleteEllipseShowTimeout()");
				deleteEllipse.hide();
			}
			if(((DeleteEvent) event).getCrossPoint() != null) {
				//deleteEllipseTimer.stop();
				deleteEllipse.setPos(event.getSceneCrossPoint());
				deleteEllipse.show();
				deleteEllipseTimer.start(1000);
			}
		}
	}
	
	private void deleteEllipseShowTimeout() {
		if(deleteEllipse != null && deleteEllipse.isVisible()) {
			deleteEllipse.hide();
		}
	}
	
	protected HashMap<Integer, QGraphicsPathItem> gestureVisualizationsMap = new HashMap<Integer, QGraphicsPathItem>();
	
	protected QPainterPath updateGestureVisualization(ExtendedGestureEvent event) {
		QPainterPath ppath = null;
		QGraphicsPathItem p;
		
		//if(event instanceof DragEvent) {
			if(event.isOngoing() && event instanceof DragEvent) {
				QPointF point = event.getSceneLocation();
				System.out.println("gesture viz "+point+" "+event+" # "+event.getTouchID());

				if((p = gestureVisualizationsMap.get(event.getTouchID())) == null) {
					System.out.println("NEW PATH");
					p = new QGraphicsPathItem();
					//p.setFlag(GraphicsItemFlag.ItemIgnoresTransformations, true);
					p.setZValue(-1000.0);
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
					gestureVisualizationsMap.put(event.getTouchID(), p);
				} else {

					QPainterPath path = new QPainterPath(p.path());
					path.lineTo(point);
					p.setPath(path);
				}
			} else {
				if((p = gestureVisualizationsMap.get(event.getTouchID())) != null) {
					if(event instanceof GroupEvent) {
						System.out.println("GROUP GROUP GROUP");
						System.out.println(event+" "+p);
						((GroupEvent)event).setPath(p.path());
					} else if(event instanceof DragEvent) {
						this.scene.removeItem(gestureVisualizationsMap.get(event.getTouchID()));
						this.gestureVisualizationsMap.remove(event.getTouchID());
					}
				}
			}

			if(p != null) {
				ppath =  p.path();
			}
		//}
		return ppath;
	}
	
	protected void handleGroupEvent(GroupEvent event) {			
	/*	if(!event.isOngoing() && event.isSuccessful()) {
			QPainterPath path = null;
//			if(path != null) {
				path = updateGestureVisualization(event);
				this.executeCommand(new GroupCommand(path, this.scene));
	//		}
		}*/
	}
	
	protected void handleDragEvent(DragEvent event) {
		
	}
	
	public boolean executeCommand(BaseEditorCommand command) {
		boolean ret = false;
		if(command.execute()) {
			undoStack.push(command);
			/*System.out.println("WHAT?");
			SessionHandler.getInstance().dumpSession();
			*/
		}
		return ret;
	}
}
