package gui.item;

import sequencer.BaseSequence;
import lazerdoom.LzrDmObjectInterface;

public interface ConnectableSequenceInterface extends LzrDmObjectInterface {
	public SequenceConnector getSequenceInConnector();
	public SequenceConnector getSequenceOutConnector();
	public BaseSequence getBaseSequence();
}
