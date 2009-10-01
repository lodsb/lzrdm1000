package GUI.Item;

import lazerdoom.LzrDmObjectInterface;
import Sequencer.BaseSequence;

public interface ConnectableSequenceInterface extends LzrDmObjectInterface {
	public SequenceConnector getSequenceInConnector();
	public SequenceConnector getSequenceOutConnector();
	public BaseSequence getBaseSequence();
}
