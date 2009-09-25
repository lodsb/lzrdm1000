package GUI.Scene.Editor;

import Control.Types.NoteType;

import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPen;

public class NoteEventScene extends BaseSequenceScene {
	public static final int hSceneSize = 2000; // -1000.0 - 1000.0
	public static final int hSceneStart = -1000;
	public static final int hGridSize = hSceneSize/NoteType.noteScaleSize;
	
	QBrush blackKeyBrush = new QBrush(QColor.lightGray); 
	
	// from b to c
	private static boolean[] gridPattern = new boolean[] {
		false,
		true,
		false,
		true, 
		false,
		true,
		false,
		false,
		true,
		false,
		true,
		false
	};
	
	
	@Override
	public void drawHorizontalGrid(QPainter painter, QRectF rect, double horizontalScale) {
		painter.setClipRect(rect);
		
		int left = (int)rect.left();
		int right = (int)rect.right();
		int width = right - left;
		
		painter.setBrush(this.blackKeyBrush);
		for(int i = 0; i < NoteType.noteScaleSize; i++) {
			if(gridPattern[(i+4)%12]) {
				painter.drawRect(left, i*hGridSize-(hSceneSize/2), width, hGridSize); 
				if(((i+4) % 12) == 0) {
					painter.setPen(QColor.black);
					painter.drawText(left,i*hGridSize-(hSceneSize/2) , NoteType.noteNameArray[127-i]);
					//painter.setPen(QPen.NoPen);
				}
			}
		}
		painter.setBrush(QBrush.NoBrush);
	}
	
	@Override
	public int horizontalSnapToGridResolution(double horizontalScale) {
		return (int)(this.hGridSize*horizontalScale);
	}
}
