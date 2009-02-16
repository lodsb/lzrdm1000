package sequence.view;

import com.trolltech.qt.core.QPointF;

public interface RulerInterface<P> extends Zoomable, CursorInterface<P>, TypehandlerDependent<P> {
	public void updateRange(P start, P end);
	public void updateVisibleRange(double start, double end);
	
	// pos must be centered to view
	public void scrollTo(P pos);
	
	public void scrollBy(int pixels);
	
	public void highlightPosition(P pos);
	public void highlightRange(P posStart, P posEnd);
}
