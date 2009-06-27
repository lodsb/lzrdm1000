package Sequencer;

import com.trolltech.qt.QSignalEmitter.Signal1;
import com.trolltech.qt.core.QObject;

public class Pause extends QObject implements SequenceInterface {

	private Signal1<Long> evalSignal = new Signal1<Long>();
	@Override
	public Signal1<Long> getSequenceEvalUpdateSignal() {
		return evalSignal;
	}
	
	private long pauseTicks;
	private long runTicks = 0;
	
	public Pause(long pauseTicks) {
		this.pauseTicks = pauseTicks;
	}
	
	@Override
	public boolean eval(long tick) {
		if(runTicks != pauseTicks) {
			runTicks++;
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean isRunning() {
		return (runTicks == pauseTicks);
	}

	@Override
	public void reset() {
		runTicks = 0;
	}

	@Override
	public long size() {
		return pauseTicks;
	}

	@Override
	public SequenceInterface deepCopy() {
		// TODO Auto-generated method stub
		return null;
	}

}
