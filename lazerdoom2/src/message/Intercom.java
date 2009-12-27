package message;

import java.util.List;

import sparshui.client.Client;
import sparshui.common.Event;
import sparshui.common.Location;

import com.trolltech.qt.QThread;
import com.trolltech.qt.core.QObject;

public class Intercom extends QObject {
	private static Intercom instance;
	
	public Intercom() {
		instance = this;
	}
	
	public System system = new System();
	
	public static Intercom getInstance() {
		return instance;
	}

	public class System extends QObject {
		
		public class GestureInput extends QObject implements Client {
			
			public GestureInput() {
				this.setupControlInputThread();
			}
			
			private class ControlInputThread extends QObject implements Runnable {
				Scheduler commIOScheduler = new Scheduler();
				
				@Override
				public void run() {
					while(true) {
						try {
							Thread.sleep(2);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						commIOScheduler.process();
					}
				}
				
			}
	
			public class ProcessEvent {
				ProcessEvent(int id, Event event) {
					this.id = id;
					this.event = event;
				}
				public int id;
				public Event event;
			}
			
			private ControlInputThread controlInputThread = new ControlInputThread();
			public ThreadXBarSlotted<Location, Integer> groupIDXBar = new ThreadXBarSlotted<Location, Integer>();
			public ThreadXBarSlotted<Integer, List<Integer>> allowedGesturesXBar = new ThreadXBarSlotted<Integer, List<Integer>>();
			public ThreadComSlotted<ProcessEvent> processEventCom = new ThreadComSlotted<ProcessEvent>();
			
			private void setupControlInputThread() {
				//groupIDXBar.executeSignal.connect(this, "getGroupIDSlot(Object, Object)");
				//allowedGesturesXBar.executeSignal.connect(this, "getAllowedGesturesSlot(Object, Object)");
				//processEventCom.executeSignal.connect(this, "processEventSlot(Object)");
				
				this.controlInputThread.commIOScheduler.registerProcessor(groupIDXBar);
				this.controlInputThread.commIOScheduler.registerProcessor(allowedGesturesXBar);
				this.controlInputThread.commIOScheduler.registerProcessor(processEventCom);
				
				QThread comThread = new QThread(controlInputThread);
				//controlInputThread.moveToThread(this.thread());
				groupIDXBar.moveToThread(comThread);
				allowedGesturesXBar.moveToThread(comThread);
				processEventCom.moveToThread(comThread);
				
				comThread.start();
				
			}

			@Override
			public List<Integer> getAllowedGestures(int groupID) {
				return this.allowedGesturesXBar.get(new Integer(groupID));
			}

			@Override
			public int getGroupID(Location location) {
				// TODO Auto-generated method stub
				return this.groupIDXBar.get(location);
			}

			@Override
			public void processEvent(int groupID, Event event) {
				this.processEventCom.post(new ProcessEvent(groupID, event));
			}
		}
	
		public GestureInput gestureInput = new GestureInput();
	}
}
