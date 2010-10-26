package sequence.view.types;

import lazerdoom.UtilMath;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.Qt;

import sequence.GenericSequenceController;
import sequence.scene.AbstractGenericSequenceScene;
import sequence.scene.types.DoublePointSequence;
import sequence.scene.types.DoublePointSequenceScene;
import sequence.view.TimelineCursor;
import sequence.view.GenericSequenceEditor;
import sequence.view.GenericSequenceViewWidget;
import sequence.view.HorizontalRuler;
import sequence.view.VerticalRuler;
import sequence.view.form.CursorToolBox;
import sequence.view.item.types.DoublePoint;
import types.TimelineTypeHandler;

public class DoublePointSequenceEditor extends GenericSequenceEditor<Double, Double> {
	
	
/*
 *  *.*.*... for bars
 *  *:*:...for time
 *  +/-*.*. next/prev bar
 *  +/-*:*. next/prev time
 *  
 *  selection like basic math expressions
 *  
 *  python interface for all commands?!
 *  qaction->python script?!
 *  
 *  jflex->commandshortcuts->python scripts via something like p	
 */
	
	private TimelineCursor cursor;
	
	private CursorToolBox cursorToolBox = new CursorToolBox(this);
	
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
		
		this.addToolBoxWidget(cursorToolBox);
		
		cursor = new TimelineCursor(viewWidget.getGraphicsView());
		viewWidget.placeCursorAtScenePos.connect(this, "placeCursorAtScenePos(QPointF)");
		
		scene.addItem(cursor);
		
	}
	
	protected void placeCursorAtScenePos(QPointF pos) {
		cursor.setPosition(types.TypeSystem.timelineTypeHandler.createNewFromScenePos(pos));
	} 
	
	protected void mouseAtScenePos(QPointF pos) {
		super.mouseAtScenePos(pos);
		
		GenericSequenceViewWidget<Double, Double> viewWidget = this.getSequenceViewWidget();
		viewWidget.getViewInfoWidget().setPositionInfo(types.TypeSystem.doubleTypeHandler.getValueStringFromScenePos(pos), types.TypeSystem.timelineTypeHandler.getValueStringFromScenePos(pos)+" ms");
	}
}
