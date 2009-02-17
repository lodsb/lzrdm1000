package sequence.view.types;

import com.trolltech.qt.core.QPointF;
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
	
	
	private int numberDisplacement = 6;
	private int msNumberAlignment = 10;
	private int msTimestampWidth = (fontWidthOf4Digits+numberDisplacement)+(msNumberAlignment - (fontWidthOf4Digits+numberDisplacement)%msNumberAlignment);
	
	public TimeLineHorizontalRuler(QWidget parent,
			TypeHandlerInterface<Double> typehandler) {
		super(parent, typehandler);
	}
	
	public TimelineTypeHandler getTypeHandler() {
		return (TimelineTypeHandler) super.getTypeHandler();
	}
	
	@Override
	protected  void paintEvent(QPaintEvent event) {
		TimelineTypeHandler typeHandler = getTypeHandler();
		double start = getStart();
		double end = getEnd();
		
		QPainter painter = new QPainter(this);
		painter.setFont(font);
		
		int offset = (int)(typeHandler.center()-start)%msTimestampWidth;
		
		for(int i = 1; i < width()-(msTimestampWidth+offset); i++) {
			if(i%(msTimestampWidth) == 0) {
				int x = i+offset;
				
				String pos = typeHandler.getMSValueStringFromScenePos(new QPointF(start+x, 10));
				//int posTxtWidth = fontMetrics.width(pos);
				
				//if(posTxtWidth < lineXStart5Decimals) {
				//	painter.drawLine(lineXStart5Decimals,y,width(),y);
					painter.drawText(x+numberDisplacement, 10, pos);
					painter.drawLine(x, 7, x, height() );
					painter.drawLine(x,7,x+numberDisplacement/2,7);
				//} else {
				//	painter.drawLine(posTxtWidth,y,width(),y);
				//	painter.drawText(0, y+4, pos);
				//}
				
			}
		}
	}

}