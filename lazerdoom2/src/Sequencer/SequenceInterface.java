package Sequencer;

import com.trolltech.qt.QSignalEmitter.Signal1;

public interface SequenceInterface {
	boolean eval(long tick);
	boolean isRunning();
	long size();
	void reset();
	
	SequenceInterface deepCopy();
	
	// for gui synchronization
	
	Signal1<Long> getSequenceEvalUpdateSignal();
}
