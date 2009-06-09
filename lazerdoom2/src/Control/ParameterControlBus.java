package Control;

import de.sciss.jcollider.ControlDesc;
import de.sciss.jcollider.Synth;
import de.sciss.net.OSCMessage;
import Control.Types.BaseType;

public class ParameterControlBus<T extends BaseType> implements ControlBusInterface<T> {

	protected ControlServer server;
	protected Synth synth;
	protected ControlDesc controlDescription;
	
	ParameterControlBus(ControlServer server, ControlDesc desc, Synth synth) {
		this.server = server;
		this.synth = synth;
		this.controlDescription = desc;
	}
	
	@Override
	public void setValue(T baseType) {
		server.appendMessage(synth.setMsg(this.controlDescription.getName(), baseType.getFloatValue()));
		
	}

	@Override
	public void setSynthAndControlDesc(Synth synth, ControlDesc desc) {
		this.synth = synth;
		this.controlDescription = desc;
	}

	@Override
	public void setDefaultValue() {
		server.appendMessage(synth.setMsg(this.controlDescription.getName(), this.controlDescription.getDefaultValue()));
	}

	@Override
	public ControlDesc getControlDesc() {
		return this.controlDescription;
	}
}
