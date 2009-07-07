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
	
	public void connectToSequenceLocalTickSignal(BaseSequence si, Object slotObject, String method) {
		si.getSequenceEvalUpdateSignal().connect(slotObject, method);
	}
	
	public void registerSequenceInterfaceEventListener(BaseSequence si, SequenceEventListenerInterface sei) {
		si.getSequenceEventSignal().connect(sei, "dispatchSequenceEvent(SequenceEvent)");
	}
	
	public void registerSequenceInterfaceEventListenerToSequencer(SequencerEventListenerInterface sei) {
		this.sequencer.sequencerEventSignal.connect(sei, "dispatchSequencerEvent(SequencerEvent)");
	}
	
	
}
