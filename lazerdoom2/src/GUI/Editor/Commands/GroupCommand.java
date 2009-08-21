package GUI.Editor.Commands;

import java.util.List;

import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QGraphicsScene;

import GUI.Editor.BaseEditorCommand;

public class GroupCommand extends BaseEditorCommand {
	
	List<QGraphicsItemInterface> items;
	QGraphicsScene scene;

	GroupCommand(List<QGraphicsItemInterface> items, QGraphicsScene scene) {
		this.items = items;
		this.scene = scene;
	}
	
	@Override
	public boolean execute() {
		// TODO Auto-generated method stub
		return false;
	}

}
