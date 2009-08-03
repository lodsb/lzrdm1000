package Sequencer;

import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import Sequencer.SequenceEvent.SequenceEventSubtype;
import Sequencer.SequenceEvent.SequenceEventType;
import Sequencer.SequencerEvent.SequencerEventSubtype;
import Sequencer.SequencerEvent.SequencerEventType;

import com.trolltech.qt.QSignalEmitter.Signal1;
import com.trolltech.qt.core.QObject;

import Control.ControlServer;

public class Sequencer extends QObject implements Runnable, SequencerInterface {
	
	private SequenceContainerInterface mainSequence;
	private ControlServer controlServer;
	private boolean isRunning  = false;
	
	
	// for thread synchronization & signals
	public Signal1<Long> globalTickSignal = new Signal1<Long>();
	public Signal1<SequencerEvent> sequencerEventSignal = new Signal1<SequencerEvent>();
	
	private Semaphore globalTickSyncSemaphore = new Semaphore(0);
	private ConcurrentLinkedQueue<Long> passedTickList = new ConcurrentLinkedQueue<Long>();
	private ConcurrentLinkedQueue<SequenceEvent> passedSequenceEventList = new ConcurrentLinkedQueue<SequenceEvent>();
	private ConcurrentLinkedQueue<SequencerEvent> passedSequencerEventList = new ConcurrentLinkedQueue<SequencerEvent>();
	
	private CopyOnWriteArrayList<SequencePlayer> sequencePlayers = new CopyOnWriteArrayList<SequencePlayer>();
	
	public void postSequenceEvent(SequenceEvent sequenceEvent) {
		passedSequenceEventList.offer(sequenceEvent);
	}
	
	private void postSequencerEvent(SequencerEvent sequencerEvent) {
		passedSequencerEventList.offer(sequencerEvent);
	}
	
	public Sequencer(ControlServer controlServer) {
		this.controlServer= controlServer;
		
		this.mainSequence = new ParallelSequenceContainer(this);
		
		Thread thread = new Thread(this);
		this.moveToThread(thread);
		thread.start();
	}
	
	private Sequencer(SequenceContainerInterface mainSequence, ControlServer controlServer) {
		this.controlServer= controlServer;
		this.mainSequence = mainSequence;
		
		Thread thread = new Thread(this);
		this.moveToThread(thread);
		thread.start();
	}

	public SequencePlayer createAndAddSequencePlayer() {
		SequencePlayer sp = new SequencePlayer(this);
		sequencePlayers.add(sp);
		mainSequence.appendSequence(sp);
		
		this.postSequencerEvent(new SequencerEvent(SequencerEventType.SEQUENCE_PLAYER_ADDED, SequencerEventSubtype.SEQUENCE_PLAYER, sp));
		
		return sp;
	}
	
	public void removeSequencePlayer(SequencePlayer sp) {
		sp.reset();
		mainSequence.removeSequence(sp);
		sequencePlayers.remove(sp);
		
		this.postSequencerEvent(new SequencerEvent(SequencerEventType.SEQUENCE_PLAYER_REMOVED, SequencerEventSubtype.SEQUENCE_PLAYER, sp));
	}
	
	public List<SequencePlayer> getSequencePlayers() {
		return (List<SequencePlayer>)sequencePlayers.clone();
	}
	
	public boolean processTick(long tick) {
			isRunning = mainSequence.eval(tick);

			controlServer.flushMessages();
			passedTickList.offer(tick);

			globalTickSyncSemaphore.release();
		return isRunning;
	}
	
	public void run() {
		while(true) {
				try {
					globalTickSyncSemaphore.acquire();
					
					Long passedTick;
					while((passedTick = passedTickList.poll()) != null) {
						globalTickSignal.emit(passedTick);
					}
						
					// send update signals of the respective sequences to the i.e. GUI thread
					// the list of sent messages is a lock-free one!
					ConcurrentLinkedQueue<ControlServer.SentMessage> queue = controlServer._getRecentMessages();
					ControlServer.SentMessage sentMessage;
					
					while((sentMessage = queue.poll()) != null) {
						sentMessage.sequence._pumpSequenceEval(sentMessage.localTick);
					}
					
					SequenceEvent se;
					
					while((se = passedSequenceEventList.poll()) != null) {
						se.getSource()._pumpSequenceEvent(se);
					}
					
					SequencerEvent sre;
					
					while((sre = passedSequencerEventList.poll()) != null) {
						this.sequencerEventSignal.emit(sre);
					}
					
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void reset() {
		if(mainSequence != null) {
			this.mainSequence.reset();
		}
	}

	public long sizeOfAllSequences() {
		if(mainSequence != null) {
			return mainSequence.size();
		} else {
			return 0;
		}
	}
}
