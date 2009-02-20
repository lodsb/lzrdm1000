package types;

import lazerdoom.UtilMath;
import lazerdoom.lazerdoom;

import com.trolltech.qt.core.QPointF;

public class TimelineTypeHandler implements ZoomContextSensitiveTypeHandler<Double> {
	
	public static double beatMeasureToMs(int beat, int measure, double bpm) {
		return (240000*((double)beat/(double)measure)/bpm);
	}
	
	public static double msToDivision(double ms, double bpm) {
		double oneBarLength = beatMeasureToMs(1, 1, bpm);
		double division = oneBarLength/ms;
		
		if(division > 1) {
			return 1/(double)UtilMath.nearestPowerOfTwo((new Double(division).intValue()));
		} else {
			return UtilMath.nearestPowerOfTwo((new Double(1/division).intValue()));
		}
	}
	
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
		return new Integer(((this.createNewFromScenePos(point)).intValue()/1000)%60).toString();
	}

	public String getMinuteValueStringFromScenePos(QPointF point) {
		return new Integer((((this.createNewFromScenePos(point).intValue())/1000)/60)%60).toString();
	}
	
	public String getMinuteSecondValueStringFromScenePos(QPointF point) {
		return new Integer((((this.createNewFromScenePos(point).intValue())/1000)/60)%60).toString() +":"+new Integer(((this.createNewFromScenePos(point).intValue())/1000)%60).toString();
	}
	
	public String getMeterSubdivisionStringFromZoomAndViewPixelWidth(double zoom, int width, int beat, int measure, double bpm) {
		// Zoomlevel 1 means one view-point equals one ms
		double ms = width*zoom;
		double division = msToDivision(ms, bpm);
		if(division > 1) {
			return new Double(division).intValue()+"/1";
		} else {
			return "1/"+new Double(1/division).intValue();
		}
	}
	
	public double getMeterSubdivisionPixelWidthFromZoomAndViewPixelWidth(double zoom, int width, int beat, int measure, double bpm) {
		double ms = width*zoom;
		double division = msToDivision(ms, bpm);
		
		if(division > 1) {
			ms = beatMeasureToMs(new Double(division).intValue(), 1, bpm);
		} else {
			ms = beatMeasureToMs(1,new Double(1/division).intValue(), bpm);
		}
		
		return ms/zoom;
		
	}
	
	@Override
	public Double center() {
		// TODO Auto-generated method stub
		return 0.0;
	}

}
