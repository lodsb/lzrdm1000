package sequence.view.types;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QFontMetrics;
import com.trolltech.qt.gui.QPaintEvent;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QWidget;

import sequence.view.HorizontalRuler;
import types.TimelineTypeHandler;
import types.TypeHandlerInterface;

public class TimeLineHorizontalRuler extends HorizontalRuler<Double> {

	private QFont font =  new QFont("Times", 10);
	private QFontMetrics fontMetrics = new QFontMetrics(font);
	private int fontHeight = fontMetrics.height();
	
	private int fontWidthOf4Digits = fontMetrics.width("000.0");
	private int fontWidthOfMinSecond = fontMetrics.width("000:00");
	
	
	private int numberDisplacement = 6;
	private int msNumberAlignment = 10;
	private int SMNumberAlignment = 10;
	private int msTimestampWidth = (fontWidthOf4Digits+numberDisplacement)+(msNumberAlignment - (fontWidthOf4Digits+numberDisplacement)%msNumberAlignment);
	private int secMinTimestampWidth = (fontWidthOfMinSecond+numberDisplacement)+(SMNumberAlignment - (fontWidthOfMinSecond+numberDisplacement)%SMNumberAlignment);
	
	public TimeLineHorizontalRuler(QWidget parent,
			TypeHandlerInterface<Double> typehandler) {
		super(parent, typehandler);
	}
	
	public TimelineTypeHandler getTypeHandler() {
		return (TimelineTypeHandler) super.getTypeHandler();
	}
	
	public QSize sizeHint() {
		return new QSize(200,40);
	} 
	
	protected void paintEvent(QPaintEvent event) {
		QPainter painter = new QPainter(this);
		paintRuler(painter, true);
	}
	
	protected void paintRuler(QPainter painter, boolean drawNumbers) {
		TimelineTypeHandler typeHandler = getTypeHandler();
		double start = getStart();
		
		
		if(drawNumbers) {
			painter.setFont(font);


			int offset = (int)(typeHandler.center()-start)%secMinTimestampWidth;
			int x;

			painter.setPen(QColor.darkGray);

			for(int i = 0; i < width()+secMinTimestampWidth; i++) {
				if(i%(secMinTimestampWidth) == 0) {
					x = i+offset;

					String pos = typeHandler.getMinuteSecondValueStringFromScenePos(new QPointF(start+x, 10));
					painter.drawText(x+numberDisplacement, 10, pos);
					painter.drawLine(x, 7, x, height() );
					painter.drawLine(x,7,x+numberDisplacement/2,7);
				}
			}

			offset = (int)(typeHandler.center()-start)%msTimestampWidth;

			painter.setPen(QColor.black);

			for(int i = 0; i < width()+msTimestampWidth; i++) {
				if(i%(msTimestampWidth) == 0) {
					x = i+offset;

					String pos = typeHandler.getMSValueStringFromScenePos(new QPointF(start+x, 10));
					painter.drawText(x+numberDisplacement, 25, pos);
					painter.drawLine(x, 20, x, height() );
					painter.drawLine(x,20,x+numberDisplacement/2,20);
				}
			}
		} else {
				int offset = (int)(typeHandler.center()-start)%msTimestampWidth;
				int x;

				for(int i = 0; i < width()+msTimestampWidth; i++) {
					if(i%(msTimestampWidth) == 0) {
						x = i+offset;
						painter.drawLine(x, 0, x, height() );
					}
				}
		}
		
	}

}