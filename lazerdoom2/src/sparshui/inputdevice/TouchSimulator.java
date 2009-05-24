package sparshui.inputdevice;

import java.awt.AWTEvent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import sparshui.common.ConnectionType;
import sparshui.common.NetworkConfiguration;
import sparshui.common.TouchState;

/**
 * 
 */
public class TouchSimulator extends JComponent implements AWTEventListener, MouseListener, MouseMotionListener, Runnable {
	private static final long serialVersionUID = 6258053340611808979L;

	private TreeSet<TouchData> _events = new TreeSet<TouchData>(new TouchDataComparator());
	private HashMap<Integer, TouchData> _active = new HashMap<Integer, TouchData>();
	private boolean _recording = false;
	private int _touchID = 0;
	private long _when = 0;
	private Timer _timer;
	private JFrame frame;
	private TransparentBackground bg;
	
	private DataOutputStream _out;

	// Colors used for rendering simulator visuals
	private Color birthBorderColor = new Color(  0, 128,   0, 255);
	private Color birthFillColor   = new Color(  0, 255,   0, 128);
	private Color birthPointColor  = new Color(  0,   0,   0, 255);
	private Color deathBorderColor = new Color(128,   0,   0, 255);
	private Color deathFillColor   = new Color(255,   0,   0, 128);
	private Color deathPointColor  = new Color(  0,   0,   0, 255);
	private Color moveBorderColor  = new Color(  0,   0,   0,   0);
	private Color moveFillColor    = new Color(  0,   0, 255,  30);
	private Color movePointColor   = new Color(  0,   0,   0,   0);
	private Color enabledColor     = new Color(  0,   0,   0, 100);
	private Color textColor		   = new Color(255, 255, 255, 255);
	
	public static void main(String[] args) {
		TouchSimulator simulator = new TouchSimulator("localhost");
		simulator.run();
	}
	
	public void run() {
		frame = new JFrame("Touch Simulator");
	    bg = new TransparentBackground();
	    frame.setUndecorated(true);
	    frame.getContentPane().add("Center",bg);
		frame.setGlassPane(this);
		frame.getGlassPane().setVisible(true);
	    frame.pack();
        Toolkit tk = Toolkit.getDefaultToolkit( );
        Dimension dim = tk.getScreenSize( );
        frame.setBounds(0, 0, dim.width, dim.height);
        frame.setVisible(true);
        frame.setState(Frame.ICONIFIED);
        Toolkit.getDefaultToolkit().addAWTEventListener(this, AWTEvent.KEY_EVENT_MASK);
	}
	
	public TouchSimulator(String address) {
		_timer = new Timer();
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		try {
			Socket socket = new Socket(address, NetworkConfiguration.PORT);
			_out = new DataOutputStream(socket.getOutputStream());
			_out.writeByte(ConnectionType.INPUT_DEVICE.value());
		} catch (UnknownHostException e) {
			System.err.println("Could not locate a server at the provided address.");
		} catch (IOException e) {
			System.err.println("Failed to connect to server.");
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Color fillColor = new Color(0, 255, 0, 128);
		Color borderColor = new Color(0, 128, 0, 255);
		Color pointColor = new Color(0, 0, 0, 255);
		
		g2.setStroke(new BasicStroke(2));
		
		// Draw events generated during recording
		for(TouchData e : _events) {
			Point p = new Point(e.x, e.y);
			SwingUtilities.convertPointFromScreen(p, this);
			
			switch(e.type) {
			case BIRTH:
				borderColor = birthBorderColor;
				fillColor = birthFillColor;
				pointColor = birthPointColor;
				break;
			case DEATH:
				borderColor = deathBorderColor;
				fillColor = deathFillColor;
				pointColor = deathPointColor;
				break;
			case MOVE:
				borderColor = moveBorderColor;
				fillColor = moveFillColor;
				pointColor = movePointColor;
				break;
			}
			
			g2.setColor(fillColor);
	        g2.fillOval(p.x - 10, p.y - 10, 20, 20);
	        g2.setColor(borderColor);
	        g2.drawOval(p.x - 10, p.y - 10, 20, 20);
	        g2.setColor(pointColor);
	        g2.fillOval(p.x-1, p.y-1, 2, 2);
	        g2.drawOval(p.x-1, p.y-1, 2, 2);
	    }
		
		// Draw shaded overlay to indicate recording is enabled
        if(_recording) {
        	g2.setColor(enabledColor);
        	g2.fillRect(0, 0, getWidth(), getHeight());
        	g2.setColor(textColor);
        	g2.drawString("Recording Multi-Touch Events...", 4, getHeight()-4);
        }
        
        // Draw points currently being played back
        for(TouchData e : _active.values()) {
			Point p = new Point(e.x, e.y);
			SwingUtilities.convertPointFromScreen(p, this);
			
			g2.setColor(birthFillColor);
	        g2.fillOval(p.x - 10, p.y - 10, 20, 20);
	        g2.setColor(birthBorderColor);
	        g2.drawOval(p.x - 10, p.y - 10, 20, 20);
	        g2.setColor(birthPointColor);
	        g2.fillOval(p.x-1, p.y-1, 2, 2);
	        g2.drawOval(p.x-1, p.y-1, 2, 2);
        }
	}
	
	public void mouseClicked(MouseEvent e) {
		if(SwingUtilities.isRightMouseButton(e)) {
			toggleMode();
		}
	}
	
	public void toggleMode() {
		if(_recording) {
			_recording = false;
			dispatchTouchEvents();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			bg.updateBackground();
		} else {
			if(frame != null) {
				bg.updateBackground();
				bg.repaint();
				frame.setVisible(true);
			}
			_recording = true;
			_active.clear();
			this.repaint();
		}

	}
	
	public void startRecording() {
		_recording = true;
		_active.clear();
		this.repaint();
	}
	
	public void endRecording() {
		_recording = false;
		dispatchTouchEvents();
	}

	public void mousePressed(MouseEvent e) {
		handleMouseEvent(e, TouchState.BIRTH);
	}

	public void mouseReleased(MouseEvent e) {
		handleMouseEvent(e, TouchState.DEATH);
	}

	public void mouseDragged(MouseEvent e) {
		handleMouseEvent(e, TouchState.MOVE);
	}

	// IGNORE
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
	
	private void handleMouseEvent(MouseEvent e, TouchState type) {
		// Ignore any input except the left mouse button
		if(!SwingUtilities.isLeftMouseButton(e)) return;
		
		// Construct the touch event
		TouchData te = new TouchData();
		te.id = (type == TouchState.BIRTH) ? ++_touchID : _touchID;
		Point p = (Point)e.getPoint().clone();
		SwingUtilities.convertPointToScreen(p, this);
		te.x = p.x;
		te.y = p.y;
		//te.x = e.getLocationOnScreen().x;
		//te.y = e.getLocationOnScreen().y;
		te.type = type;
		te.when = e.getWhen();
		
		if(_recording) {
			// Store the event to be played later
			if(type == TouchState.BIRTH) {
				te.delay = 0;
				_when = te.when;
			} else {
				te.delay = te.when - _when;
			}
			_events.add(te);
			this.repaint();
		} else {
			// Dispatch the event now
			dispatchTouchEvent(te);
			//System.out.println("dispatchTouchEvent("+te.id+", "+te.x+", "+te.y+", "+te.type+")");
		}
	}
	
	private void dispatchTouchEvents() {
		for(TouchData e : _events) {
			TouchTimerTask task = new TouchTimerTask(e);
			_timer.schedule(task, e.delay + 250);
		}
		frame.setState(Frame.ICONIFIED);
		_events.clear();
		_touchID = 0;
		repaint();
	}
	
	private void dispatchTouchEvent(TouchData e) {
        Toolkit tk = Toolkit.getDefaultToolkit( );
        Dimension dim = tk.getScreenSize( );
		try {
			_out.writeInt(1);
			_out.writeInt(e.id);
			_out.writeFloat(((float)e.x / (float)dim.width));
			_out.writeFloat(((float)e.y / (float)dim.height));
			_out.writeByte(e.type.ordinal());
		} catch (IOException e1) {
			System.err.println("Failed to send event to server.");
		}
	}

	@Override
	public void eventDispatched(AWTEvent event) {
		if(event instanceof KeyEvent) {
			KeyEvent e = (KeyEvent)event;
			if(e.getID() == KeyEvent.KEY_PRESSED) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					toggleMode();
				}
			}
		}
	}
	
	private class TouchData {
		public TouchState type;
		public int id;
		public int x;
		public int y;
		public long when;
		public long delay;
	}
	
	private class TouchDataComparator implements Comparator<TouchData> {

		public int compare(TouchData o1, TouchData o2) {
			if(o1.delay == o2.delay)
				if(o1.when < o2.when)
					return -1;
				else
					return 1;
			else if(o1.delay < o2.delay)
				return -1;
			else
				return 1;
		}
		
	}
	
	private class TouchTimerTask extends TimerTask {
		TouchData e;
		
		public TouchTimerTask(TouchData e) {
			this.e = e;
		}

		@Override
		public void run() {
			dispatchTouchEvent(e);
			if(e.type != TouchState.DEATH) {
				_active.put(e.id, e);
			} else {
				_active.remove(e.id);
			}
			repaint();
		}
	}
	
	public static class TransparentBackground extends JComponent {
		private static final long serialVersionUID = 4722190557116388056L;
		private Image background;

		public TransparentBackground() {
		    updateBackground( );
		}
	
		public void updateBackground( ) {
		    try {
		        Robot rbt = new Robot( );
		        Toolkit tk = Toolkit.getDefaultToolkit( );
		        Dimension dim = tk.getScreenSize( );
		        background = rbt.createScreenCapture(
		        new Rectangle(0,0,(int)dim.getWidth( ),
		                          (int)dim.getHeight( )));
		    } catch (Exception ex) {
		        ex.printStackTrace( );
		    }
		}
		
		public void paintComponent(Graphics g) {
		    Point pos = this.getLocationOnScreen( );
		    Point offset = new Point(-pos.x,-pos.y);
		    g.drawImage(background,offset.x,offset.y,null);
		}
	}
}
