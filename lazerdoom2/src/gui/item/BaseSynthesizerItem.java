package gui.item;

import sequencer.BaseSequence;
import synth.SynthInstance;

import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSizeF;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;

public abstract class BaseSynthesizerItem extends BaseSequencerItem {
 
	
	@Override
	public QRectF boundingRect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void paint(QPainter painter, QStyleOptionGraphicsItem option,
			QWidget widget) {
		// TODO Auto-generated method stub

	}
	
	abstract public SynthInstance getSynthesizer();
	abstract public void setSynthesizer(SynthInstance synth);

	@Override
	public QSizeF getMaximumSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QSizeF getPreferedSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGeometry(QRectF size) {
		// TODO Auto-generated method stub

	}

}
