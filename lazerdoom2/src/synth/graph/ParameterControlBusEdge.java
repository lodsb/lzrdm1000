package synth.graph;

import control.ParameterControlBus;

public class ParameterControlBusEdge {
	private ParameterControlBus parameterConcolBus;
	
	public ParameterControlBusEdge(ParameterControlBus bus) {
		this.parameterConcolBus = bus;
	}
	
	public ParameterControlBus getParameterControlBus() {
		return this.parameterConcolBus;
	}

}
