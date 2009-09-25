package Sequencer;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import Control.Types.DoubleType;
import Control.Types.NoteType;
import Sequencer.Graph.SequenceGraph;
import Sequencer.SequenceEvent.SequenceEventSubtype;
import Sequencer.SequencerEvent.SequencerEventSubtype;
import Sequencer.SequencerEvent.SequencerEventType;

import com.trolltech.qt.core.QObject;

import edu.uci.ics.jung.graph.util.Pair;

public class SequenceController extends QObject {
	private Sequencer sequencer;
	private SequenceGraph graph;
	private ParallelSequenceContainer rootSequence;
	
	private static SequenceController instance = null;
	
	public static SequenceController getInstance() {
		return instance;
	}
	
	public SequenceController(Sequencer sequencer, SequenceGraph graph) {
		this.sequencer = sequencer;
		this.graph = graph;
		this.rootSequence = new ParallelSequenceContainer(sequencer);
		
		this.sequencer.setRootSequence(this.rootSequence);
		
		instance = this;
	}
	
	public String toString() {
		return ""+this.rootSequence;
	}
	
	public boolean removeBaseSequence(BaseSequence sequence) {
		LinkedList<Pair<SequenceInterface>> connections = graph.remove(sequence);
		boolean ret = false;
		
		if(connections != null) {
			graph.updateStructure(rootSequence, sequencer);
			
			if(sequence instanceof SequencePlayer) {
				sequencer.postSequencerEvent(new SequencerEvent(SequencerEventType.SEQUENCE_PLAYER_REMOVED, SequencerEventSubtype.SEQUENCE_PLAYER, sequence));
			} else if(sequence instanceof EventPointsSequence){
				sequencer.postSequencerEvent(new SequencerEvent(SequencerEventType.EVENT_POINTS_SEQUENCE_REMOVED, SequencerEventSubtype.EVENT_POINTS_SEQUENCE, sequence));
			} else if(sequence instanceof Pause) {
				sequencer.postSequencerEvent(new SequencerEvent(SequencerEventType.PAUSE_REMOVED, SequencerEventSubtype.PAUSE, sequence));
			}
			
			for(Pair<SequenceInterface> connection: connections) {
				sequencer.postSequencerEvent(new SequencerEvent(SequencerEventType.CONNECTION_REMOVED, SequencerEventSubtype.SEQUENCE_PAIR, connection));
			}
			
			ret = true;
		}
		
		return ret;
	}
	
	public EventPointsSequence<DoubleType> createDoubleTypeEventPointsSequence() {
		EventPointsSequence<DoubleType> e = new EventPointsSequence<DoubleType>(sequencer);
		
		sequencer.postSequencerEvent(new SequencerEvent(SequencerEventType.EVENT_POINTS_SEQUENCE_ADDED, SequencerEventSubtype.EVENT_POINTS_SEQUENCE, e));
		
		return e;
	}
	
	public EventPointsSequence<NoteType> createNoteTypeEventPointsSequence() {
		EventPointsSequence<NoteType> e = new EventPointsSequence<NoteType>(sequencer);
		
		sequencer.postSequencerEvent(new SequencerEvent(SequencerEventType.EVENT_POINTS_SEQUENCE_ADDED, SequencerEventSubtype.EVENT_POINTS_SEQUENCE, e));
		
		return e;
	}
	
	public SequencePlayer createSequencePlayer() {
		SequencePlayer sp = new SequencePlayer(sequencer);

		sequencer.postSequencerEvent(new SequencerEvent(SequencerEventType.SEQUENCE_PLAYER_ADDED, SequencerEventSubtype.SEQUENCE_PLAYER, sp));
		
		return sp;
	}
	
	public Pause createPauseSequence(long pauseTicks) {
		Pause p = new Pause(sequencer, pauseTicks);
		
		sequencer.postSequencerEvent(new SequencerEvent(SequencerEventType.PAUSE_ADDED, SequencerEventSubtype.PAUSE, p));
		
		return p;
	}
	public boolean connectSequences(SequenceInterface source, SequenceInterface target) {
		boolean ret = false;
		
		if(!(target instanceof SequencePlayerInterface)) {
			ret = graph.connect(source, target);

			if(ret) {
				graph.updateStructure(rootSequence, sequencer);
				sequencer.postSequencerEvent(new SequencerEvent(SequencerEventType.CONNECTION_ADDED, SequencerEventSubtype.SEQUENCE_PAIR, new Pair<SequenceInterface>(source, target)));
			}
		}
		System.out.println("connect "+ret);
		return ret;
	}
	
	public boolean disconnectSequences(SequenceInterface source, SequenceInterface target) {
		boolean ret = false;
		
		ret = graph.disconnect(source, target);
		
		if(ret) {
			graph.updateStructure(rootSequence, sequencer);
		}
		
		return ret;
	}
	
	public void connectToGlobalTickSignal(Object slotObject , String method) {
		this.sequencer.globalTickSignal.connect(slotObject, method);
	} 
	
	public void connectToSequenceLocalEval(BaseSequence si, SequenceEvalListenerInterface svali) {
		si.registerSequenceEvalListener(svali);
	}
	
	public void registerSequenceInterfaceEventListener(BaseSequence si, SequenceEventListenerInterface seli) {
		si.registerSequenceEventListener(seli);
	}
	
	public void registerSequenceInterfaceEventListenerToSequencer(SequencerEventListenerInterface sei) {
		this.sequencer.registerSequencerEventListener(sei);
	}
	
	
}
