package types;

import com.trolltech.qt.core.QPointF;

public class DoubleTypeHandler implements TypeHandlerInterface<Double> {

	@Override
	public Double createNewFromScenePos(QPointF point) {
		return point.y();
	}

	@Override
	public Double dec(Double v) {
		return --v;
	}

	@Override
	public String getTypeName() {
		return "Double";
	}

	@Override
	public Double inc(Double v) {
		return ++v;
	}

	@Override
	public Double max() {
		return Double.MAX_VALUE;
	}

	@Override
	public Double min() {
		return Double.MIN_VALUE;
	}

	@Override
	public QPointF scenePosFromType(Double v) {
		return new QPointF(0,v);
	}

	@Override
	public Double stepSize() {
		return 1.0;
	}

}
