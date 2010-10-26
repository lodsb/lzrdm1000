package sequence.scene.types;

import com.trolltech.qt.gui.QGraphicsItem;

import sequence.scene.AbstractGenericSequenceScene;
import sequence.scene.SequenceSceneItemInterface;
import sequence.view.item.types.DoublePoint;

public class DoublePointSequenceScene extends AbstractGenericSequenceScene<Double, Double> {

	@Override
	protected void addGraphicsItemToScene(Double point, QGraphicsItem item) {
		this.addItem(item);
		item.setPos(point, 0);
	}

	@Override
	protected SequenceSceneItemInterface<Double> createSequenceItem(Double entry) {
		return new DoublePoint(entry);
	}


}
