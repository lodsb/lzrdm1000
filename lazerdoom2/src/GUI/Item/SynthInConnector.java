package GUI.Item;

import Synth.SynthInstance;
import Control.ParameterControlBus;

public class SynthInConnector extends SynthConnector {
	private String parameterName;
	private ParameterControlBus parameterControlBus;
	private SynthInstance synthInstance;
	
	public SynthInConnector(String parameterName, ParameterControlBus controlBus, SynthInstance synth) {
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
