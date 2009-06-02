package Control;

import de.sciss.net.OSCMessage;
import Control.Types.BaseType;

public class ParameterControlBus<T extends BaseType> implements ControlBusInterface<T> {

	protected ControlServer server;
	protected String url;
	protected String parameter;
	protected int nodeID;
	
	ParameterControlBus(ControlServer server, String url, String parameter, int nodeID) {
		this.server = server;
		this.url = url;
		this.parameter = parameter;
		this.nodeID = nodeID;
	}
	
	@Override
	public void setValue(T baseType) {
		server.appendMessage(new OSCMessage(url, 
				new Object[] {new Integer(nodeID), new String(parameter), baseType.getValue()}                             
		));
		
	}

}
