package sequence.view;

public interface GenericSequenceViewInterface<P,T> extends Zoomable {
	public void addHorizontalRuler(HorizontalRuler<P> ruler);
	public void addVerticalRuler(VerticalRuler<P> ruler);
	
	
}
