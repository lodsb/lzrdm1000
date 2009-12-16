package sequencer;

public interface SequencePlayerInterface extends SequenceInterface {
	public void setSequence(SequenceInterface sequence);
	public SequenceInterface getSequence();
	
	public void startSequenceImmidiately();
	public void stopSequenceImmidiately();
	
	public void scheduleStart(long ticks);
	public void scheduleStop(long ticks);
}
