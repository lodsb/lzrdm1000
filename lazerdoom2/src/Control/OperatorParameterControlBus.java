package Control;

import de.sciss.net.OSCMessage;
import Control.Types.BaseType;

public class OperatorParameterControlBus<T extends BaseType> extends ParameterControlBus<T> {

	private ControlOperatorInterface<T> operator;
	
	OperatorParameterControlBus(ControlServer server, String url, String parameter, int nodeID, ControlOperatorInterface<T> operator) {
		super(server, url, parameter, nodeID);	
		this.operator = operator;
	}
	
	@Override
	public void setValue(T value) {
		if(operator.consume(value)) {
			server.appendMessage(new OSCMessage(url, 
				new Object[] {new Integer(nodeID), new String(parameter), operator.getResult().getValue()}                             
			));
		}
		
	}
}
