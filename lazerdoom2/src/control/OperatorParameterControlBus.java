package control;

import sequencer.BaseSequence;
import control.types.BaseType;
import de.sciss.jcollider.ControlDesc;
import de.sciss.jcollider.Synth;
import de.sciss.net.OSCMessage;

public class OperatorParameterControlBus<T extends BaseType> extends ParameterControlBus<T> {

	private ControlOperatorInterface<T> operator;
	
	OperatorParameterControlBus(ControlServer server, ControlDesc desc, Synth synth, ControlOperatorInterface<T> operator) {
		super(server,desc, synth);	
		this.operator = operator;
	}
	
	@Override
	public void setValue(BaseSequence si, long tick, T value) {
		if(operator.consume(value)) {
			server.appendMessage(si, tick,synth.setMsg(this.controlDescription.getName(), operator.getResult().getFloatValue()));
		}
		
	}
}
