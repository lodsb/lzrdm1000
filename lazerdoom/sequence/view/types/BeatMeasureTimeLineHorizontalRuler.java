package sequence.view.types;

import types.TimelineTypeHandler;
import types.TypeHandlerInterface;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QPaintEvent;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QWidget;

public class BeatMeasureTimeLineHorizontalRuler extends TimeLineHorizontalRuler {

	private final int divisions = 10;
	
	public BeatMeasureTimeLineHorizontalRuler(QWidget parent,
			TypeHandlerInterface<Double> typehandler) {
		super(parent, typehandler);
		
	}

	@Override
	protected  void paintEvent(QPaintEvent event) {
		
		TimelineTypeHandler typeHandler = getTypeHandler();
		
		QPainter painter = new QPainter(this);
				
		int divisionWidth = (int)typeHandler.getMeterSubdivisionPixelWidthFromZoomAndViewPixelWidth(1.0, width(), 4, 4, 120);
		int offset = (int)(typeHandler.center()-getStart())%divisionWidth;
		int x;
		
		int divCount = ((int)(typeHandler.center()+getStart())/divisionWidth)%2;
		int divCount2 = ((int)(typeHandler.center()+getStart())/divisionWidth/2)%2;
		int divCount4 = ((int)(typeHandler.center()+getStart())/divisionWidth/4)%2;
			
		painter.setPen(QColor.white);
		
		for(int i = 0; i < width()+divisionWidth; i++) {
			if(i%(divisionWidth) == 0) {
				
				if(divCount % 2 == 0) {
					painter.setBrush(QColor.blue);
				} else {
					painter.setBrush(QColor.darkBlue);					
				}
				
				divCount++;
				
				x = i+offset;
				painter.drawRect(x, 0, divisionWidth, height()/2);
			}
			
			if(i%(divisionWidth/2) == 0) {
				if(divCount2 % 2 == 0) {
					painter.setBrush(QColor.blue);
				} else {
					painter.setBrush(QColor.darkBlue);					
				}
				
				divCount2++;
				
				x = i+offset;
				painter.drawRect(x, height()/2, divisionWidth/2, height()/2+height()/4);
			}
			
			if(i%(divisionWidth/4) == 0) {
				if(divCount4 % 2 == 0) {
					painter.setBrush(QColor.blue);
				} else {
					painter.setBrush(QColor.darkBlue);					
				}
				
				divCount4++;
				
				x = i+offset;
				painter.drawRect(x, height()/2+height()/4, divisionWidth/4, height());
			}
		}
		painter.setPen(QColor.black);
		painter.drawText(width()/2,height()/2,typeHandler.getMeterSubdivisionStringFromZoomAndViewPixelWidth(1.0, width(), 4, 4, 120));

	}
}
