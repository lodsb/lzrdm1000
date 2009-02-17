package types;

import com.trolltech.qt.core.QPointF;

public class TimelineTypeHandler implements ZoomContextSensitiveTypeHandler<Double> {

	@Override
	public Double createNewFromScenePos(QPointF point) {
		return point.x();
	}

	@Override
	public Double dec(Double v) {
		return --v;
	}

	@Override
	public String getTypeName() {
		return "Timeline";
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
		return new QPointF(v,0);
	}

	@Override
	public Double stepSize() {
		return 1.0;
	}

	@Override
	public Double dec(Double v, double zoom) {
		return v-zoom;
	}

	@Override
	public Double inc(Double v, double zoom) {
		return v+zoom;
	}

	@Override
	public String getValueStringFromScenePos(QPointF point) {
		// TODO Auto-generated method stub
		return this.createNewFromScenePos(point).toString();
	}
	
	public String getMSValueStringFromScenePos(QPointF point) {
		return new Double(((this.createNewFromScenePos(point))%1000)).toString();
	}
	
	public String getSecondValueStringFromScenePos(QPointF point) {
		return new Double(((this.createNewFromScenePos(point))/1000)%60).toString();
	}

	public String getMinuteValueStringFromScenePos(QPointF point) {
		return new Double((((this.createNewFromScenePos(point))/1000)/60)%60).toString();
	}

	
	@Override
	public Double center() {
		// TODO Auto-generated method stub
		return 0.0;
	}

}
