package lazerdoom;

import gui.editor.Editor;
import gui.editor.SequencerEditor;
import gui.item.editor.PushButton;
import gui.item.editor.TouchableEditor;
import gui.multitouch.TouchableGraphicsItemContainer;
import gui.scene.editor.EditorScene;
import gui.scene.editor.SequenceDataEditorScene;
import gui.scene.editor.SynthesizerScene;
import gui.view.SequencerView;


import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Vector;

import lazerdoom.TUIOMouseEmulation.MouseCursor;
import message.Intercom;


import com.illposed.osc.OSCBundle;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPacket;
import com.illposed.osc.OSCPortOut;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
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

import control.types.DoubleType;

import de.sciss.net.OSCServer;

import sequencer.EventPointsSequence;
import sequencer.TestingSequencer;
import session.SessionHandler;
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
	
	private SessionHandler sessionHandler = new SessionHandler(LazerdoomConfiguration.getInstance().baseLzrdmPath+LazerdoomConfiguration.getInstance().sessionFileName);
	
	private EditorScene scene = new EditorScene();
	private SequencerView view = new SequencerView(new SequencerEditor(scene,false), this);
	private QHBoxLayout layout = new QHBoxLayout();

    public static void main(String[] args) {
    	
    	File configFile = new File(System.getProperty("user.home")+"/lzrdm/lzrdmConfig.xml");
		XStream configParser = new XStream(new DomDriver());
		configParser.alias("lzrdmConfig", LazerdoomConfiguration.class);

    	if(configFile.exists()) {
    		System.out.print("Loading configuration ...");
    		LazerdoomConfiguration lzrdmConfig;
			
    		try {
				lzrdmConfig = (LazerdoomConfiguration) configParser.fromXML(new FileReader(configFile));
	    		lzrdmConfig._setInstance(lzrdmConfig);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
    		System.out.println("done");

    	} else {
    		// Dump base-cfg
    		try {
    			LazerdoomConfiguration newConfig = new LazerdoomConfiguration();
    			configParser.toXML(newConfig, new FileWriter(configFile));
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    	
    	if(!LazerdoomConfiguration.getInstance().enableDebugOutput) {
    		// /dev/null ;)
    		System.setOut (new java.io.PrintStream(new java.io.OutputStream() {public void write( int b) {}})) ;
    	}
    	
        QApplication.initialize(args);

        lazerdoom lazerdoomApp = new lazerdoom(null);
        lazerdoomApp.show();

        QShortcut shortcut = new QShortcut(new QKeySequence("Ctrl+Q"),lazerdoomApp);
        shortcut.activated.connect(QApplication.instance(), "quit()");
        lazerdoomApp.loadSession();
        QApplication.exec();
        
        SessionHandler.getInstance().safeSession(LazerdoomConfiguration.getInstance().baseLzrdmPath+LazerdoomConfiguration.getInstance().sessionFileName);
        
        System.exit(0);
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
 

    public void loadSession() {
    	SessionHandler.getInstance().loadSession();
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
		
		//System.out.println(oscPortOut);
        
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
        TUIOTouchHandler handler = new TUIOTouchHandler(Intercom.getInstance().system.gestureInput);
        
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
		//this.showMaximized();
		this.showFullScreen();
		
		this.core.start();
		//System.out.println("**********"+QApplication.overrideCursor());
		//this.grabMouse();
    }
}
