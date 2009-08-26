package GUI.View;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import sparshui.client.Client;
import sparshui.common.Event;
import sparshui.common.Location;
import sparshui.common.TouchState;
import sparshui.common.messages.events.DeleteEvent;
import sparshui.common.messages.events.DragEvent;
import sparshui.common.messages.events.ExtendedGestureEvent;
import sparshui.common.messages.events.GroupEvent;
import sparshui.common.messages.events.TouchEvent;

import com.trolltech.qt.QThread;
import com.trolltech.qt.core.QCoreApplication;
import com.trolltech.qt.core.QEvent;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QPoint;
import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.QEvent.Type;
import com.trolltech.qt.core.Qt.KeyboardModifier;
import com.trolltech.qt.core.Qt.MouseButton;
import com.trolltech.qt.core.Qt.MouseButtons;
import com.trolltech.qt.core.Qt.Orientation;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QFrame;
import com.trolltech.qt.gui.QGraphicsEllipseItem;
import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QGraphicsLinearLayout;
import com.trolltech.qt.gui.QGraphicsPathItem;
import com.trolltech.qt.gui.QGraphicsProxyWidget;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.gui.QGraphicsWidget;
import com.trolltech.qt.gui.QMouseEvent;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPainterPath;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QRadialGradient;
import com.trolltech.qt.gui.QSizePolicy;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QGraphicsScene.ItemIndexMethod;
import com.trolltech.qt.gui.QSizePolicy.ControlType;
import com.trolltech.qt.gui.QSizePolicy.Policy;
import com.trolltech.qt.opengl.QGLWidget;

import edu.uci.ics.jung.graph.util.Pair;

import Control.Types.DoubleType;
import GUI.Multitouch.*;
import GUI.Editor.Editor;
import GUI.Editor.SequenceDataEditor.SequenceDataEditor;
import GUI.Item.*;
import GUI.Item.Editor.TouchableEditor;

import java.util.Map.Entry;
import SceneItems.TouchPointCursor;
import Sequencer.EventPointsSequence;
import Sequencer.SequencerInterface;
import Sequencer.TestingSequencer;
import GUI.Multitouch.*;
import GUI.Scene.Editor.SequenceDataEditorScene;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicLong;

public class SequencerView extends QGraphicsView implements Client, TouchItemInterface {	

	private QWidget parentWidget;
	
	public static QGLWidget sharedGlWidget;
	private LinkedList<TouchableEditor> editors = new LinkedList<TouchableEditor>();
	
	public void registerEditor(TouchableEditor editor) {
		editors.add(editor);
	}
	
	public void unregisterEditor(TouchableEditor editor) {
		editors.remove(editor);
	}
	
	Semaphore sema = new Semaphore(10);
	
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
				
				try {
					sema.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
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
	
	private Editor sequencerEditor;
	
	public SequencerView(Editor editor) {
	
		this.parentWidget = parentWidget;
		this.sequencerEditor = editor;
		
		/*
		 * Gestures supported by the view
		 */
		
		//viewGestures.add(sparshui.gestures.GestureType.TOUCH_GESTURE.ordinal());
		//viewGestures.add(sparshui.gestures.GestureType.DRAG_GESTURE.ordinal());
		viewGestures.add(sparshui.gestures.GestureType.GROUP_GESTURE.ordinal());
		viewGestures.add(sparshui.gestures.GestureType.DELETE_GESTURE.ordinal());
		
		this.setCacheMode(QGraphicsView.CacheModeFlag.CacheNone);
		this.setViewportUpdateMode(ViewportUpdateMode.FullViewportUpdate);
		this.sharedGlWidget = new QGLWidget();
		System.out.println(this.sharedGlWidget);
		this.setViewport(this.sharedGlWidget);
		System.out.println("fdsdf "+this.viewportUpdateMode());
		
		SequencerView.instance = this;
		
		tect = new TeCommThread(this);	
		this.thread = new QThread(tect);
		tect.moveToThread(thread);
		thread.start();
		
		this.setRenderHint(QPainter.RenderHint.HighQualityAntialiasing);
		
		this.registerTouchItem(this);
	
		//updateTimer.timeout.connect(this, "update()");
		//updateTimer.start(1000/60);
	}
	
	public void setUp(double scale) {
		QRectF sceneRect = this.scene().sceneRect();
		createMenuItems();
		this.centerOn(new QPointF(sceneRect.height()/2, sceneRect.width()/2));
		this.scale(scale, scale);
		
		//this.scene().setItemIndexMethod(ItemIndexMethod.NoIndex);
		
		/*
		
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
		this.scene().addItem(new SynthConnection(synth.getSynthInConnectors().get(2), si3.getSynthOutConnectors().get(0)));*/
		//this.scene().addItem(new SynthConnection(synth.getSynthInConnectors().get(3), si6.getSynthOutConnectors().get(0)));
		//this.scene().addItem(new MulticontrolItem());
	
		
		
		/*QGraphicsLinearLayout baseLayout = new QGraphicsLinearLayout() {
			public String toString() { return "BNGGGFFGGF"; }
		};
		baseLayout.setOrientation(Orientation.Vertical);
		QGraphicsLinearLayout layout = new QGraphicsLinearLayout();
		layout.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);
		PushButton button = new PushButton("<");
		TextLabel button1 = new TextLabel("SynthesizerOne");
		PushButton button2 = new PushButton(">");
		TouchableGraphicsItemContainer container = new TouchableGraphicsItemContainer();
		container.setItem(button);
		layout.addItem(container);
		container = new TouchableGraphicsItemContainer();
		container.setItem(button1);
		layout.addItem(container);
		container = new TouchableGraphicsItemContainer();
		container.setItem(button2);
		layout.addItem(container);
		baseLayout.addItem(layout);
		
		layout = new QGraphicsLinearLayout();
		
		Slider slider1 = new Slider();
		Slider slider2 = new Slider();
		Slider slider3 = new Slider();
		Slider slider4 = new Slider();
		Slider slider5 = new Slider();
		Slider slider6 = new Slider();
		Slider slider7 = new Slider();
		Slider slider8 = new Slider();
		
		container = new TouchableGraphicsItemContainer();
		container.setItem(slider1);
		layout.addItem(container);
		container = new TouchableGraphicsItemContainer();
		container.setItem(slider2);
		layout.addItem(container);
		container = new TouchableGraphicsItemContainer();
		container.setItem(slider3);
		layout.addItem(container);
		container = new TouchableGraphicsItemContainer();
		container.setItem(slider4);
		layout.addItem(container);
		container = new TouchableGraphicsItemContainer();
		container.setItem(slider5);
		layout.addItem(container);
		container = new TouchableGraphicsItemContainer();
		container.setItem(slider6);
		layout.addItem(container);
		container = new TouchableGraphicsItemContainer();
		container.setItem(slider7);
		layout.addItem(container);
		container = new TouchableGraphicsItemContainer();
		container.setItem(slider8);
		layout.addItem(container);

		baseLayout.addItem(layout);*/
		//QGraphicsProxyWidget x = new QGraphicsProxyWidget();
		//QGraphicsScene sc = new QGraphicsScene();
		//TouchableGraphicsView gv = new TouchableGraphicsView();
		//gv.setScene(sc);
		//gv.setSizeIncrement(500, 800);
		//x.setWidget(new QGraphicsView());

		EventPointsSequence<DoubleType> s = new EventPointsSequence<DoubleType>(new TestingSequencer());
		s.insert(new DoubleType(0.5), 100);
		s.insert(new DoubleType(-0.5), 200);
		s.insert(new DoubleType(1.0), 300);
		s.insert(new DoubleType(-1.0), 400);
		s.insert(new DoubleType(0.3), 500);
		s.insert(new DoubleType(0.2), 600);
		s.insert(new DoubleType(-0.1), 700);
		s.insert(new DoubleType(0.3), 800);
		
		SequenceDataEditorScene<DoubleType> sc = new SequenceDataEditorScene<DoubleType>();
		SequenceDataEditor<DoubleType> e = new SequenceDataEditor<DoubleType>(sc, s);
		TouchableEditor editor = new TouchableEditor();
		editor.setScene(sc);
		QFrame frame = new QFrame();
		QGraphicsView fview = new QGraphicsView(frame);
		fview.setScene(sc);
		this.scene().addItem(editor);
		editor.setPos(800, 800);
		this.registerEditor(editor);
	}
		
	public void registerTouchItem(TouchItemInterface it) {
		touchItemGroupIDMap.put(it.getGroupID(), it);
	}
	
	public void unregisterTouchItem(TouchItemInterface it) {
		touchItemGroupIDMap.remove(it.getGroupID());
	}
	
	@Override
	public List<Integer> getAllowedGestures(int id) {
		System.out.println("gestures");
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
			System.out.println("WHAT?");
			ret = it.getAllowedGestures();
		}
		
		tect.setAllowedGestures(tc, ret);
	}

	private TouchEventCommunicationContainer postAndwaitUntilProcessed(TouchEventCommunicationContainer tc) {
		teCommContainerSendQueue.add(tc);
		
		sema.release();
		
		TouchEventCommunicationContainer currentTe;
		while(true) {
			Iterator<TouchEventCommunicationContainer> it = teCommContainerRecvQueue.iterator();
			while(it.hasNext()) {
				currentTe = it.next();
				if(currentTe == tc) {
					teCommContainerRecvQueue.remove(tc);
					sema.release();
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
			int retVal = -1;
			
			for(TouchableEditor editor: editors) {
				QPointF point = editor.mapFromScene(convertScreenPos(x,y));
				System.out.println("editor");
				if(editor.boundingRect().contains(point)) {
					Pair<Object> pair = editor.getGroupIDViewCoordinates(point);
					
					if(pair != null && pair.getSecond() != null) {
						retVal = (Integer) pair.getFirst();
						TouchItemInterface editorTouchItem = (TouchItemInterface) pair.getSecond(); 
						System.out.println("YES EDITOR "+retVal);
						if(!touchItemGroupIDMap.containsKey(retVal)) {
							System.out.println("REGISTERED EDITOR");
							this.touchItemGroupIDMap.put(retVal, editorTouchItem);
							//						this.registerTouchItem(((TouchItemInterface)editor));
						}
					}
				}
			} 
			
			if(retVal == -1) {
				getGroupIDReturnValue = this.viewGroupID;
			} else {
				getGroupIDReturnValue = retVal;
			}
		}
		tect.setGroupID(tc, getGroupIDReturnValue);
	}

	@Override
	public synchronized void processEvent(final int id, final Event event) {
		sema.release();
		processEventQueue.add(new ProcessEvent(id, event));
	}
	
	public QPointF convertScreenPos(double x, double y) {
		x = x*this.viewport().width();
		y = y*this.viewport().height();
		return this.mapToScene((int)x, (int)y);
	}
	
	private void processEventLocalThread(Integer id, Event event) {
		//this.update();
		/*if(id == viewGroupID) {
			if(event instanceof TouchEvent) {
				TouchPointCursor tc = null;
				TouchEvent e = (TouchEvent) event;
				
				if(e.getState() == TouchState.BIRTH) {					
					tc = new TouchPointCursor();
					this.scene().addItem(tc);	
					
						
					touchPointCursors.put(e.getTouchID(), tc);
					tc.setPos(convertScreenPos(e.getX(), e.getY()));
					tc.setZValue(-100.0);
					
					tc.setVisible(true);
					
					tc.setHTMLText("<b>ID: </b>"+e.getTouchID()+"<br><b>Pos x/y: <b>"+convertScreenPos(e.getX(), e.getY()).x()+"/"+convertScreenPos(e.getX(), e.getY()).y());
					
				} else if(e.getState() == TouchState.MOVE) {
					tc = touchPointCursors.get(e.getTouchID());
					tc.setPos(convertScreenPos(e.getX(), e.getY()));
					tc.setHTMLText("<b>ID: </b>"+e.getTouchID()+"<br><b>Pos x/y: <b>"+convertScreenPos(e.getX(), e.getY()).x()+"/"+convertScreenPos(e.getX(), e.getY()).y());
				} else if(e.getState() == TouchState.DEATH) {
					tc = touchPointCursors.get(e.getTouchID());
					touchPointCursors.remove(tc);
					this.scene().removeItem(tc);
					tc.setVisible(false);
				}
				
			} 
		} else {*/
			TouchItemInterface it = null;
			if((it  = touchItemGroupIDMap.get(id)) != null) {
				if(event instanceof TouchEvent) {
					TouchEvent e = (TouchEvent) event;
					e.setSceneLocation(convertScreenPos(e.getX(), e.getY()));
				} 
				it.processEvent(event);
			}
		//}
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
	public void mousePressEvent(QMouseEvent event) {
	     System.out.println("??????????CLICK CLICK CLICK CLICK" + event.x() +  " " +event.y());
	     super.mousePressEvent(event);
	}
//	
//	@Override
//	public void mouseMoveEvent(QMouseEvent event) {
//		System.out.println("move move move"+ event.x() +  " " +event.y());
//		this.scene().addEllipse(event.x(), event.y(), 30,30);
//		//System.out.println(viewport().visibleRegion());
//		super.mouseMoveEvent(event);
//	}
//	
//	@Override
//	public void mouseReleaseEvent(QMouseEvent event) {
//		super.mouseReleaseEvent(event);
//	}

	
	private void sendMouseEventFromTouchEvent(TouchEvent touchEvent) {
		QPoint mousePoint =  this.mapFromScene(convertScreenPos(touchEvent.getX(), touchEvent.getY()));
		QEvent event;
		
		if(touchEvent.getState() == TouchState.BIRTH) {
			event = new QMouseEvent(Type.MouseButtonPress, new QPoint((int)mousePoint.x(), (int)mousePoint.y()), MouseButton.LeftButton, new Qt.MouseButtons() , KeyboardModifier.NoModifier);
		} else if(touchEvent.getState() == TouchState.MOVE) {
			event = new QMouseEvent(Type.MouseMove, new QPoint((int)mousePoint.x(), (int)mousePoint.y()), MouseButton.LeftButton, new Qt.MouseButtons() , KeyboardModifier.NoModifier);
		} else {
			event = new QMouseEvent(Type.MouseButtonRelease, new QPoint((int)mousePoint.x(), (int)mousePoint.y()), MouseButton.LeftButton, new Qt.MouseButtons() , KeyboardModifier.NoModifier);
		}
		
		QApplication.sendEvent(this.parentWidget, event);
	}
	
	QGraphicsEllipseItem ellipse = null;
	@Override
	public boolean processEvent(Event event) {
		if(event instanceof TouchEvent) {
			TouchEvent e = (TouchEvent) event;
			e.setSceneLocation(convertScreenPos(e.getX(), e.getY()));
			this.sequencerEditor.handleTouchEvent(e);
		} 
		if(event instanceof ExtendedGestureEvent) {
			ExtendedGestureEvent e = (ExtendedGestureEvent) event;
			e.setSceneLocation(convertScreenPos(e.getRelX(), e.getRelY()));
			this.sequencerEditor.handleExtendedGestureEvent(e);
			
			if(event instanceof DeleteEvent) {
				if(ellipse == null) {
					ellipse = new QGraphicsEllipseItem(-30,-30,60,60);
					ellipse.setPen(new QPen(QColor.white));
					ellipse.setPos(500,500);
					this.scene().addItem(ellipse);
					ellipse = new QGraphicsEllipseItem(-15,-15,30,30);
					ellipse.setPen(new QPen(QColor.red));
					this.scene().addItem(ellipse);
				}
				if(((DeleteEvent) e).getCrossPoint() != null) {
					QPointF point = convertScreenPos(((DeleteEvent) e).getCrossPoint().x(),((DeleteEvent) e).getCrossPoint().y());
					ellipse.setPos(point);
					System.out.println("WHATxdddd "+point);
				}
			}

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
