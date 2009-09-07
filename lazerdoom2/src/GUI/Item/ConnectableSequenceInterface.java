package GUI.Item;

import Sequencer.BaseSequence;

public interface ConnectableSequenceInterface {
	public SequenceConnector getSequenceInConnector();
	public SequenceConnector getSequenceOutConnector();
	public BaseSequence getBaseSequence();
}
