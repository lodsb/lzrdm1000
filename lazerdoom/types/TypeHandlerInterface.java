package types;

import com.trolltech.qt.core.QPointF;

public interface TypeHandlerInterface<T> {
	String getName();
	
	T min();
	T max();
	
	T stepSize();
	
	T inc(T v);
	T dec(T v);
	
	T createNewFromScenePos(QPointF point);
	QPointF scenePosFromType(T v);
}
