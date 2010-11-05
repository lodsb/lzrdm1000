package sparshui.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;

import sparshui.common.Location;
import sparshui.common.TouchState;

/**
 * Represents a connection to the input device.
 * 
 * @author Tony Ross
 *
 */
public class InputDeviceConnection implements Runnable {
	
	/**
	 * 
	 */
	private GestureServer _gestureServer;
	
	/**
	 * 
	 */
	private Socket _socket;
	
	/**
	 * 
	 */
	private DataInputStream _in;
	
	/**
	 * 
	 */
	private HashMap<Integer, TouchPoint> _touchPoints;

	private Vector<Integer> _flaggedids;
	
	/**
	 * Create a new input device connection with the given
	 * gesture server and socket.
	 * 
	 * @param gestureServer
	 * 		The gesture server.
	 * @throws IOException 
	 * 		If there is a communication error.
	 */
	public InputDeviceConnection(GestureServer gestureServer, Socket socket) throws IOException {
		_gestureServer = gestureServer;
		_socket = socket;
		_in = new DataInputStream(socket.getInputStream());
		_touchPoints = new HashMap<Integer, TouchPoint>();
		_flaggedids = new Vector<Integer>();
		startListening();
	}
	
	/**
	 * Create a new touch point.
	 * 
	 * @param x
	 * @param y
	 * @param state
	 * @return
	 */
	private TouchPoint createTouchPoint(Location location) {
		TouchPoint touchPoint = new TouchPoint(location);
		_gestureServer.processBirth(touchPoint);
		//System.out.println("[InputDeviceConnection] Successful Create Location: " + location.toString());
		return touchPoint;
	}
	
	/**
	 * 
	 */
	private void removeDeadTouchPoints() {
		for (int id : _flaggedids) {
			_touchPoints.remove(id);
		}
		_flaggedids.clear();
	}
	
	/**
	 * 
	 * @param id
	 */
	private void flagTouchPointForRemoval(int id) {
		_flaggedids.add(id);
	}
	
	/**
	 * 
	 */
	private void processTouchPoint(int id, Location location, TouchState state) {
		if(_touchPoints.containsKey(id)) {
			TouchPoint touchPoint = _touchPoints.get(id);
			synchronized(touchPoint) {
				touchPoint.update(location, state);
			}
		} else {
			//System.out.println("[InputDeviceConnection] Creating new touch point");
			_touchPoints.put(id, createTouchPoint(location));
		}
	}
	
	/**
	 * 
	 */
	private void receiveData() {
		try {
			while(!_socket.isInputShutdown()) {
				readTouchPoints();
			}
		} catch (IOException e) {
			//System.err.println("Error reading touch point from input device.");
			//e.printStackTrace();
			System.out.println("[GestureServer] InputDevice Disconnected");
			//TODO figure out some way to destroy the object
		}
	}
	
	/**
	 * 
	 * @throws IOException
	 */
	private void readTouchPoint() throws IOException {
		int id = _in.readInt();
		float x = _in.readFloat();
		float y = _in.readFloat();
		Location location = new Location(x, y);
		TouchState state = TouchState.values()[(int)_in.readByte()];
		processTouchPoint(id, location, state);

		// DEBUG
		//System.out.println("[InputDeviceConnection] ID: " + id + " STATE: " +state.name() + 
		//		" X: " + x + " Y: " + y);
		
		if (state == TouchState.DEATH) {
			flagTouchPointForRemoval(id);
		}
	}
	
	/**
	 * 
	 * @throws IOException
	 */
	private void readTouchPoints() throws IOException {
		//int count = _in.readInt();
		
		// With Count
		int count = _in.readInt();
		if(count < 0) {
			_in.close();
			return;
		}
		//System.out.println("Reading '"+count+"' Input Events.");
		for(int i = 0; i < count; i++) {
			readTouchPoint();
		}
		removeDeadTouchPoints();

		/*
		// Without Count
		while(true) {
			readTouchPoint();
			removeDeadTouchPoints();
		}
		*/
	}
	
	/**
	 * 
	 */
	private void startListening() {
		Thread thread = new Thread(this);
		thread.start();
	}

	/**
	 * Begin receiving data from the input device.
	 */
	@Override
	public void run() {
		receiveData();
	}

	/*
	public float readFloat() throws IOException{
    	return Float.intBitsToFloat(readInt());
    }

    public int readInt() throws IOException{
    	int n = 0;
    	for(int i = 0; i < 4; i++) {
    		n += _in.read() << (i*8);
    	}
    	return n;
    }
    */
	
}
