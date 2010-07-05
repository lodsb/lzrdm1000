package message;

import java.util.List;
import java.util.concurrent.Semaphore;

import sequencer.SequenceEvalListenerInterface;
import sequencer.SequenceEvent;
import sequencer.SequenceEventListenerInterface;
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
	
	public IntercomSystem system = new IntercomSystem();
	
	public static Intercom getInstance() {
		return instance;
	}

	public class IntercomSystem extends QObject {
		
		public class GestureInput extends QObject implements Client {
			
			public GestureInput() {
				this.setupControlInputThread();
			}
			
			private class ControlInputThread extends QObject implements Runnable {
				private Semaphore sema = new Semaphore(1);
				Scheduler commIOScheduler = new Scheduler(sema);
				
				
				@Override
				public void run() {
					while(true) {
						try {
							//synchronized(monitor) {
								sema.acquire();
							//}
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
		
		
		public class SequenceEventDispatcher extends QObject {
			
			public SequenceEventDispatcher() {
				this.setupSequenceEventDispatchThread();
			}
			
			private class SequenceEventThread extends QObject implements Runnable {
				private Semaphore sema = new Semaphore(1);
				Scheduler seqEventScheduler = new Scheduler(sema);
				
				
				@Override
				public void run() {
					while(true) {
						try {
							//synchronized(monitor) {
								sema.acquire();
							//}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						seqEventScheduler.process();
					}
				}
				
			}
	
			public class SequenceEventContainer {
				public SequenceEventContainer(SequenceEventListenerInterface s, SequenceEvent e) {
					this.se = e;
					this.seli = s;
				} 
				
				public SequenceEventContainer(SequenceEvalListenerInterface s, long t) {
					this.svali = s;
					this.tick = t;
				}
				
				public SequenceEventListenerInterface seli;
				public SequenceEvalListenerInterface svali;
				public long tick;
				public SequenceEvent se;
			}
			
			private SequenceEventThread sequenceEventThread = new SequenceEventThread();
			//public ThreadXBarSlotted<Location, Integer> groupIDXBar = new ThreadXBarSlotted<Location, Integer>();
			//public ThreadXBarSlotted<Integer, List<Integer>> allowedGesturesXBar = new ThreadXBarSlotted<Integer, List<Integer>>();
			public ThreadComSlotted<SequenceEventContainer> sequenceEventCom = new ThreadComSlotted<SequenceEventContainer>();
			public ThreadComSlotted<SequenceEventContainer> sequenceEvalCom = new ThreadComSlotted<SequenceEventContainer>();
			
			private void setupSequenceEventDispatchThread() {
				this.sequenceEventThread.seqEventScheduler.registerProcessor(sequenceEvalCom);
				this.sequenceEventThread.seqEventScheduler.registerProcessor(sequenceEventCom);
				
				QThread seqEventThread = new QThread(sequenceEventThread);
				sequenceEvalCom.moveToThread(seqEventThread);
				sequenceEventCom.moveToThread(seqEventThread);
				
				seqEventThread.start();
			}

			public void propagateSequenceEvent(SequenceEventListenerInterface seli, SequenceEvent se) {
				this.sequenceEventCom.post(new SequenceEventContainer(seli, se));
			}
			
			public void propagateSequenceEval(SequenceEvalListenerInterface svali, long tick) {
				this.sequenceEvalCom.post(new SequenceEventContainer(svali, tick));
			}
			
		}

		public SequenceEventDispatcher sequenceEventDispatch = new SequenceEventDispatcher();
	}
}
