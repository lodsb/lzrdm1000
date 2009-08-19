package GUI.View;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import sparshui.client.Client;
import sparshui.common.Event;
import sparshui.common.Location;
import sparshui.common.TouchState;
import sparshui.common.messages.events.DragEvent;
import sparshui.common.messages.events.TouchEvent;

import com.trolltech.qt.QThread;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QPoint;
import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QRadialGradient;
import GUI.Multitouch.*;
import GUI.Item.*;
import GUI.Item.SynthesizerItem;

import java.util.Map.Entry;
import SceneItems.TouchPointCursor;
import GUI.Multitouch.*;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

public class SequencerView extends QGraphicsView implements Client, TouchItemInterface {	
	private class TeCommThread extends QObject implements Runnable {
		Signal2<TouchEventCommunicationContainer, Location> groupIDSignal = new Signal2<TouchEventCommunicationContainer, Location>();
		Signal2<TouchEventCommunicationContainer, Integer> gesturesSignal = new Signal2<TouchEventCommunicationContainer,Integer>();
		Signal2<Integer, Event> processEventSignal = new Signal2<Integer, Event>();
		
		SequencerView sc;
		public TeCommThread(SequencerView sc) {
			this.sc = sc;
			
			groupIDSignal.connect(sc, "getGroupIDLocalThread(GUI.View.SequencerView$TouchEventCommunicationContainer, sparshui.common.Location)");
			gesturesSignal.connect(sc, "getAllowedGesturesLocalThread(GUI.View.SequencerView$TouchEventCommunicationContainer, int)");
			processEventSignal.connect(sc, "processEventLocalThread(Integer, Event)");
		}
		
		void setGroupID(TouchEventCommunicationContainer tc, int id) {
			tc.returnGroupID = id;
			teCommContainerRecvQueue.add(tc);
		}
		
		void setAllowedGestures(TouchEventCommunicationContainer tc, List<Integer> list) {
			tc.returnAllowedGestures= list;
			teCommContainerRecvQueue.add(tc);
		}
		
		@Override
		public void run() {
			ProcessEvent pe;
			TouchEventCommunicationContainer tc;
			
			while(true) { 
				if((pe = processEventQueue.poll()) != null) {
					processEventSignal.emit(pe.id, pe.event);
				}
				
				if((tc = teCommContainerSendQueue.poll()) != null) {
					if(tc.isGetGroupID) {
						groupIDSignal.emit(tc, tc.loc);
					} else {
						gesturesSignal.emit(tc, tc.id);
					}
				}
			}
		}
		
	} 
	// ugly!
	QThread thread;
	TeCommThread tect;
	ConcurrentLinkedQueue<TouchEventCommunicationContainer> teCommContainerSendQueue = new ConcurrentLinkedQueue<TouchEventCommunicationContainer>();
	ConcurrentLinkedQueue<TouchEventCommunicationContainer> teCommContainerRecvQueue = new ConcurrentLinkedQueue<TouchEventCommunicationContainer>(); 
	private class TouchEventCommunicationContainer {
		TouchEventCommunicationContainer(Location l, int id, boolean isGetGroup) {
			this.loc = l;
			this.id = id;
			this.isGetGroupID = isGetGroup;
		}
		
		Location loc;
		int id;
		boolean isGetGroupID;
		
		
		int returnGroupID = 0;
		List<Integer> returnAllowedGestures;
		
	}

	ConcurrentLinkedQueue<ProcessEvent> processEventQueue = new ConcurrentLinkedQueue<ProcessEvent>();
	private class ProcessEvent {
		ProcessEvent(int id, Event event) {
			this.id = id;
			this.event = event;
		}
		int id;
		Event event;
	}
	
	private HashMap<Integer, TouchItemInterface> touchItemGroupIDMap = new HashMap<Integer, TouchItemInterface>();
	
	
	/**
	 * 
	 * for sparshui interface
	 *
	 */
	
	private static SequencerView instance = null;
	public static SequencerView getInstance() {
		return instance;
	}
	
	private int viewGroupID = 1;
	private LinkedList<Integer> viewGestures = new LinkedList<Integer>();
	private HashMap<Integer, TouchPointCursor> touchPointCursors = new HashMap<Integer, TouchPointCursor>();
	
	private LinkedList<SequenceButton> menuItems = new LinkedList<SequenceButton>();
	
	private void createMenuItems() {
		double margin = 10;
		
		QGraphicsScene scene = this.scene();
		
		//basic size
		QRectF sceneRect = scene.sceneRect();
		
		SequenceButton button1 = new SequenceButton("addSequence");
		SequenceButton button2 = new SequenceButton("addSynth");
		SequenceButton button3 = new SequenceButton("delete");
		
		QRectF buttonRect = button1.boundingRect();
		double verticalOffset = (sceneRect.height() - (buttonRect.height()*3))/2;
		
		button1.setPos(buttonRect.width(),verticalOffset);
		button2.setPos(buttonRect.width(),verticalOffset+margin+buttonRect.height());
		button3.setPos(buttonRect.width(),verticalOffset+2*(margin+buttonRect.height()));
		
		scene.addItem(button1);
		scene.addItem(button2);
		scene.addItem(button3);
		
		menuItems.add(button1);
		menuItems.add(button2);
		menuItems.add(button3);

		button3 = new SequenceButton("addSequence");
		button2 = new SequenceButton("addSynth");
		button1 = new SequenceButton("delete");
		
		button1.setParent(this);
		button2.setParent(this);
		button3.setParent(this);

		
		button1.setPos(sceneRect.width()-buttonRect.width(),verticalOffset);
		button2.setPos(sceneRect.width()-buttonRect.width(),verticalOffset+margin+buttonRect.height());
		button3.setPos(sceneRect.width()-buttonRect.width(),verticalOffset+2*(margin+buttonRect.height()));
		
		button1.rotate(180.0);
		button2.rotate(180.0);
		button3.rotate(180.0);

		button1.setParent(this);
		button2.setParent(this);
		button3.setParent(this);

		
		scene.addItem(button1);
		scene.addItem(button2);
		scene.addItem(button3);
		
		menuItems.add(button1);
		menuItems.add(button2);
		menuItems.add(button3);
	}
	
	public SequencerView() {
		
		/*
		 * Gestures supported by the view
		 */
		
		viewGestures.add(sparshui.gestures.GestureType.TOUCH_GESTURE.ordinal());
		viewGestures.add(sparshui.gestures.GestureType.DRAG_GESTURE.ordinal());
		
		this.setCacheMode(QGraphicsView.CacheModeFlag.CacheBackground);
		
		SequencerView.instance = this;
		
		tect = new TeCommThread(this);	
		this.thread = new QThread(tect);
		tect.moveToThread(thread);
		thread.start();
		
		this.setRenderHint(QPainter.RenderHint.Antialiasing);
	}
	
	public void setUp(double scale) {
		QRectF sceneRect = this.scene().sceneRect();
		createMenuItems();
		this.centerOn(new QPointF(sceneRect.height()/2, sceneRect.width()/2));
		this.scale(scale, scale);
		
		
		/*****/
		
		SequenceItem si1 = new SequenceItem(true);
		si1.setPos(500,500);
		this.scene().addItem(si1);
		
		SequenceItem si2;
		si2 = new SequenceItem(false);
		si2.setPos(700,500);
		this.scene().addItem(si2);
		
		SequenceItem si3;
		si3 = new SequenceItem(false);
		si3.setPos(300,500);
		this.scene().addItem(si3);
		
		SequenceItem si4;
		si4 = new SequenceItem(false);
		si4.setPos(300,300);
		this.scene().addItem(si4);
		
		SequenceItem si5;
		si5 = new SequenceItem(false);
		si5.setPos(300,700);
		this.scene().addItem(si5);
		
		SequenceItem si6;
		si6 = new SequenceItem(false);
		si6.setPos(200,200);
		this.scene().addItem(si6);
		
		SequencePlayerItem player;
		player = new SequencePlayerItem();
		player.setPos(800,800);
		this.scene().addItem(player);
		
		SynthesizerItem synth;
		synth = new SynthesizerItem(new String[]{"A", "B", "C"});
		
		synth.setPos(500,800);
		this.scene().addItem(synth);
	
		EditorCursor ec = new EditorCursor();
		ec.setPos(1000,1000);
		this.scene().addItem(ec);
		
		this.scene().addItem(new SequenceConnection( player.getSequenceOutConnector(), si2.getSequenceInConnector()));
		this.scene().addItem(new SequenceConnection( si1.getSequenceOutConnector(), si6.getSequenceInConnector()));
		this.scene().addItem(new SequenceConnection( si3.getSequenceOutConnector(), si5.getSequenceInConnector()));
		this.scene().addItem(new SequenceConnection( si3.getSequenceOutConnector(), si1.getSequenceInConnector()));
		this.scene().addItem(new SequenceConnection( si2.getSequenceOutConnector(), si3.getSequenceInConnector()));
		this.scene().addItem(new SequenceConnection( si2.getSequenceOutConnector(), si4.getSequenceInConnector()));
		this.scene().addItem(new SynthConnection(synth.getSynthInConnectors().get(1), si4.getSynthOutConnectors().get(0)));
		this.scene().addItem(new SynthConnection(synth.getSynthInConnectors().get(0), si2.getSynthOutConnectors().get(0)));
		this.scene().addItem(new SynthConnection(synth.getSynthInConnectors().get(2), si3.getSynthOutConnectors().get(0)));
		//this.scene().addItem(new SynthConnection(synth.getSynthInConnectors().get(3), si6.getSynthOutConnectors().get(0)));
		//this.scene().addItem(new MulticontrolItem());
	
		
	}
		
	public void registerTouchItem(TouchItemInterface it) {
		touchItemGroupIDMap.put(it.getGroupID(), it);
	}
	
	public void unregisterTouchItem(TouchItemInterface it) {
		touchItemGroupIDMap.remove(it.getGroupID());
	}
	
	@Override
	public List<Integer> getAllowedGestures(int id) {
		TouchEventCommunicationContainer tc = new TouchEventCommunicationContainer(null, id, false);
		return postAndwaitUntilProcessed(tc).returnAllowedGestures;
	}
	
	public void getAllowedGesturesLocalThread(TouchEventCommunicationContainer tc, int id) {
		TouchItemInterface it = null;
		List<Integer> ret = null;
		
		System.out.println(id +" + "+viewGroupID);
		
		if(id == viewGroupID) {
			ret = viewGestures;
		} else if((it = touchItemGroupIDMap.get(id)) != null) {
			ret = it.getAllowedGestures();
		}
		
		tect.setAllowedGestures(tc, ret);
	}

	private TouchEventCommunicationContainer postAndwaitUntilProcessed(TouchEventCommunicationContainer tc) {
		teCommContainerSendQueue.add(tc);
		
		TouchEventCommunicationContainer currentTe;
		while(true) {
			Iterator<TouchEventCommunicationContainer> it = teCommContainerRecvQueue.iterator();
			while(it.hasNext()) {
				currentTe = it.next();
				if(currentTe == tc) {
					teCommContainerRecvQueue.remove(tc);
					
					return tc;
				}
			}
		}
	}
	
	@Override
	public int getGroupID(Location pos) {		
		TouchEventCommunicationContainer tc = new TouchEventCommunicationContainer(pos, -1, true);
		return postAndwaitUntilProcessed(tc).returnGroupID;
	}

	
	public void getGroupIDLocalThread(TouchEventCommunicationContainer tc, Location pos) {
		double x = pos.getX();
		double y = pos.getY();
		int getGroupIDReturnValue = 0;
		
		QGraphicsItemInterface it = this.scene().itemAt(convertScreenPos(x,y));
		
		if(it != null && it instanceof TouchItemInterface) {
			getGroupIDReturnValue = ((TouchItemInterface)it).getGroupID();
			
			if(!touchItemGroupIDMap.containsKey(getGroupIDReturnValue)) {
				this.registerTouchItem(((TouchItemInterface)it));
			}
			
		} else {
			getGroupIDReturnValue = this.viewGroupID;
		}
		
		tect.setGroupID(tc, getGroupIDReturnValue);
	}

	@Override
	public synchronized void processEvent(final int id, final Event event) {
		processEventQueue.add(new ProcessEvent(id, event));
	}
	
	public QPointF convertScreenPos(double x, double y) {
		x = x*this.viewport().width();
		y = y*this.viewport().height();
		return this.mapToScene(new QPoint((int)x, (int)y));
	}
	
	private void processEventLocalThread(Integer id, Event event) {
		this.update();
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
					
					tc.setHTMLText("<b>ID: </b>"+e.getTouchID()+"<br><b>Pos x/y: <b>"+convertScreenPos(e.getX(), e.getY()).x()+"/"+convertScreenPos(e.getX(), e.getY()).y());
					
				} else if(e.getState() == TouchState.MOVE) {
					tc = touchPointCursors.get(e.getTouchID());
					tc.setPos(convertScreenPos(e.getX(), e.getY()));
					tc.setHTMLText("<b>ID: </b>"+e.getTouchID()+"<br><b>Pos x/y: <b>"+convertScreenPos(e.getX(), e.getY()).x()+"/"+convertScreenPos(e.getX(), e.getY()).y());
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
	
	
	
	private QColor bgColor1 = new QColor(38,50,62);
	private QColor bgColor2 = new QColor(bgColor1.lighter(155));
	
    protected void drawBackground(QPainter painter, QRectF rect) {
        // Fill
        QRadialGradient gradient = new QRadialGradient(0,0,rect.width());
        gradient.setColorAt(0, bgColor2);
        gradient.setColorAt(1, bgColor1);
        painter.fillRect(rect, new QBrush(gradient));
    }

	@Override
	public List<Integer> getAllowedGestures() {
		// dummy
		return this.viewGestures;
	}

	@Override
	public int getGroupID() {
		return this.viewGroupID;
	}

	@Override
	public boolean processEvent(Event event) {
		if(event instanceof DragEvent) {
			DragEvent de = (DragEvent) event;
			
			TouchableGraphicsItem tii;
			
			if((tii = (TouchableGraphicsItem) de.getSource()) != null && tii.getParent() == this) {
				System.out.println(">***");
				System.out.println(de);
				System.out.println(de.getTouchID());
				System.out.println(de.isDrop());
				System.out.println("***>");
			}
		}
		return false;
	}

}
