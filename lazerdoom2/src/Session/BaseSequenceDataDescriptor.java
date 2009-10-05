package Session;

import Sequencer.BaseSequence;
import lazerdoom.Core;
import lazerdoom.LzrDmObjectInterface;

public class BaseSequenceDataDescriptor {
	long sequenceID;
	long length;
	
	public BaseSequenceDataDescriptor(BaseSequence sequence) {
		this.sequenceID = SessionHandler.getInstance().getRegisteredObjectID(sequence);
		this.length = Core.getInstance().oneBarInPPQ();
	}
	
	public long getLength() {
		return this.length;
	}
	
	protected long getSequenceID() {
		return this.sequenceID;
	}
	
	void gatherData() {
		BaseSequence sequence = (BaseSequence) SessionHandler.getInstance().getRegisteredObject(sequenceID);
		this.length = ((BaseSequence)sequence).size();
	}
}
