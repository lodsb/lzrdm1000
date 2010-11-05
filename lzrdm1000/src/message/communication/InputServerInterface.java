package message.communication;

import message.ProcessorInterface;

public interface InputServerInterface<MessageType> extends ProcessorInterface {
	public MessageType getNextMessage(); 
}
