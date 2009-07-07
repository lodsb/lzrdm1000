package Control.Types;

public class DoubleType implements BaseType<Double> {
	
	private double value = 0.0;
	private double defaultValue = 0.0;
	
	@Override
	public Double defaultValue() {
		// TODO Auto-generated method stub
		return defaultValue;
	}

	@Override
	public float getFloatValue() {
		// TODO Auto-generated method stub
		return (float) value;
	}

	@Override
	public Double getValue() {
		// TODO Auto-generated method stub
		return value;
	}

	@Override
	public void setValue(Double t) {
		this.value = t;
	}

}
