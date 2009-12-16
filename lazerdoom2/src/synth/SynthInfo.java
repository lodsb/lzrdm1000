package synth;

import control.ControlServer;
import lazerdoom.LzrDmObjectInterface;
import de.sciss.jcollider.ControlDesc;

public class SynthInfo implements LzrDmObjectInterface {
	private String name;
	private String description;
	private ControlDesc[] parameters;
	
	public SynthInfo(String name, String description, ControlDesc[] controlParameters) {
		this.name = name;
		this.description = description;
		this.parameters = controlParameters;
	}
	
	private boolean isHidden = false;
	public boolean isHidden() {
		return isHidden;
	}
	
	public void setHidden() {
		this.isHidden = true;
	}
	
		
	public SynthInstance createNewInstance(ControlServer cs){
		return null;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public ControlDesc[] getControlParameters() {
		return parameters;
	}
	
	public String toString() {
		return "Name: "+this.name+"\nDescription: "+this.description+"\nParameters: "+this.parameters;
	}
	
	public String getUniqueIDString() {
		return this.name+this.description;
	}
}
