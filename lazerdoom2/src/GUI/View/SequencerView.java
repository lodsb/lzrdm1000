package GUI.View;

import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QRadialGradient;

public class SequencerView extends QGraphicsView {
	private QColor bgColor1 = new QColor(38,50,62);
	private QColor bgColor2 = new QColor(bgColor1.lighter(155));
	
    protected void drawBackground(QPainter painter, QRectF rect) {
        // Fill
        QRadialGradient gradient = new QRadialGradient(0,0,rect.width());
        gradient.setColorAt(0, bgColor2);
        gradient.setColorAt(1, bgColor1);
        painter.fillRect(rect, new QBrush(gradient));
    }
}
