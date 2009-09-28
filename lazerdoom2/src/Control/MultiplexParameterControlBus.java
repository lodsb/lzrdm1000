package Control;

import Sequencer.BaseSequence;
import Sequencer.SequenceInterface;
import de.sciss.jcollider.ControlDesc;
import de.sciss.jcollider.Synth;
import de.sciss.net.OSCMessage;
import Control.Types.BaseType;

public class MultiplexParameterControlBus<T extends BaseType> extends ParameterControlBus<T> implements ControlBusInterface<T> {

	protected ControlServer server;
	protected Synth[] synths;
	protected ControlDesc controlDescription;
	
	public MultiplexParameterControlBus(ControlServer server, ControlDesc desc, Synth[] synths) {
		super(server, desc, null);
		this.server = server;
		this.synths = synths;
		this.controlDescription = desc;
	}
	
	@Override
	public void setValue(BaseSequence si, long tick, T baseType) {
		for(Synth currentSynth: synths) {
			server.appendMessage(si, tick, currentSynth.setMsg(this.controlDescription.getName(), baseType.getFloatValue()));
		}
	}

	@Override
	public void setSynthAndControlDesc(Synth synth, ControlDesc desc) {
		System.out.println("public void setSynthAndControlDesc(Synth synth, ControlDesc desc) not implemented!");
		this.synth = synth;
		this.controlDescription = desc;
	}

	@Override
	public void setDefaultValue(BaseSequence si, long tick) {
		for(Synth currentSynth: synths) {
			server.appendMessage(si, tick, currentSynth.setMsg(this.controlDescription.getName(), this.controlDescription.getDefaultValue()));
		}
	}

	@Override
	public ControlDesc getControlDesc() {
		return this.controlDescription;
	}
}
