package message.communication;

import message.ProcessorInterface;

public interface OutputServerInterface<MessageType> extends ProcessorInterface {
	public void post(MessageType m);
}
