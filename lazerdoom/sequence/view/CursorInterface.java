package sequence.view;

import com.trolltech.qt.QSignalEmitter.Signal1;

public interface CursorInterface<T> {
	Signal1<T> getPositionChangedSignal();
	void setPosition(T t);
}
