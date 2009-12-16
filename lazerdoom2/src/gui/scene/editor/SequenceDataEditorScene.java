package gui.scene.editor;

import gui.item.Editor.TouchableEditorItem;
import gui.item.Editor.SequenceDataEditor.*;

import com.trolltech.qt.QVariant;
import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsLineItem;
import com.trolltech.qt.gui.QLineF;
import com.trolltech.qt.gui.QMatrix;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QGraphicsItem.GraphicsItemChange;

import control.types.BaseType;
import control.types.DoubleType;

public class SequenceDataEditorScene<T extends BaseType> extends EditorScene {

	public static final double valueMultiplyer = 1000;
	public static final int valueRange = 2000;
	public static final int sceneValueCenter = 0;
	public static final double tickMultiplyer = 1.0;
	
	QGraphicsLineItem cursor = new QGraphicsLineItem() {
		@Override
		public Object itemChange(GraphicsItemChange change, Object value) {
			// ignore horizontal scaling
			if(change == GraphicsItemChange.ItemMatrixChange) {
				QMatrix matrix = (QMatrix) value;
				matrix.setMatrix(matrix.m11(), matrix.m12(), matrix.m21(), 1.0, matrix.dx(), matrix.dy());
				value = matrix;
			}
			
			return super.itemChange(change, value);
		}
	};
	
	QPen cursorPen = new QPen(QColor.red);
	
	public SequenceDataEditorScene() {
		this.cursor.setLine(new QLineF(0,-1000,0,1000));
		this.cursor.setPos(0, 0);
		this.cursorPen.setWidth(10);
		this.cursor.setPen(this.cursorPen);
	}
	
	/*protected TouchableSequenceDataItem<T> createNewItem(long tick, T value) {
		TouchableDoubleTypeSequenceDataItem item = new TouchableDoubleTypeSequenceDataItem();
		item.setValue(tick, (DoubleType) value);
		
		return (TouchableSequenceDataItem<T>) item;
	}*/

	/*public TouchableSequenceDataItem<T> addNewEditorItem(long tick, T value) {
		TouchableSequenceDataItem<T> item = createNewItem(tick, value);
		System.out.println("§ "+item);
		this.addItem(item);
		return item;
	}*/
	
	public void removeEditorItem(TouchableEditorItem item) {
		this.removeItem(item);
	}


	/*public void updateEditorItem(TouchableSequenceDataItem<T> item, long tick, T value) {
		item.setValue(tick, value);
	}

	public void updateEditorItem(TouchableSequenceDataItem<T> item, QPointF pos) {
		item.setValueFromPosition(pos);
	}*/
	
	
	public void setPlayCursor(long tick) {
		this.cursor.setPos(tick*tickMultiplyer, 0);
	}
}
