package lazerdoom;

import java.awt.Point;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Vector;

import lazerdoom.TUIOMouseEmulation.MouseCursor;

import Control.Types.DoubleType;
import GUI.Editor.Editor;
import GUI.Editor.SequencerEditor;
import GUI.Editor.SequenceDataEditor.SequenceDataEditor;
import GUI.Item.Editor.PushButton;
import GUI.Item.Editor.TouchableEditor;
import GUI.Multitouch.TouchableGraphicsItemContainer;
import GUI.Scene.Editor.EditorScene;
import GUI.Scene.Editor.SequenceDataEditorScene;
import GUI.Scene.Editor.SynthesizerScene;
import GUI.View.SequencerView;
import GUI.View.SynthesizerView;
import Sequencer.EventPointsSequence;
import Sequencer.TestingSequencer;

import com.illposed.osc.OSCBundle;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPacket;
import com.illposed.osc.OSCPortOut;
import com.trolltech.qt.QThread;
import com.trolltech.qt.core.QEvent;
import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.Qt.FocusPolicy;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.gui.QGraphicsView.ViewportUpdateMode;
import com.trolltech.qt.gui.QPainter.RenderHint;
import com.trolltech.qt.gui.QSizePolicy.Policy;
import com.trolltech.qt.opengl.QGLWidget;

import de.sciss.net.OSCServer;

import sparshui.server.GestureServer;
import sparshui.client.Client;
import sparshui.client.ServerConnection;
import sparshui.common.Event;
import sparshui.common.Location;
import sparshui.gestures.GestureType;


public class lazerdoom extends QWidget {
	
	private Core core = new Core();
	
	private SparshTUIOAdapter sparshTuioAdapter;
	private GestureServer sparshGestureServer;
	private Thread sparshGestureServerThread;
	private ServerConnection sparshServerConnection;
	
	private EditorScene scene = new EditorScene();
	private SequencerView view = new SequencerView(new SequencerEditor(scene,true), this);
	private QHBoxLayout layout = new QHBoxLayout();

    public static void main(String[] args) {
        QApplication.initialize(args);

        lazerdoom lazerdoomApp = new lazerdoom(null);
        lazerdoomApp.show();

        QApplication.exec();
    }

    private QCursor cursor = new QCursor(Qt.CursorShape.PointingHandCursor);
    private OSCPortOut oscPortOut;
    
    private int currentID = 0;
    private MouseCursor currentCursor = null;
    
    public void handleMouseMoveEvent(QMouseEvent event) {
    	if(currentCursor == null) {
    		return;
    	}
    	
		OSCBundle cursorBundle = new OSCBundle();
		OSCMessage aliveMessage = new OSCMessage("/tuio/2Dcur");
		aliveMessage.addArgument("alive");
		aliveMessage.addArgument(currentID);
		currentCursor.update(cursor.pos().x(), cursor.pos().y());
		Point point = currentCursor.getPosition();
		
		float xpos = (point.x)/(float)QApplication.desktop().screenGeometry().width();
		float ypos = (point.y)/(float)QApplication.desktop().screenGeometry().height();
		
		OSCMessage setMessage = new OSCMessage("/tuio/2Dcur");
		setMessage.addArgument("set");
		setMessage.addArgument(currentCursor.session_id);
		setMessage.addArgument(xpos);
		setMessage.addArgument(ypos);
		setMessage.addArgument(currentCursor.xspeed);
		setMessage.addArgument(currentCursor.yspeed);
		setMessage.addArgument(currentCursor.maccel);

		currentFrame++;
		OSCMessage frameMessage = new OSCMessage("/tuio/2Dcur");
		frameMessage.addArgument("fseq");
		frameMessage.addArgument(currentFrame);
		
		cursorBundle.addPacket(aliveMessage);
		cursorBundle.addPacket(setMessage);
		cursorBundle.addPacket(frameMessage);

		this.sendOSC(cursorBundle);
    }
    
    private int currentFrame = 0;
    
    public void handleMousePressEvent(QMouseEvent event) {
		OSCBundle cursorBundle = new OSCBundle();
		OSCMessage aliveMessage = new OSCMessage("/tuio/2Dcur");
		aliveMessage.addArgument("alive");
		aliveMessage.addArgument(currentID);

		currentCursor = new MouseCursor(currentID, cursor.pos().x(), cursor.pos().y());
		
		Point point = currentCursor.getPosition();
		
		float xpos = (point.x)/(float)QApplication.desktop().screenGeometry().width();
		float ypos = (point.y)/(float)QApplication.desktop().screenGeometry().height();
		
		OSCMessage setMessage = new OSCMessage("/tuio/2Dcur");
		setMessage.addArgument("set");
		setMessage.addArgument(currentCursor.session_id);
		setMessage.addArgument(xpos);
		setMessage.addArgument(ypos);
		setMessage.addArgument(currentCursor.xspeed);
		setMessage.addArgument(currentCursor.yspeed);
		setMessage.addArgument(currentCursor.maccel);

		currentFrame++;
		OSCMessage frameMessage = new OSCMessage("/tuio/2Dcur");
		frameMessage.addArgument("fseq");
		frameMessage.addArgument(currentFrame);
		
		cursorBundle.addPacket(aliveMessage);
		cursorBundle.addPacket(setMessage);
		cursorBundle.addPacket(frameMessage);

		this.sendOSC(cursorBundle);
    	
    	/*OSCBundle test = new OSCBundle();
    	
       	System.out.println("******* "+cursor.pos());
    	System.out.println("******* "+QApplication.desktop().screenGeometry());*/
    }
    
	private void sendOSC(OSCPacket packet) {
		try { oscPortOut.send(packet); }
		catch (java.io.IOException e) {}
	}
    
    public void handleMouseReleaseEvent(QMouseEvent event) {
    	
		
		OSCBundle cursorBundle = new OSCBundle();
		OSCMessage aliveMessage = new OSCMessage("/tuio/2Dcur");
		aliveMessage.addArgument("alive");

		currentFrame++;
		OSCMessage frameMessage = new OSCMessage("/tuio/2Dcur");
		frameMessage.addArgument("fseq");
		frameMessage.addArgument(currentFrame);
		
		cursorBundle.addPacket(aliveMessage);
		cursorBundle.addPacket(frameMessage);

		sendOSC(cursorBundle);
    	
       	currentID++;
       	currentCursor = null;
    }
    
    public void stopCommunication() {
    	sparshGestureServerThread.interrupt();
    }
 

    
    public lazerdoom(QWidget parent){
        super(parent);
        
        
        // mouse -> tuio emulation
        try {
			oscPortOut = new OSCPortOut(java.net.InetAddress.getLocalHost(), 3333);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(oscPortOut);
        
        /*System.out.println("Starting Sparsh-GestureServer");
        sparshGestureServer = new GestureServer();
        sparshGestureServerThread = new QThread (sparshGestureServer);
        sparshGestureServerThread.start();

        System.out.println("Starting Sparsh-TUIOAdapter");
        sparshTuioAdapter = new SparshTUIOAdapter(3333,5945);

        
        System.out.println("Starting Sparsh-ServerConnection");
        try {
			sparshServerConnection = new ServerConnection("localhost", view);

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	*/
        TUIOTouchHandler handler = new TUIOTouchHandler(view);
        
		this.setLayout(layout);
		layout.addWidget(view);	
		
		
		/*SynthesizerScene synthSc = new SynthesizerScene();
		QGraphicsView v = new QGraphicsView(this);
		v.setScene(synthSc);
		
		layout.addWidget(v);
		*/
		//QGLWidget w = new QGLWidget();
		//view.setViewport(w);
		view.setScene(scene);
		//view.setRenderHint(RenderHint.Antialiasing);
		//view.setViewportUpdateMode(ViewportUpdateMode.MinimalViewportUpdate);
		//view.scale(0.5, 0.5);
		
		view.setUp((double)0.5);

		this.setWindowFlags(Qt.WindowType.Window);
		this.setFocusPolicy(FocusPolicy.StrongFocus);
		this.setMouseTracking(true);
		
		QApplication.setOverrideCursor(cursor);
		this.showMaximized();
		
		this.core.start();
		//System.out.println("**********"+QApplication.overrideCursor());
		//this.grabMouse();
    }
}
