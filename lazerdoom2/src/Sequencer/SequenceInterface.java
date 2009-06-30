package Sequencer;

import com.trolltech.qt.QSignalEmitter.Signal1;

public interface SequenceInterface {
	public boolean isRunning();
	public long size();

	boolean eval(long tick);
	void reset();
	
	SequenceInterface deepCopy();
}
