package lazerdoom;

import sparshui.client.Client;
import sparshui.common.Event;
import sparshui.common.Location;
import sparshui.common.TouchState;

import sparshui.server.*;
import java.util.HashMap;

import java.util.List;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class TouchHandler implements Runnable {
	private Client mainClient;

	private ConcurrentHashMap<Integer, TouchPoint> activeTouchPoints = new ConcurrentHashMap<Integer, TouchPoint>();
	private ConcurrentHashMap<Integer, GestureGroup> activeGroups = new ConcurrentHashMap<Integer, GestureGroup>();


	private boolean garbageCollect = false;
	private long garbageCollectInterval = 0;
	private Thread garbageCollectionThread;

	private Object monitor = new Object();

	public TouchHandler(Client mc) {
		this.mainClient = mc;

		if(LazerdoomConfiguration.getInstance().garbageCollectZombieTuioBlobs) {
			this.garbageCollect = true;
			this.garbageCollectInterval = LazerdoomConfiguration.getInstance().maxUpdateIntervalUntilGarbageCollect;

			this.garbageCollectionThread = new Thread(this);
			this.garbageCollectionThread.start();
		}
	}

	public void setMainClient(Client mc) {
		this.mainClient = mc;
	}

	public void addTouchPoint(int id, float x, float y) {
		synchronized(monitor) {
			Location loc = new Location(x,y);
			TouchPoint tp = new TouchPoint(id, loc);

			int groupID = mainClient.getGroupID(loc);
			GestureGroup group = this.getGroup(groupID);
			tp.setGroup(group);

			activeTouchPoints.put(id, tp);
		}
	}

	public void removeTouchPoint(int id, float x, float y) {
		synchronized(monitor) {
			System.err.println("rm ?"+id);
			TouchPoint tp;
			if((tp = activeTouchPoints.get(id))!=null) {
				System.err.println("rm !"+tp+" "+tp.getID());
				tp.update(new Location(x,y), TouchState.DEATH);
				activeTouchPoints.remove(id);
			}
		}
	}

	public void updateTouchPoint(int id, float x, float y) {
		synchronized(monitor) {
			TouchPoint tp;
			if((tp = activeTouchPoints.get(id))!=null) {
				tp.update(new Location(x,y), TouchState.MOVE);
			}		
		}
	}

	private GestureGroup getGroup(int groupID) {
		GestureGroup group;

		synchronized(monitor) {
			if((group = activeGroups.get(groupID)) == null) {
				List<Integer> allowedGestures = mainClient.getAllowedGestures(groupID);
				group = new GestureGroup(groupID, allowedGestures, this);
				activeGroups.put(groupID, group);
			}

		}
		return group;
	}

	public void processEvents(int groupID, Vector<Event> events) {
		for(Event e: events) {
			mainClient.processEvent(groupID, e);
		}
	}

	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(this.garbageCollectInterval);

				TouchPoint tp;

				for(Entry<Integer, TouchPoint> e : this.activeTouchPoints.entrySet()) {
					tp = e.getValue();

					if(tp.getUpdateIntervallMillis() < this.garbageCollectInterval && tp.garbageCollectChecked()) {
						System.err.println("killll "+tp);
						this.removeTouchPoint(tp.getID(), tp.getLocation().getX(), tp.getLocation().getY());
					}
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


}
