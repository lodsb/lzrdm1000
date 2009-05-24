package Sequencer;

public interface SequenceContainerInterface extends SequenceInterface {
	public void appendSequence(SequenceInterface sequence);
	public void prependSequence(SequenceInterface sequence);
}
