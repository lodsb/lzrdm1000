package lazerdoom;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import sparshui.client.Client;
import sparshui.common.Event;
import sparshui.common.Location;
import sparshui.common.TouchState;
import sparshui.common.messages.events.TouchEvent;
import SceneItems.ContextMenu;
import SceneItems.TouchItemInterface;
import SceneItems.TouchPointCursor;

import com.trolltech.qt.QSignalEmitter.Signal1;
import com.trolltech.qt.QSignalEmitter.Signal2;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QPoint;
import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.opengl.QGLWidget;


public class View extends QGraphicsView implements Client{
	private HashMap<Integer, TouchItemInterface> touchItemGroupIDMap = new HashMap<Integer, TouchItemInterface>();
	
	
	/**
	 * 
	 * for sparshui interface
	 *
	 */
	
	private static View instance = null;
	public static View getInstance() {
		return instance;
	}
	
	private int viewGroupID = 1;
	private LinkedList<Integer> viewGestures = new LinkedList<Integer>();
	private HashMap<Integer, TouchPointCursor> touchPointCursors = new HashMap<Integer, TouchPointCursor>();
	
	private class ProcessRunnable implements Runnable {
		public int _id;
		public Event _event;
		public Signal2<Integer,Event> _signal;
		@Override
		public void run() {
			_signal.emit(_id, _event);
		}
	}
	
	private class GetGroupIDRunnable implements Runnable {
		public Location _pos;
		public Signal1<Location> _signal;
		@Override
		public void run() {
			_signal.emit(_pos);
		}
	}
	
	private class GetAllowedGesturesRunnable implements Runnable {
		public int _id;
		public Signal1<Integer> _signal;
		@Override
		public void run() {
			_signal.emit(_id);
		}
	}
	
	ProcessRunnable processRunnable = new ProcessRunnable();
	GetGroupIDRunnable getGroupIDRunnable = new GetGroupIDRunnable();
	GetAllowedGesturesRunnable getAllowedGesturesRunnable = new GetAllowedGesturesRunnable();
	
	private Signal2<Integer, Event> processEventSignal = new Signal2<Integer,Event>();
	
	private volatile Integer getGroupIDReturnValue;
	private Signal1<Location> getGroupIDSignal = new Signal1<Location>();
	
	private volatile List<Integer> getAllowedGesturesReturnValue;
	private Signal1<Integer> getAllowedGesturesSignal = new Signal1<Integer>();
	
	
	public View() {
		this.processEventSignal.connect(this, "processEventSlot(Integer, Event)");
		this.getGroupIDSignal.connect(this, "getGroupIDSlot(Location)");
		this.getAllowedGesturesSignal.connect(this, "getAllowedGesturesSlot(int)");
		
		processRunnable._signal = this.processEventSignal;
		getGroupIDRunnable._signal = this.getGroupIDSignal;
		getAllowedGesturesRunnable._signal = this.getAllowedGesturesSignal;
		
		
		/*
		 * Gestures supported by the view
		 */
		
		viewGestures.addLast(sparshui.gestures.GestureType.TOUCH_GESTURE.ordinal());
		
		View.instance = this;
	}
		
	public void registerTouchItem(TouchItemInterface it) {
		touchItemGroupIDMap.put(it.getGroupID(), it);
	}
	
	public void unregisterTouchItem(TouchItemInterface it) {
		touchItemGroupIDMap.remove(it.getGroupID());
	}
	
	@Override
	public List<Integer> getAllowedGestures(int id) {
		getAllowedGesturesRunnable._id = id;
		QApplication.invokeAndWait(getAllowedGesturesRunnable);
		return getAllowedGesturesReturnValue;
	}
	
	public void getAllowedGesturesSlot(int id) {
		TouchItemInterface it = null;
		
		System.out.println(id + " "+viewGroupID);
		
		if(id == viewGroupID) {
			getAllowedGesturesReturnValue = viewGestures;
			return;
		}
		
		getAllowedGesturesReturnValue = null;
		
		
		if((it = touchItemGroupIDMap.get(id)) != null) {
			getAllowedGesturesReturnValue = it.getAllowedGestures();
		}
	}

	@Override
	public int getGroupID(Location pos) {		
		getGroupIDRunnable._pos = pos;
		QApplication.invokeAndWait(getGroupIDRunnable);
		return getGroupIDReturnValue;
	}

	
	public void getGroupIDSlot(Location pos) {
		double x = pos.getX();
		double y = pos.getY();
		
		QGraphicsItemInterface it = this.scene().itemAt(convertScreenPos(x,y));
		
		getGroupIDReturnValue = 0;
		
		if(it instanceof TouchItemInterface) {
			getGroupIDReturnValue = ((TouchItemInterface)it).getGroupID();
			
			if(!touchItemGroupIDMap.containsKey(getGroupIDReturnValue)) {
				this.registerTouchItem(((TouchItemInterface)it));
			}
			
		} else {
			getGroupIDReturnValue = this.viewGroupID;
		}
	}

	@Override
	public synchronized void processEvent(final int id, final Event event) {
		processRunnable._id = id;
		processRunnable._event = event;
		QApplication.invokeAndWait(processRunnable);
	}
	
	public QPointF convertScreenPos(double x, double y) {
		x = x*this.viewport().width();
		y = y*this.viewport().height();
		return this.mapToScene(new QPoint((int)x, (int)y));
	}
	
	private void processEventSlot(Integer id, Event event) {
		
		if(id == viewGroupID) {
			if(event instanceof TouchEvent) {
				TouchPointCursor tc = null;
				TouchEvent e = (TouchEvent) event;
				
				if(e.getState() == TouchState.BIRTH) {
					for(Entry<Integer, TouchPointCursor> tc2: touchPointCursors.entrySet()) {
						if(!(tc2.getValue()).isVisible()) {
							tc = tc2.getValue();
							break;
						}
					}
					
					if(tc == null) { 
						tc = new TouchPointCursor();
						this.scene().addItem(tc);	
					}
						
					touchPointCursors.put(e.getTouchID(), tc);
					tc.setPos(convertScreenPos(e.getX(), e.getY()));
					
					tc.setVisible(true);
					
					//tc.setHTMLText("<b>ID: </b>"+e.getTouchID()+"<br><b>Pos x/y: <b>"+convertScreenPos(e.getX(), e.getY()).x()+"/"+convertScreenPos(e.getX(), e.getY()).y());
					
				} else if(e.getState() == TouchState.MOVE) {
					tc = touchPointCursors.get(e.getTouchID());
					tc.setPos(convertScreenPos(e.getX(), e.getY()));
					//tc.setHTMLText("<b>ID: </b>"+e.getTouchID()+"<br><b>Pos x/y: <b>"+convertScreenPos(e.getX(), e.getY()).x()+"/"+convertScreenPos(e.getX(), e.getY()).y());
				} else if(e.getState() == TouchState.DEATH) {
					tc = touchPointCursors.get(e.getTouchID());
					tc.setVisible(false);
				}
				
			}
		} else {
			TouchItemInterface it = null;
			if((it  = touchItemGroupIDMap.get(id)) != null) {
				it.processEvent(event);
			}
		}
	}
}
