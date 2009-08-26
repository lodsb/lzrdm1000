package GUI.Item.Editor.SequenceDataEditor;

import java.util.Map.Entry;
import Control.Types.DoubleType;
import GUI.Scene.Editor.SequenceDataEditorScene;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsLineItem;
import com.trolltech.qt.gui.QLineF;
import com.trolltech.qt.gui.QMatrix;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.svg.QSvgRenderer;

import edu.uci.ics.jung.graph.util.Pair;

public class TouchableDoubleTypeSequenceDataItem extends TouchableSequenceDataItem<DoubleType> {

	private static String svgFileName = System.getProperty("user.dir")+"/src/GUI/Item/SVG/node-icons.svg";
	private static QSvgRenderer sharedRenderer = new QSvgRenderer(svgFileName);
	
	private QRectF boundingRect = new QRectF(-50,-50,100,100);
	
	private class Line extends QGraphicsLineItem {
		private TouchableDoubleTypeSequenceDataItem parent;
		private QLineF line;
		private QPen linePen = new QPen(QColor.red);
		
		public Line(TouchableDoubleTypeSequenceDataItem parent) {
			this.parent = parent;
			this.updateLine();
			linePen.setWidth(10);
			this.setPen(linePen);
			this.setZValue(-100.0);
		}
		
		public void updateLine() {
			QPointF parentPos = parent.pos();
			
			line = new QLineF(0, 0, 0, parentPos.y());
			this.setLine(line);
			this.setPos(parentPos.x(), 0);
			System.out.println(line);
		}
	}
	
	@Override
	public Object itemChange(GraphicsItemChange change, Object value) {
		// ignore scaling
		if(change == GraphicsItemChange.ItemMatrixChange) {
			QMatrix matrix = (QMatrix) value;
			matrix.setMatrix(matrix.m11(), matrix.m12(), 1.0, 1.0, matrix.dx(), matrix.dy());
			value = matrix;
		}
		
		return super.itemChange(change, value);
	}
	
	boolean firstRun = true;
	
	@Override
	public void paint(QPainter painter, QStyleOptionGraphicsItem option, QWidget widget) {
		System.out.println("--- "+this.pos());
		sharedRenderer.render(painter, "node", this.boundingRect());
		line.updateLine();
		if(firstRun) {
			this.scene().addItem(line);
			firstRun = false;
		}
	}
	
	private Line line = new Line(this);
	private DoubleType currentValue = null;
	private long currentTick = 0;
	
	@Override
	public Pair<Object> getValue() {
		// TODO Auto-generated method stub
		Entry<Long, DoubleType> entry;
		
		return new Pair<Object>(currentTick, currentValue);
	}
	
	@Override 
	public QRectF boundingRect() {
		return this.boundingRect;
	}
	
	@Override
	public void setValue(long tick, DoubleType value) {
		double x = 0;
		double y = 0;
		
		double val = value.getFloatValue();
		
		if(tick < 0) {
			tick = 0;
		}
		
		x = SequenceDataEditorScene.tickMultiplyer*(double)tick;
		
		if(val < -1.0) {
			val = -1.0;
			value.setValue(val);
		} else if(val > 1.0) {
			val = 1.0;
			value.setValue(val);
		}
		
		y = SequenceDataEditorScene.valueMultiplyer*val;
		
		this.setPos(x, -y);
		
		this.line.updateLine();
		
		currentTick = tick;
		currentValue = value;
	}

	@Override
	public void setValueFromPosition(QPointF pos) {
		long tick;
		double value;
		
		DoubleType val;
		
		value = pos.y()*SequenceDataEditorScene.valueMultiplyer;
		
		if(value > 1.0) {
			value = 1.0;
		}
		
		if(value < -1.0) {
			value = 1.0;
		}
		
		val = new DoubleType(value);
		
		tick = (long) (pos.x()*SequenceDataEditorScene.tickMultiplyer);
		
		if(tick < 0) {
			tick = 0;
		}
		
		this.setValue(tick, val);
	}

	
}
