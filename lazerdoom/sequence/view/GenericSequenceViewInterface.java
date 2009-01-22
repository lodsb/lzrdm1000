package sequence.view;

public interface GenericSequenceViewInterface<P,T> extends Zoomable {
	public void addHorizontalRuler(HorizontalRulerInterface<P> ruler);
	public void addVerticalRuler(VerticalRulerInterface<P> ruler);
	
	
}
