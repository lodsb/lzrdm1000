package Control;

import Sequencer.BaseSequence;
import Sequencer.SequenceInterface;
import de.sciss.jcollider.ControlDesc;
import de.sciss.jcollider.Synth;
import de.sciss.net.OSCMessage;
import Control.Types.BaseType;

public class ParameterControlBus<T extends BaseType> implements ControlBusInterface<T> {

	protected ControlServer server;
	protected Synth synth;
	protected ControlDesc controlDescription;
	
	public ParameterControlBus(ControlServer server, ControlDesc desc, Synth synth) {
		this.server = server;
		this.synth = synth;
		this.controlDescription = desc;
	}
	
	@Override
	public void setValue(BaseSequence si, long tick, T baseType) {
		server.appendMessage(si, tick, synth.setMsg(this.controlDescription.getName(), baseType.getFloatValue()));		
	}

	@Override
	public void setSynthAndControlDesc(Synth synth, ControlDesc desc) {
		this.synth = synth;
		this.controlDescription = desc;
	}

	@Override
	public void setDefaultValue(BaseSequence si, long tick) {
		server.appendMessage(si, tick, synth.setMsg(this.controlDescription.getName(), this.controlDescription.getDefaultValue()));
	}

	@Override
	public ControlDesc getControlDesc() {
		return this.controlDescription;
	}
}
