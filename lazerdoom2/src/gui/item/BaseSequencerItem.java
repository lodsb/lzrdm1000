package gui.item;

import gui.multitouch.TouchableGraphicsItem;

import java.util.LinkedList;

import sequencer.BaseSequence;

import lazerdoom.LzrDmObjectInterface;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.gui.QGraphicsSceneMouseEvent;
import com.trolltech.qt.gui.QPainterPath;


abstract public class BaseSequencerItem extends TouchableGraphicsItem implements LzrDmObjectInterface {
	private static QRectF rect = new QRectF(0,0,200,200);
	private static QPainterPath p = null;
	
	private LinkedList<EditorCursor> cursors = new LinkedList<EditorCursor>();
	
	public QPainterPath shape() {
		
		if(p == null) {
			p = new QPainterPath();
			p.addEllipse(rect);
		}
		return p;
	}
	
	public abstract boolean isInitialized();
	public abstract void setContentObject(LzrDmObjectInterface object);
	
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
	
	public boolean setPosition(QPointF pos) {
		if(cursors.size() != 0) {
			//QPointF currentPos = this.pos();
			QPointF offset = new QPointF(this.pos().x()-pos.x(), this.pos().y()-pos.y());
			
			for(EditorCursor cursor: cursors) {
				QPointF cursorPos = cursor.pos();
				cursor.setPos(cursorPos.x()-offset.x(), cursorPos.y()-offset.y());
			}
		}
		
		this.setPos(pos);
		
		return true;
	}
	
	public void dockCursor(EditorCursor cursor) {
		System.out.println("DOCKKKKKKEKEKEKEKEK");
		cursors.add(cursor);
	}
	
	public void undockAllCursors() {
		for(EditorCursor cursor: this.cursors) {
			cursor.setUndocked();
		}
		cursors.clear();
	}
		
	public void undockCursor(EditorCursor cursor) {
		cursors.remove(cursor);
	}
}
