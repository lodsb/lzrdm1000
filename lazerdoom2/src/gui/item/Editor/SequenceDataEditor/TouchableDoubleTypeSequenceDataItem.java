package gui.item.Editor.SequenceDataEditor;

import java.util.Map.Entry;

import sparshui.common.Event;
import sparshui.common.messages.events.DragEvent;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsLineItem;
import com.trolltech.qt.gui.QGraphicsTextItem;
import com.trolltech.qt.gui.QLineF;
import com.trolltech.qt.gui.QMatrix;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QGraphicsItem.GraphicsItemFlag;
import com.trolltech.qt.gui.QTextOption.Flag;
import com.trolltech.qt.gui.QTextOption.Flags;
import com.trolltech.qt.svg.QSvgRenderer;

import control.types.DoubleType;

import edu.uci.ics.jung.graph.util.Pair;
import gui.editor.Editor;
import gui.scene.editor.EventPointsDoubleSequenceScene;
import gui.scene.editor.SequenceDataEditorScene;

public class TouchableDoubleTypeSequenceDataItem extends TouchableSequenceDataItem<DoubleType> {

	public Signal3<TouchableSequenceDataItem, QPointF, Boolean> dragged = new Signal3<TouchableSequenceDataItem, QPointF, Boolean>();
	
	public TouchableDoubleTypeSequenceDataItem(Editor editor) {
		super(editor);
		// TODO Auto-generated constructor stub
		//this.scale(1.5, 1.5);
		this.setZValue(100.0);
		//line.setParentItem(this);
		this.setFlag(GraphicsItemFlag.ItemIgnoresTransformations, true);
		//line.setFlag(GraphicsItemFlag.ItemIgnoresTransformations, true);
	}
	
	public TouchableDoubleTypeSequenceDataItem(long tick, DoubleType value, Editor editor) {
		super(editor);
		// TODO Auto-generated constructor stub
		//this.scale(1.5, 1.5);
		this.setZValue(100.0);
		//line.setParentItem(this);
		this.setFlag(GraphicsItemFlag.ItemIgnoresTransformations, true);
		
		//line.setFlag(GraphicsItemFlag.ItemIgnoresTransformations, true);
		this.setTickAndValue(tick, value);
	}

	private static String svgFileName = System.getProperty("user.dir")+"/src/gui/item/SVG/node-icons.svg";
	private static QSvgRenderer sharedRenderer = new QSvgRenderer(svgFileName);
	
	private QRectF boundingRect = new QRectF(-30,-30,80,80);
	private QRectF SVGboundingRect = new QRectF(-20,-20,40,40);
	
	private class Line extends QGraphicsLineItem {
		private TouchableDoubleTypeSequenceDataItem parent;
		private QLineF line;
		private QPen linePen = new QPen(QColor.red);
		
		public Line(TouchableDoubleTypeSequenceDataItem parent) {
			this.parent = parent;
			this.updateLine();
			//linePen.setWidth(5);
			this.setPen(linePen);
			this.setZValue(-100.0);
		}
		
		public void updateLine() {
			QPointF parentPos = parent.pos();
			
			line = new QLineF(0, 0, 0, -parentPos.y());
			this.setLine(line);
			//this.setPos(parentPos.x(), 0);
			//System.out.println(line);
		}
	}
	
/*	@Override
	public Object itemChange(GraphicsItemChange change, Object value) {
		// ignore scaling
		if(change == GraphicsItemChange.ItemMatrixChange) {
			QMatrix matrix = (QMatrix) value;
			matrix.setMatrix(matrix.m11(), matrix.m12(), 1.0, 1.0, matrix.dx(), matrix.dy());
			value = matrix;
		}
		
		return super.itemChange(change, value);
	}*/
	
	@Override
	public void paint(QPainter painter, QStyleOptionGraphicsItem option, QWidget widget) {
		//FIXME: a whole lot of repaints?! why?
		
	//	System.out.println("--- "+this.pos());
		sharedRenderer.render(painter, "node", this.SVGboundingRect);
		/*if(this.currentValue != null) {
			painter.setPen(QColor.black);
			//painter.scale(0.7, 0.7);
			//painter.drawText(-70, -50, 100, 100, Qt.AlignmentFlag.AlignHCenter.ordinal(), ""+this.getDoubleValFromPos(this.pos()));
		}*/
		//line.updateLine();
	}
	
	//private Line line = new Line(this);
	private DoubleType currentValue = null;
	private long currentTick = 0;
	
	@Override
	public Pair<Object> getTickValuePairFromPosition() {
		// TODO Auto-generated method stub
		//this.updateTickAndValueFromPosition(this.pos());
		return new Pair<Object>(currentTick, currentValue);
	}
	
	@Override 
	public QRectF boundingRect() {
		return this.boundingRect;
	}
	
	boolean firstEvent = true;
	
	Pair<Object> oldTickValuePair = null;
	@Override
	public Pair<Object> getOldTickValuePair() {
		return oldTickValuePair;
	}
	
	public boolean processEvent(Event e) {
		if(e instanceof DragEvent) {
			
			this.dragged.emit(this, e.getSceneLocation(), ((DragEvent) e).isSuccessful());
			
			if(((DragEvent) e).isSuccessful()) {
				firstEvent = true;
			} else if(firstEvent) {
				firstEvent = false;
				oldTickValuePair = new Pair<Object>(new Long(currentTick), currentValue); 
			}
		}
		
		return true;
	}
	
	public QPointF getPositionFromTickValue(long tick, double value) {
		return new QPointF(tick*SequenceDataEditorScene.tickMultiplyer, -value*SequenceDataEditorScene.valueMultiplyer);
	}
	
	private double getDoubleValFromPos(QPointF pos) {
		double value = 0.0;
		
		value = pos.y()/SequenceDataEditorScene.valueMultiplyer;
		
		if(value > 1.0) {
			value = 1.0;
		}
		
		if(value < -1.0) {
			value = 1.0;
		}
		
		return -value;
	}
	//ugly
	private void setTickAndValue(long tick, DoubleType value) {
		this.currentTick = tick;
		this.currentValue = value;
	}
	
	public void updateTickAndValueFromPosition(QPointF pos) {
		long tick;
		double value;
		
		DoubleType val;
		
		value = this.getDoubleValFromPos(pos);
		
		tick = (long) (pos.x()/SequenceDataEditorScene.tickMultiplyer);
		
		// ugh...	
		val = new DoubleType(value);
		
		if(tick < 0) {
			tick = 0;
		}
		
		this.currentTick = tick;
		this.currentValue = val;
	}

	@Override
	public boolean setPosition(QPointF pos) {
		System.out.println("POSITION "+pos+" scene "+this.scene());
		this.setPos(pos);
		return true;
	}

	@Override
	public String getValueLabelText() {
		// TODO Auto-generated method stub
		return ""+this.getDoubleValFromPos(this.pos());
	}

	
}
