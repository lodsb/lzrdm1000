package Control;

import de.sciss.jcollider.Synth;
import de.sciss.net.OSCMessage;
import Control.Types.BaseType;

public class OperatorParameterControlBus<T extends BaseType> extends ParameterControlBus<T> {

	private ControlOperatorInterface<T> operator;
	
	OperatorParameterControlBus(ControlServer server, Synth synth, String parameter, ControlOperatorInterface<T> operator) {
		super(server,synth, parameter);	
		this.operator = operator;
	}
	
	@Override
	public void setValue(T value) {
		if(operator.consume(value)) {
			server.appendMessage(synth.setMsg(parameter, operator.getResult().getFloatValue()));
		}
		
	}
}
