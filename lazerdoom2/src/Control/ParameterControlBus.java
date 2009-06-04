package Control;

import de.sciss.jcollider.Synth;
import de.sciss.net.OSCMessage;
import Control.Types.BaseType;

public class ParameterControlBus<T extends BaseType> implements ControlBusInterface<T> {

	protected ControlServer server;
	protected Synth synth;
	protected String parameter;
	
	ParameterControlBus(ControlServer server, Synth synth, String parameter) {
		this.server = server;
		this.synth = synth;
		this.parameter = parameter;
	}
	
	@Override
	public void setValue(T baseType) {
		server.appendMessage(synth.setMsg(this.parameter, baseType.getFloatValue()));
		
	}

	@Override
	public void setSynthAndParameter(Synth synth, String parameter) {
		this.synth = synth;
		this.parameter = parameter;
	}

}
