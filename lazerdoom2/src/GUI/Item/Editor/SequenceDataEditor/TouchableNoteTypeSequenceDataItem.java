package GUI.Item.Editor.SequenceDataEditor;

import java.util.Map.Entry;

import sparshui.common.Event;
import sparshui.common.messages.events.DragEvent;
import Control.Types.DoubleType;
import Control.Types.NoteType;
import GUI.Editor.Editor;
import GUI.Scene.Editor.EventPointsDoubleSequenceScene;
import GUI.Scene.Editor.NoteEventScene;
import GUI.Scene.Editor.SequenceDataEditorScene;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsLineItem;
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

import edu.uci.ics.jung.graph.util.Pair;

public class TouchableNoteTypeSequenceDataItem extends TouchableSequenceDataItem<DoubleType> {

	public Signal3<TouchableSequenceDataItem, QPointF, Boolean> dragged = new Signal3<TouchableSequenceDataItem, QPointF, Boolean>();
	private boolean isNoteOff = false;
	private TouchableNoteTypeSequenceDataItem parent = null;
	private TouchableNoteTypeSequenceDataItem child = null;
	private QPen linePen = new QPen(QColor.red);
	
	public TouchableNoteTypeSequenceDataItem(Editor editor, TouchableNoteTypeSequenceDataItem parentItem) {
		
		super(editor);
		// TODO Auto-generated constructor stub
		//this.scale(1.5, 1.5);
		this.setZValue(100.0);
		
		linePen.setWidth(5);
		//line.setParentItem(this);
		this.setFlag(GraphicsItemFlag.ItemIgnoresTransformations, true);
		if(parentItem != null) {
			this.isNoteOff = true;
			this.parent = parentItem;
			this.child = this;
			this.parent.setNoteOff(this);
			this.line = new Line();
			this.line.setPos(this.pos());
		} else {
			this.parent = this;
		}
		
		//this.line.setParentItem(this);
	}
	
public TouchableNoteTypeSequenceDataItem(long tick, NoteType value, Editor editor, TouchableNoteTypeSequenceDataItem parentItem) {
		
		super(editor);
		// TODO Auto-generated constructor stub
		//this.scale(1.5, 1.5);
		this.setZValue(100.0);
		
		linePen.setWidth(5);
		//line.setParentItem(this);
		this.setFlag(GraphicsItemFlag.ItemIgnoresTransformations, true);
		if(parentItem != null) {
			this.isNoteOff = true;
			this.parent = parentItem;
			this.child = this;
			this.parent.setNoteOff(this);
			this.line = new Line();
			this.line.setPos(this.pos());
		} else {
			this.parent = this;
		}
		
		this.currentTick = tick;
		this.currentValue = value;
		//this.line.setParentItem(this);
	}
	
	public QPointF getPositionFromTickValue(long tick, NoteType value) {
		double x = tick*SequenceDataEditorScene.tickMultiplyer;
		double y = ((NoteType.noteScaleSize - value.getNoteNumber())*NoteEventScene.hGridSize)+NoteEventScene.hSceneStart;
		return new QPointF(x,y);
	} 
	@Override
	public void destroy() {
		System.out.println(this.scene());
		this.scene().removeItem(line);
	}
	
	public boolean isNoteOff() {
		return this.isNoteOff;
	}
	
	private void setNoteOff(TouchableNoteTypeSequenceDataItem child) {
		this.child = child;
	}
	
	public TouchableNoteTypeSequenceDataItem noteOn() {
		return this.parent;
	}
	
	public TouchableNoteTypeSequenceDataItem noteOff() {
		return this.child;
	}

	private static String svgFileName = System.getProperty("user.dir")+"/src/GUI/Item/SVG/node-icons.svg";
	private static QSvgRenderer sharedRenderer = new QSvgRenderer(svgFileName);
	
	private QRectF boundingRect = new QRectF(-30,-30,80,80);
	private QRectF SVGboundingRect = new QRectF(-20,-20,40,40);
	private QRectF SVGboundingRect2 = new QRectF(-15,-15,30,30);
	private Line line = null; 
	
	private class Line extends QGraphicsLineItem {
		private TouchableDoubleTypeSequenceDataItem parent;
		private QLineF line;
		private QPen linePen = new QPen(QColor.red);
		
		public Line() {
			linePen.setWidth(4);
			this.setPen(linePen);
			this.setZValue(-100.0);
		}
		
		QPointF op1 = null;
		QPointF op2 = null;
		public void updateLine(QPointF p1, QPointF p2) {
			if(!p1.equals(op1) || ! p2.equals(p2)) {
				line = new QLineF(p1, p2);
				this.setLine(line);
			}
			//this.setPos(parentPos.x(), 0);
			//System.out.println(line);
		}
	}
	
	// ugly as hell, but ignoretransform-flag makes it impossible to recalculate the inverse transform for the line
	boolean firstRun = true;
	@Override
	public void paint(QPainter painter, QStyleOptionGraphicsItem option, QWidget widget) {
		if(!this.isNoteOff && this.currentValue != null) {
			sharedRenderer.render(painter, "node", this.SVGboundingRect);
			painter.setPen(QColor.black);
			//painter.scale(0.7, 0.7);
			//painter.drawText(-50, -50, 62, 100, Qt.AlignmentFlag.AlignHCenter.ordinal(), ""+NoteType.noteNameArray[this.getNoteIndexFromPos(this.pos())]);
		} else if(this.isNoteOff && this.parent != null) {
			sharedRenderer.render(painter, "node", this.SVGboundingRect2);
			//painter.setPen(this.linePen);
			//painter.drawLine(-16, 0,0,0);
			if(firstRun) {
				this.scene().addItem(line);
				firstRun = false;
			}
			
			line.updateLine(this.pos(),this.parent.pos());
		}
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
	public Pair<Object> getOldTickValuePair() {
		return oldTickValuePair;
	}
	
	public DoubleType getCurrentValue() {
		return this.currentValue;
	}
	
	public long getCurrenTick() {
		return this.currentTick;
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
	
	private int getNoteIndexFromPos(QPointF pos) {
		int value = 0;
		
		value = NoteType.noteScaleSize - ((int)(pos.y()-NoteEventScene.hSceneStart))/NoteEventScene.hGridSize;
		
		/*
		 
		  y = (value+NoteType.noteScaleSize)*NoteEventScene.hGridSize+-NoteEventScene.hSceneStart;
		 
		 */
		
		return value;
	}
	
	public void updateTickAndValueFromPosition(QPointF pos) {
		System.out.println("POS "+pos);
		long tick;
		int value;
		
		DoubleType val;
		
		value = this.getNoteIndexFromPos(pos);
		
		tick = (long) (pos.x()/SequenceDataEditorScene.tickMultiplyer);
		
		if(this.isNoteOff) {
			val = new NoteType(value, 0.0f);
		} else {
			val = new NoteType(value, 0.8f);
		}
		
		if(tick < 0) {
			tick = 0;
		}
		
		this.currentTick = tick;
		this.currentValue = val;
		
		if(this.isNoteOff && this.oldTickValuePair == null) {
			this.oldTickValuePair = new Pair<Object>(new Long(currentTick), currentValue);
		}
	}

	@Override
	public boolean setPosition(QPointF pos) {
		this.setPos(pos);
		
		return true;
	}

	@Override
	public String getValueLabelText() {
		return ""+NoteType.noteNameArray[this.getNoteIndexFromPos(this.pos())];
	}

	
}
