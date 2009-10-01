package GUI.Item;

import lazerdoom.LzrDmObjectInterface;

public class SynthOutConnector extends SynthConnector implements LzrDmObjectInterface {

	private String parameterName; 

	public SynthOutConnector(String parameterName) {
		this.parameterName = parameterName;
		super.setIsInPort(false);
	}

	public String getParameterName() {
		return this.parameterName;
	}
}
