package sequence.view.types;

import lazerdoom.UtilMath;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.Qt;

import sequence.GenericSequenceController;
import sequence.scene.AbstractGenericSequenceScene;
import sequence.scene.types.DoublePointSequence;
import sequence.scene.types.DoublePointSequenceScene;
import sequence.view.Cursor;
import sequence.view.GenericSequenceEditor;
import sequence.view.GenericSequenceViewWidget;
import sequence.view.HorizontalRuler;
import sequence.view.VerticalRuler;
import sequence.view.item.types.DoublePoint;
import types.TimelineTypeHandler;

public class DoublePointSequenceEditor extends GenericSequenceEditor<Double, Double> {
	public DoublePointSequenceEditor() {
		super();
		
		GenericSequenceViewWidget<Double, Double> viewWidget = this.getSequenceViewWidget();
		
		DoublePointSequenceScene scene = new DoublePointSequenceScene();
		
		scene.setTypeHandlers(types.TypeSystem.doubleTypeHandler,types.TypeSystem.timelineTypeHandler);
		
		DoublePointSequence sequence = new DoublePointSequence();
		GenericSequenceController<Double,Double> controller = new GenericSequenceController<Double, Double>(sequence);
		
		scene.addSequenceController(controller);
		
		viewWidget.setSequenceScene((AbstractGenericSequenceScene<Double, Double>) scene);
		
		viewWidget.addVerticalRuler(new DoublePointVerticalRuler(this, types.TypeSystem.doubleTypeHandler));
		viewWidget.addHorizontalRuler(new TimeLineHorizontalRuler(this, types.TypeSystem.timelineTypeHandler));
		viewWidget.addHorizontalRuler(new BeatMeasureTimeLineHorizontalRuler(this, types.TypeSystem.timelineTypeHandler));
		viewWidget.getGraphicsView().setAlignment(Qt.AlignmentFlag.AlignLeft);
		
		scene.addItem(new Cursor(viewWidget.getGraphicsView()));
	}
	
	protected void mouseAtScenePos(QPointF pos) {
		super.mouseAtScenePos(pos);
		
		GenericSequenceViewWidget<Double, Double> viewWidget = this.getSequenceViewWidget();
		viewWidget.getViewInfoWidget().setPositionInfo(types.TypeSystem.doubleTypeHandler.getValueStringFromScenePos(pos), types.TypeSystem.timelineTypeHandler.getValueStringFromScenePos(pos)+" ms");
	}
}
