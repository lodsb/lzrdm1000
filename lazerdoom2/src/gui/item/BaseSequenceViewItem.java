package gui.item;

import gui.multitouch.TouchableGraphicsItem;

import java.util.LinkedList;

import sequencer.BaseSequence;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.gui.QGraphicsSceneMouseEvent;
import com.trolltech.qt.gui.QPainterPath;


abstract public class BaseSequenceViewItem extends BaseSequencerItem {
	private static QRectF rect = new QRectF(0,0,200,200);
	private static QPainterPath p = null;
	
	public QPainterPath shape() {
		
		if(p == null) {
			p = new QPainterPath();
			p.addEllipse(rect);
		}
		return p;
	}
	
	public abstract BaseSequence getBaseSequence();
	
	/*public void mouseMoveEvent(QGraphicsSceneMouseEvent event) {
		this.setPosition(this.mapToScene(event.pos()));
	}*/
	
	/*public void setPosition(QPointF pos) {
		if(cursors.size() != 0) {
			QPointF currentPos = this.pos();
			QPointF offset = new QPointF(this.pos().x()-pos.x(), this.pos().y()-pos.y());
			
			for(EditorCursor cursor: cursors) {
				QPointF cursorPos = cursor.pos();
				cursor.setPos(cursorPos.x()-offset.x(), cursorPos.y()-offset.y());
			}
		}
		super.setPos(pos);
	}*/
}
