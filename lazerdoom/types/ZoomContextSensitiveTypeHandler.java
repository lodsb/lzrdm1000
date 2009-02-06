package types;

import sequence.view.Zoomable;

public interface ZoomContextSensitiveTypeHandler<T> extends
		TypeHandlerInterface<T>, Zoomable {
	
	T inc(T v, double zoom);
	T dec(T v, double zoom);
}
