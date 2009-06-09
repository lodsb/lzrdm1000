package Synth;

import de.sciss.jcollider.ControlDesc;

public class SynthInfo {
	private String name;
	private String description;
	private ControlDesc[] parameters;
	
	public SynthInfo(String name, String description, ControlDesc[] controlParameters) {
		this.name = name;
		this.description = description;
		this.parameters = controlParameters;
	}
	
	String getName() {
		return name;
	}
	
	String getDescription() {
		return description;
	}
	
	ControlDesc[] getControlParameters() {
		return parameters;
	}
}
