package Sequencer;

public interface SequenceInterface {
	boolean eval(long tick);
	boolean isRunning();
	long size();
	void reset();
	
	SequenceInterface deepCopy();
}
