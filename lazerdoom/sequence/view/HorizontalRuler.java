package sequence.view;

import types.TypeHandlerInterface;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.gui.*;

public class HorizontalRuler<P> extends QWidget implements HorizontalRulerInterface<P> {

	private TypeHandlerInterface<P> typeHandler;
	private double start = -1.0;
	private double end = 1.0;
	
	public HorizontalRuler(QWidget parent, TypeHandlerInterface<P> typehandler) {
		super(parent);
		typeHandler = typehandler;
		
	}
	
	@Override
	protected  void paintEvent(QPaintEvent event) {
		QPainter painter = new QPainter(this);
		
		for(int i = 0; i < width(); i++) {
			if(i%5 == 0) {
				painter.drawLine(i,0,i,height());
			}
		}
	}

	public QSize sizeHint() {
		return new QSize(200,20);
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
		this.start = start;
		this.end = end;
		
	}

}
