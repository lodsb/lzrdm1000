package sequence.view;

import types.TypeHandlerInterface;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.Qt.SizeHint;
import com.trolltech.qt.gui.*;;

public class VerticalRuler<P> extends QWidget implements VerticalRulerInterface<P> {
	
	private double start = -1.0;
	private double end = 1.0;

	protected double getStart() {
		return start;
	}
	
	protected double getEnd() {
		return end;
	}
	
	protected TypeHandlerInterface<P> getTypeHandler() {
		return typeHandler;
	}
	
	public QSize sizeHint() {
		return new QSize(50,2000);
	} 
	
	private TypeHandlerInterface<P> typeHandler;
	
	public VerticalRuler(QWidget parent, TypeHandlerInterface<P> typehandler) {
		super(parent);
		typeHandler = typehandler;
		
		this.start = start;
		this.end = end;
	}
	
	@Override
	public void decZoomH() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decZoomV() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void incZoomH() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void incZoomV() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetZoomH() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetZoomV() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void highlightPosition(P pos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void highlightRange(P posStart, P posEnd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scrollBy(int pixels) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scrollTo(P pos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRange(P start, P end) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTypeHandler(TypeHandlerInterface<P> t) {
		typeHandler = t;
		
	}

	@Override
	public void updateVisibleRange(double start, double end) {
		if(start != this.start && end != this.end) {
			this.start = start;
			this.end = end;
			this.update();
		}
	}

	@Override
	public Signal1<P> getPositionChangedSignal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPosition(P t) {
		// TODO Auto-generated method stub
		
	}

}