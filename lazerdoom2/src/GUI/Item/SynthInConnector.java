package GUI.Item;

public class SynthInConnector extends SynthConnector {
	private String parameterName; 
	
	public SynthInConnector(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getParameterName() {
		return this.parameterName;
	}
}
