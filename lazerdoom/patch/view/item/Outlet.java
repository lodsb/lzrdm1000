package patch.view.item;

import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsSceneDragDropEvent;

public class Outlet extends Inlet {

	public Outlet(PatchNodeItem patchNodeItem, QColor color) {
		super(patchNodeItem, color);
		// TODO Auto-generated constructor stub
	}
	
	public void dragEnterEvent(QGraphicsSceneDragDropEvent event) {
		event.ignore();
	} 

}
