package GUI.Item;

import lazerdoom.LzrDmObjectInterface;
import Session.SessionHandler;
import Synth.SynthInstance;
import Control.ParameterControlBus;

public class SynthInConnector extends SynthConnector implements LzrDmObjectInterface {
	private String parameterName;
	private ParameterControlBus parameterControlBus;
	private SynthInstance synthInstance;
	
	public SynthInConnector(String parameterName, ParameterControlBus controlBus, SynthInstance synth) {
		// TODO: add register-process to command
		SessionHandler.getInstance().registerObject(this);
		this.parameterName = parameterName;
		this.parameterControlBus = controlBus;
		this.synthInstance = synth;
		super.setIsInPort(true);
	}

	public String getParameterName() {
		return this.parameterName;
	}
	
	public ParameterControlBus getParameterControlBus() {
		return this.parameterControlBus;
	}
	
	public SynthInstance getSynthInstance() {
		return this.synthInstance;
	}
}
