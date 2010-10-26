package sequence.view.types;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QFontMetrics;
import com.trolltech.qt.gui.QPaintEvent;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QWidget;

import sequence.view.*;
import types.TypeHandlerInterface;

public class DoublePointVerticalRuler extends VerticalRuler<Double> {

	private QFont font =  new QFont("Times", 10);
	private QFontMetrics fontMetrics = new QFontMetrics(font);
	private int lineXStart5Decimals = fontMetrics.width("0000.0");
	
	public DoublePointVerticalRuler(QWidget parent,
			TypeHandlerInterface<Double> typehandler) {
		super(parent, typehandler);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected  void paintEvent(QPaintEvent event) {
		TypeHandlerInterface<Double> typeHandler = getTypeHandler();
		double start = getStart();
		double end = getEnd();
		
		QPainter painter = new QPainter(this);
		painter.setFont(font);
		
		int offset = (int)(typeHandler.center()-start)%10;
		
		for(int i = 1; i < height()-(10+offset); i++) {
			if(i%(10) == 0) {
				int y = i+offset;
				
				String pos = typeHandler.getValueStringFromScenePos(new QPointF(0,start+y));
				int posTxtWidth = fontMetrics.width(pos);
				
				if(posTxtWidth < lineXStart5Decimals) {
					painter.drawLine(lineXStart5Decimals,y,width(),y);
					painter.drawText(lineXStart5Decimals-posTxtWidth, y+4, pos);
				} else {
					painter.drawLine(posTxtWidth,y,width(),y);
					painter.drawText(0, y+4, pos);
				}
				
			}
		}
	}

}
