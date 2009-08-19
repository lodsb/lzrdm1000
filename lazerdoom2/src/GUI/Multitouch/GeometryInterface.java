package GUI.Multitouch;

import com.trolltech.qt.core.QSizeF;

public interface GeometryInterface {
	public abstract void setSize(QSizeF size);
	public abstract QSizeF getPreferedSize();
}
