package Synth.Graph;

import Control.ParameterControlBus;

public class ParameterControlBusEdge {
	private ParameterControlBus parameterConcolBus;
	
	public ParameterControlBusEdge(ParameterControlBus bus) {
		this.parameterConcolBus = bus;
	}
	
	public ParameterControlBus getParameterControlBus() {
		return this.parameterConcolBus;
	}

}
