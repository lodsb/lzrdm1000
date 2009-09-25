package Control;

import Control.Types.BaseType;
import Sequencer.BaseSequence;
import de.sciss.jcollider.ControlDesc;
import de.sciss.jcollider.Synth;

public class TwoParameterControlBus<T extends BaseType> extends ParameterControlBus<T> {

	protected ControlDesc desc2;
	
	public TwoParameterControlBus(ControlServer server, ControlDesc desc, ControlDesc desc2, Synth synth) {
		super(server, desc, synth);
		this.desc2 = desc2;
	}
	
	@Override
	public void setValue(BaseSequence si, long tick, T baseType) {
		super.setValue(si, tick, baseType);
		server.appendMessage(si, tick, synth.setMsg(this.desc2.getName(), baseType.getFloatValue2()));		
	}
	
	@Override
	public void setDefaultValue(BaseSequence si, long tick) {
		server.appendMessage(si, tick, synth.setMsg(this.controlDescription.getName(), this.controlDescription.getDefaultValue()));
		server.appendMessage(si, tick, synth.setMsg(this.desc2.getName(), this.desc2.getDefaultValue()));
	}
}
