package GUI.Item;

import Session.SessionHandler;
import lazerdoom.LzrDmObjectInterface;

public class SynthOutConnector extends SynthConnector implements LzrDmObjectInterface {

	private String parameterName; 

	public SynthOutConnector(String parameterName) {
		// TODO: put the register-process into a command
		SessionHandler.getInstance().registerObject(this);
		
		this.parameterName = parameterName;
		super.setIsInPort(false);
	}

	public String getParameterName() {
		return this.parameterName;
	}
}
