package gui.multitouch;

import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSizeF;

public interface GeometryInterface {
	public abstract void setGeometry(QRectF size);
	public abstract QSizeF getPreferedSize();
	public abstract QSizeF getMaximumSize();
}
