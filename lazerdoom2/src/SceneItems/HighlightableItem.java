package SceneItems;

import com.trolltech.qt.gui.QGraphicsItemInterface;

public interface HighlightableItem extends QGraphicsItemInterface {
	public boolean isHighlighted();
	public void setHighlighted(boolean b);
}
