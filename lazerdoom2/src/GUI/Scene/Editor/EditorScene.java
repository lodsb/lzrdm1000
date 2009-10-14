package GUI.Scene.Editor;

import lazerdoom.LzrDmObjectInterface;
import sparshui.common.Event;
import sparshui.common.messages.events.EventType;
import Control.Types.BaseType;
import GUI.Item.Editor.TouchableEditorItem;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRect;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.gui.QPainter;

public class EditorScene extends QGraphicsScene implements LzrDmObjectInterface {
	public void drawVerticalGrid(QPainter painter, QRectF rect, double verticalScale) {
		
	}
	
	public void drawHorizontalGrid(QPainter painter, QRectF rect, double horizontalScale) {
		
	}
	
	public void drawVerticalGridCaption(QPainter painter, QRectF rect, QPointF point, double verticalScale) {
		
	}
	
	public String getVerticalGridCaption(double verticalScale) {
		return "";
	}
	
	public String getHorizontalGridCaption(double horizontalScale) {
		return "";
	}
	
	public void drawHorizontalGridCaption(QPainter painter, QRectF rect, QPointF point, double horizontalScale) {
		
	}
	
	public int verticalSnapToGridResolution(double verticalScale) {
		return -1;
	}
	
	public int horizontalSnapToGridResolution(double verticalScale) {
		return -1;
	}
}
