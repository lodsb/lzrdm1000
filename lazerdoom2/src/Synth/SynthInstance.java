package Synth;

import lazerdoom.LzrDmObjectInterface;
import de.sciss.jcollider.ControlDesc;
import de.sciss.jcollider.Synth;
import Control.ControlServer;
import Control.ParameterControlBus;
import Control.Types.*;

public class SynthInstance implements LzrDmObjectInterface {
	
	private SynthInfo info;
	private ParameterControlBus[] controlBusses;
	public 
	
	SynthInstance(ControlServer server, SynthInfo info, Synth synth) {
		this.info = info;
		
		controlBusses = new ParameterControlBus[info.getControlParameters().length];
		
		int i = 0;
		for(ControlDesc desc: info.getControlParameters()) {
			controlBusses[i] = new ParameterControlBus<DoubleType>(server, desc, synth);
			i++;
		}
	}
	
	public SynthInfo getSynthInfo() {
		return this.info;
	}
	
	public ParameterControlBus[] getControlBusses() {
		return this.controlBusses;
	}
}
