package sequence.view;

public interface RulerInterface<P> extends Zoomable {
	public void updateRange(P start, P end);
	
	// pos must be centered to view
	public void scrollTo(P pos);
	
	public void scrollBy(int pixels);
	
	public void highlightPosition(P pos);
	public void highlightRange(P posStart, P posEnd);
}
