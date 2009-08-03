package Sequencer;

import com.trolltech.qt.core.QObject;

public class SequenceController extends QObject {
	private Sequencer sequencer;
	
	public SequenceController(Sequencer sequencer) {
		this.sequencer = sequencer;
	}
	
	public void connectToGlobalTickSignal(Object slotObject , String method) {
		this.sequencer.globalTickSignal.connect(slotObject, method);
	} 
	
	public void connectToSequenceLocalTickSignal(BaseSequence si, SequenceEvalListenerInterface svali) {
		si.registerSequenceEvalListener(svali);
	}
	
	public void registerSequenceInterfaceEventListener(BaseSequence si, SequenceEventListenerInterface seli) {
		si.registerSequenceEventListener(seli);
	}
	
	public void registerSequenceInterfaceEventListenerToSequencer(SequencerEventListenerInterface sei) {
		this.sequencer.sequencerEventSignal.connect(sei, "dispatchSequencerEvent(SequencerEvent)");
	}
	
	
}
