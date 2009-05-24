package Sequencer;

public class Pause implements SequenceInterface {

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

}
