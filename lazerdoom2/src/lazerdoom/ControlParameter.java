package lazerdoom;

import com.trolltech.qt.QSignalEmitter.Signal1;

public class ControlParameter {
	public String label;
	public Signal1<Double> parameterChangeSignal;
}
