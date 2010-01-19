package sequencer;

public interface ClockInterface {
	public void setInterval(long interval);
	public void setSequencer(SequencerInterface sequencer);
	public void start();
	public void stopClock();
}
