package GUI.Item;

public class SynthOutConnector extends SynthConnector {

	private String parameterName; 

	public SynthOutConnector(String parameterName) {
		this.parameterName = parameterName;
		super.setIsInPort(false);
	}

	public String getParameterName() {
		return this.parameterName;
	}
}
