package sequence.scene;

import com.trolltech.qt.gui.*;
import com.trolltech.qt.QSignalEmitter.Signal2;

public interface SequenceSceneItemInterface<T> {
	public Signal2<SequenceSceneItemInterface, T> getModifiedSignal();
	public Signal2<SequenceSceneItemInterface, T> getModifyingSignal();
	public QGraphicsItem getRepresentation();
	public void update(T entry);
}
