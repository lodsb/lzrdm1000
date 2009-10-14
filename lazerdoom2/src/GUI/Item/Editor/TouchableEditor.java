package GUI.Item.Editor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import sparshui.common.Event;
import sparshui.common.TouchState;
import sparshui.common.messages.events.DragEvent;
import sparshui.common.messages.events.RotateEvent;
import sparshui.common.messages.events.TouchEvent;
import sparshui.common.messages.events.ZoomEvent2D;
import sparshui.gestures.GestureType;
import sparshui.gestures.ZoomGesture2D;
import sparshui.gestures.GestureType;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSizeF;
import com.trolltech.qt.core.Qt.Orientation;
import com.trolltech.qt.core.Qt.ScrollBarPolicy;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QGraphicsLinearLayout;
import com.trolltech.qt.gui.QGraphicsTextItem;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPainterPath;
import com.trolltech.qt.gui.QRadialGradient;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QTransform;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QGraphicsItem.GraphicsItemFlag;
import com.trolltech.qt.svg.QGraphicsSvgItem;
import com.trolltech.qt.svg.QSvgRenderer;

import edu.uci.ics.jung.graph.util.Pair;

import GUI.Editor.BaseSequencerItemEditor;
import GUI.Editor.Editor;
import GUI.Multitouch.TouchableGraphicsItem;
import GUI.Scene.Editor.EditorScene;
import GUI.View.SequencerView;
import SceneItems.*;

public class TouchableEditor extends TouchableGraphicsWidget {


	public Signal0 closeEditor = new Signal0();
	
	public class EditorButton extends TouchableGraphicsItem {
		
		public Signal0 pressed = new Signal0();
		// botton-icons.svg includes:
		// "delete"
		// "addSynth"
		// "addSequence"
		
		private String svgFileName = System.getProperty("user.dir")+"/src/GUI/Item/SVG/button-icons.svg";

		private QSvgRenderer renderer = new QSvgRenderer(svgFileName);
		private String elementID;
		
		private QRectF boundingRect = new QRectF(-40.0, -40.0, 80,80);
		private LinkedList<Integer> gestures = new LinkedList<Integer>();
		
		public EditorButton() {
			gestures.add(sparshui.gestures.GestureType.TOUCH_GESTURE.ordinal());
		}
		
		/*
		@Override
		public List<Integer> getAllowedGestures() {
			System.out.println("WJWJWJWJWJWJWJWJWJWJ "+this.getGroupID());
			return gestures;
		}
		
		@Override
		public boolean processEvent(Event event) {
			System.out.println("CLOSE PRESSSED");
			if(event instanceof TouchEvent) {
				TouchEvent e = (TouchEvent) event;
				if(e.getState() == TouchState.DEATH) {
					pressed.emit();
				}
			}
			
			return true;
		}
		*/
		public String elementID() {
			return elementID;
		}
		
		public EditorButton(String elementID) {
			super();
			this.elementID = elementID;
		}

		@Override
		public QRectF boundingRect() {
			return boundingRect;
		}

		@Override
		public void paint(QPainter painter, QStyleOptionGraphicsItem option, QWidget widget) {
			renderer.render(painter, this.elementID, this.boundingRect());	
		}

		@Override
		public QSizeF getPreferedSize() {
			QRectF rect = boundingRect();
			return new QSizeF(rect.width(), rect.height());
		}

		@Override
		public void setGeometry(QRectF size) {
		}

		@Override
		public QSizeF getMaximumSize() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

	
	
	public class EditorHeader extends TouchableGraphicsItem {
		private QRectF boundingRect;
		private QColor color = QColor.black;
		private QPainterPath path;
		private QColor normalColor = new QColor(130,130,130);
		private QBrush gradientBrush;
		
		private void setBrushes() {
			double rad = 100;
			QRadialGradient gr = new QRadialGradient(100,20, 400, 50,50);
			gr.setColorAt(0.0, new QColor(255, 255, 255, 80));
			gr.setColorAt(0.3, new QColor(155, 150, 150, 80));
			gr.setColorAt(0.9, new QColor(50, 50, 50, 30));
			gr.setColorAt(0.95, new QColor(0, 0, 0, 20));
			gr.setColorAt(1, new QColor(0, 0, 0, 0));

			gradientBrush = new QBrush(gr);
		}
		
		private void updatePath() {
			path = new QPainterPath();
			path.moveTo(this.boundingRect.bottomLeft());
			QPointF centerTop = this.boundingRect.center();
			centerTop.setY(this.boundingRect.top());
			centerTop.setX(boundingRect.left()+50);
			
			QPointF ctrl1 = this.boundingRect.bottomLeft();
			ctrl1.setX(ctrl1.x());
			ctrl1.setY(0);
			
			QPointF ctrl2 = this.boundingRect.bottomLeft();
			ctrl2.setX(ctrl2.x());
			ctrl2.setY(0);

			path.cubicTo(ctrl1, ctrl2, centerTop);
			
			centerTop.setY(this.boundingRect.top());
			centerTop.setX(boundingRect.right()-50);
			
			path.lineTo(centerTop);

			ctrl1 = this.boundingRect.bottomRight();
			ctrl1.setX(ctrl1.x());
			ctrl1.setY(0);
			
			ctrl2 = this.boundingRect.bottomRight();
			ctrl2.setX(ctrl2.x());
			ctrl2.setY(0);
			
			path.cubicTo(ctrl1, ctrl2, this.boundingRect.bottomRight());
			path.closeSubpath();
		}
		
		public EditorHeader(QRectF size) {
			this.boundingRect = size;
			this.setBrushes();
			this.updatePath();
		}
		
		public void setColor(QColor color) {
			this.color = color;
			this.update();
		}
		
		@Override
		public QRectF boundingRect() {
			return boundingRect;
		}

		@Override
		public void paint(QPainter painter, QStyleOptionGraphicsItem option,
				QWidget widget) {
			
			painter.setPen(normalColor);
				painter.setBrush(color);
				painter.drawPath(path);
				painter.fillPath(path, gradientBrush);
		}

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

	
/*	public class EditorViewHandle extends TouchableGraphicsItem {
		private QRectF boundingRect;
		private QColor color = QColor.black;
		private QPainterPath path;
		private QColor normalColor = new QColor(130,130,130);
		private QBrush gradientBrush;
		
		private void setBrushes() {
			double rad = 100;
			QRadialGradient gr = new QRadialGradient(100,20, 400, 50,50);
			gr.setColorAt(0.0, new QColor(255, 255, 255, 80));
			gr.setColorAt(0.3, new QColor(155, 150, 150, 80));
			gr.setColorAt(0.9, new QColor(50, 50, 50, 30));
			gr.setColorAt(0.95, new QColor(0, 0, 0, 20));
			gr.setColorAt(1, new QColor(0, 0, 0, 0));

			gradientBrush = new QBrush(gr);
		}
		
		private void updatePath() {
			path = new QPainterPath();
			path.moveTo(this.boundingRect.bottomLeft());
			QPointF centerTop = this.boundingRect.center();
			centerTop.setY(this.boundingRect.top());
			centerTop.setX(boundingRect.left()+50);
			
			QPointF ctrl1 = this.boundingRect.bottomLeft();
			ctrl1.setX(ctrl1.x());
			ctrl1.setY(0);
			
			QPointF ctrl2 = this.boundingRect.bottomLeft();
			ctrl2.setX(ctrl2.x());
			ctrl2.setY(0);

			path.cubicTo(ctrl1, ctrl2, centerTop);
			
			centerTop.setY(this.boundingRect.top());
			centerTop.setX(boundingRect.right()-50);
			
			path.lineTo(centerTop);

			ctrl1 = this.boundingRect.bottomRight();
			ctrl1.setX(ctrl1.x());
			ctrl1.setY(0);
			
			ctrl2 = this.boundingRect.bottomRight();
			ctrl2.setX(ctrl2.x());
			ctrl2.setY(0);
			
			path.cubicTo(ctrl1, ctrl2, this.boundingRect.bottomRight());
			path.closeSubpath();
		}
		
		private LinkedList<Integer> allowedGestures = new LinkedList<Integer>();
		
		public EditorViewHandle(QRectF size) {
			this.boundingRect = size;
			this.setBrushes();
			this.updatePath();
			
			allowedGestures.add(GestureType.TOUCH_GESTURE.ordinal());
		}
		
		public void setColor(QColor color) {
			this.color = color;
			this.update();
		}
		
		@Override
		public QRectF boundingRect() {
			return boundingRect;
		}

		@Override
		public void paint(QPainter painter, QStyleOptionGraphicsItem option,
				QWidget widget) {
			
			painter.setPen(normalColor);
				painter.setBrush(color);
				painter.drawPath(path);
				painter.fillPath(path, gradientBrush);
		}

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
		
		public List<Integer> getAllowedGestures() {
			return allowedGestures;
		} 
		
		HashMap<Integer, TouchEvent> currentTouchEvents = new HashMap<Integer, TouchEvent>();
		
		public boolean processEvent(Event event){
			if(event instanceof TouchEvent) {
				TouchEvent e = (TouchEvent) event;
				if(e.getState() == TouchState.DEATH) {
					currentTouchEvents.remove(e.getTouchID());
				} else {
					TouchEvent currentTouchEvent = null;
					if((currentTouchEvent = currentTouchEvents.get(e.getTouchID())) != null) {
						
					}
					
					currentTouchEvents.put(e.getTouchID(), e);
				}
			}
			return true;
		}
		
	}*/

	
	public class EditorViewHandle extends TouchableGraphicsItem {
		private QRectF boundingRect;
		private QColor color = QColor.black;
		private QPainterPath path;
		private QColor normalColor = new QColor(130,130,130);
		private QBrush gradientBrush;
		
		private void setBrushes() {
			double rad = 100;
			QRadialGradient gr = new QRadialGradient(100,20, 400, 50,50);
			gr.setColorAt(0.0, new QColor(255, 255, 255, 80));
			gr.setColorAt(0.3, new QColor(155, 150, 150, 80));
			gr.setColorAt(0.9, new QColor(50, 50, 50, 30));
			gr.setColorAt(0.95, new QColor(0, 0, 0, 20));
			gr.setColorAt(1, new QColor(0, 0, 0, 0));

			gradientBrush = new QBrush(gr);
		}
		
		private void updatePath() {
			path = new QPainterPath();
			path.moveTo(this.boundingRect.bottomLeft());
			QPointF centerTop = this.boundingRect.center();
			centerTop.setY(this.boundingRect.top());
			centerTop.setX(boundingRect.left()+50);
			
			QPointF ctrl1 = this.boundingRect.bottomLeft();
			ctrl1.setX(ctrl1.x());
			ctrl1.setY(0);
			
			QPointF ctrl2 = this.boundingRect.bottomLeft();
			ctrl2.setX(ctrl2.x());
			ctrl2.setY(0);

			path.cubicTo(ctrl1, ctrl2, centerTop);
			
			centerTop.setY(this.boundingRect.top());
			centerTop.setX(boundingRect.right()-50);
			
			path.lineTo(centerTop);

			ctrl1 = this.boundingRect.bottomRight();
			ctrl1.setX(ctrl1.x());
			ctrl1.setY(0);
			
			ctrl2 = this.boundingRect.bottomRight();
			ctrl2.setX(ctrl2.x());
			ctrl2.setY(0);
			
			path.cubicTo(ctrl1, ctrl2, this.boundingRect.bottomRight());
			path.closeSubpath();
		}
		
		private LinkedList<Integer> allowedGestures = new LinkedList<Integer>();
		
		public EditorViewHandle(QRectF size) {
			this.boundingRect = size;
			this.setBrushes();
			this.updatePath();
			
			allowedGestures.add(GestureType.ZOOM_GESTURE2D.ordinal());
			allowedGestures.add(GestureType.TOUCH_GESTURE.ordinal());
		}
		
		public void setColor(QColor color) {
			this.color = color;
			this.update();
		}
		
		@Override
		public QRectF boundingRect() {
			return boundingRect;
		}

		@Override
		public void paint(QPainter painter, QStyleOptionGraphicsItem option,
				QWidget widget) {
			
			painter.setPen(normalColor);
				painter.setBrush(color);
				painter.drawPath(path);
				painter.fillPath(path, gradientBrush);
		}

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
		
		public List<Integer> getAllowedGestures() {
			return allowedGestures;
		} 
		
		private HashMap<Integer, TouchEvent> currentTouchEvents = new HashMap<Integer, TouchEvent>();
		
		public Signal2<Double, Double> scrollBy = new Signal2<Double, Double>();
		
		public Signal2<Double, Double> zoomTo = new Signal2<Double, Double>();
		
		public boolean processEvent(Event event){
			if(event instanceof ZoomEvent2D) {
				ZoomEvent2D ze = (ZoomEvent2D) event;
				System.out.println("ZOOM "+ze.getXZoom()+" "+ze.getYZoom());
				
				zoomTo.emit(ze.getXZoom(),ze.getYZoom());
			} else if(event instanceof TouchEvent) {
				TouchEvent e = (TouchEvent) event;
				if(e.getState() == TouchState.DEATH) {
					currentTouchEvents.remove(e.getTouchID());
				} else {
						// scroll
						TouchEvent currentTouchEvent = null;
						if((currentTouchEvent = currentTouchEvents.get(e.getTouchID())) != null) {
							QPointF oldPos = this.parentItem().mapFromScene(currentTouchEvent.getSceneLocation());
							//System.out.println(oldPos+" --- "+this.parentItem().mapFromScene(oldPos));
							QPointF newPos = this.parentItem().mapFromScene(event.getSceneLocation());
							scrollBy.emit(oldPos.x()-newPos.x(), oldPos.y()-newPos.y());
						}
					currentTouchEvents.put(e.getTouchID(), e);
				}
			}
			return true;
		}
		
	}

	
	private	int id = Util.getGroupID();
	private QSizeF containerSize = new QSizeF(800,600);
	private double zValue = 100.0;
	private EditorHeader header;
	private EditorViewHandle viewHandle;
	
	private TouchableGraphicsView graphicsView;
	private TouchableGraphicsViewContainer graphicsViewContainer;
	private QGraphicsLinearLayout baseLayout;
	
	private LinkedList<Integer> allowedGestures = new LinkedList<Integer>();
	
	//public void shit() {
	//	this.baseLayout.removeItem(graphicsViewContainer);
	//}
	
	private String svgFileName = System.getProperty("user.dir")+"/src/GUI/Item/SVG/editor-frame.svg";
	private QSvgRenderer renderer = new QSvgRenderer(svgFileName);
	
	private BaseSequencerItemEditor editor  = null;
	
	public BaseSequencerItemEditor getCurrentEditor() {
		return editor;
	}
	
	public void setCurrentEditor(BaseSequencerItemEditor editor) {
		this.updateInfoCaption("");
		this.editor = editor;
		EditorScene scene = editor.getScene();
		this.graphicsView.setEditorScene(scene);
		this.show();
	}
	
	public void updateViewScene() {
		this.graphicsView.setEditorScene(this.editor.getScene());
	}
	
	private EditorButton closeButton;
	
	private QGraphicsTextItem infoText;
	
	public TouchableEditor() {
		
		QFont infoFont = new QFont("Helvetica [Cronyx]", 24);
		infoText = new QGraphicsTextItem(this.info);
		infoText.setPos(600, -45);
		infoText.setDefaultTextColor(QColor.white);
		infoText.setFont(infoFont);
		infoText.setZValue(1.0);
		infoText.setParentItem(this);
		//infoText.setHtml("<p align=\"right\">Some<br/>centred<br/>text</p>");
		
		baseLayout = new QGraphicsLinearLayout();
		baseLayout.setOrientation(Orientation.Vertical);
		graphicsView = new TouchableGraphicsView(this);
		graphicsView.setHorizontalScrollBarPolicy(ScrollBarPolicy.ScrollBarAlwaysOff);
		graphicsView.setVerticalScrollBarPolicy(ScrollBarPolicy.ScrollBarAlwaysOff);
		graphicsViewContainer = new TouchableGraphicsViewContainer(graphicsView, containerSize);
		baseLayout.addItem(graphicsViewContainer);
		this.setLayout(baseLayout);
		this.setZValue(zValue);
		
		header = new EditorHeader(new QRectF(0,0,400,75));
		header.rotate(90.0);
		header.setParent(this);
		header.setPos(885, 100);
		
		viewHandle = new EditorViewHandle(new QRectF(0,0,250,180));
		viewHandle.rotate(-90.0);
		viewHandle.setParentItem(this);
		viewHandle.setPos(-172,425);
		
		viewHandle.scrollBy.connect(this, "scrollBy(Double, Double)");
		viewHandle.zoomTo.connect(this, "zoomTo(Double, Double)");
		
		
		/*viewHandle = new EditorViewHandle(new QRectF(0,0,250,160));
		viewHandle.rotate(-90.0);
		viewHandle.setParentItem(this);
		viewHandle.setPos(-152,425);
		*/
		closeButton = new EditorButton("delete");
		closeButton.pressed.connect(this.closeEditor);
		closeButton.setParent(this);
		closeButton.setPos(847,450);
		closeButton.setZValue(10);
		
		this.setFlag(GraphicsItemFlag.ItemIsMovable, true);
		this.setFlag(GraphicsItemFlag.ItemIsSelectable, true);
		
		//this.setParent(SequencerView.getInstance());
		
		this.allowedGestures.add(sparshui.gestures.GestureType.ROTATE_GESTURE.ordinal());
		this.allowedGestures.add(sparshui.gestures.GestureType.DRAG_GESTURE.ordinal());
		this.allowedGestures.add(sparshui.gestures.GestureType.TOUCH_GESTURE.ordinal());
		
		//this.rotate(-193.34);
		

	}
	
	private String info = "";
	public void updateInfoCaption(String info) {
		this.info = info;
		this.infoText.setPlainText(info);
		
	} 
	
	private QRectF frameBounds = new QRectF(-95,-60,940, 680);
	@Override
	public void paint(QPainter painter, QStyleOptionGraphicsItem option, QWidget widget) {
//		painter.setClipRect(option.exposedRect());
		renderer.render(painter, "frame",  frameBounds);
	}
	
	
	
	private double currentZoomX = 1.0;
	private double currentZoomY = 1.0;
	
	private void zoomTo(Double x, Double y) {
		if(this.editor.allowViewpointChange()) {
			this.graphicsView.zoomTo(x, y);
		}
	}
	
	private void scrollBy(Double x, Double y) {
		if(this.editor.allowViewpointChange()) {
			this.graphicsView.scrollBy(x,y);
		}
		//System.out.println(10*x.intValue()+"-------"+y.intValue()*10);
	}
		
	public void setColor(QColor color){
		header.setColor(color);
	}
	
	public Pair<Object> getGroupIDViewCoordinates(QPointF pos) {
		
		return this.graphicsView.getGroupIDViewCoordinates(pos);
	}
	
	@Override
	public List<Integer> getAllowedGestures() {
		System.out.println("GAG");
		return this.allowedGestures;
	}

	@Override
	public int getGroupID() {
		return this.id;
	}

	QPointF dragOffset = null;
	QPointF start = null;
	
	private double currentAngle = 0.0;
	@Override
	public boolean processEvent(Event event) {
		if(event instanceof TouchEvent) {
			if(event.getSource() == this.closeButton && ((TouchEvent)event).getState() == TouchState.DEATH) {
				this.closeEditor.emit();
			}
		}else {
			if(event instanceof RotateEvent) {
				RotateEvent re = (RotateEvent) event;
				System.out.println(re);
				double angle = re.getRotation();
				
				System.out.println("ANGLE bbb "+angle);
				angle = angle + currentAngle;
				System.out.println("ANGLE ---- "+angle);
				this.setTransform(new QTransform().translate(885, 400).rotate(angle).translate(-885,-400), false);
				
				if(!re.isOngoing()) {
					System.out.println(currentAngle+"===");
					currentAngle = angle;
				} /*else {
					currentAngle = 0;
				}*/
				
			} else if(event instanceof DragEvent) {
				DragEvent e = (DragEvent) event;
				
				if(e.isOngoing()) {
					if(dragOffset == null) {
						dragOffset = e.getSceneLocation();
						start = this.pos();
					} else {
						QPointF p = new QPointF(dragOffset.x()-e.getSceneLocation().x(),e.getSceneLocation().y()-dragOffset.y());
						this.setPos(start.x()+(e.getSceneLocation().x()-dragOffset.x()), start.y()+(e.getSceneLocation().y()-dragOffset.y()));
					}
					
				} else {
					dragOffset = null;
				}
			}
		}
		return true;
	}

}
