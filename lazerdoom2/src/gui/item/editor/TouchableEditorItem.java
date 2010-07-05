package gui.item.editor;

import gui.editor.Editor;
import gui.multitouch.TouchableGraphicsItem;

import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSizeF;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;


public class TouchableEditorItem extends TouchableGraphicsItem {

	private Editor editor;
	
	public TouchableEditorItem(Editor editor) {
		this.editor = editor;
	}
	
	public Editor getEditor() {
		return this.editor;
	}
	
	@Override
	public QRectF boundingRect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void paint(QPainter painter, QStyleOptionGraphicsItem option,
			QWidget widget) {
		// TODO Auto-generated method stub

	}

	@Override
	public QSizeF getMaximumSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QSizeF getPreferedSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGeometry(QRectF size) {
		// TODO Auto-generated method stub

	}

}
