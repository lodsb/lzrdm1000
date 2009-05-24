package SceneItems;

import java.util.LinkedList;
import java.util.List;

import sparshui.common.Event;
import sparshui.common.TouchState;
import sparshui.common.messages.events.TouchEvent;
import sparshui.gestures.GestureType;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.Qt.AlignmentFlag;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QGraphicsEllipseItem;
import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QGraphicsPathItem;
import com.trolltech.qt.gui.QGraphicsTextItem;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPainterPath;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QTransform;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QPainter.RenderHint;

public class ContextMenu extends QGraphicsEllipseItem implements TouchItemInterface {
	// port to TouchableGraphicsItem
	
	private QRectF size = new QRectF(-50,-50,100,100);
	
	private QColor startColor = QColor.red;
	
	private int divisions = 4;
	
	private int highlighedSelection = 6;
	
	private String contextMenuLabel = "Modifications";
	private String accept = "ok";
	
	private int id = Util.getGroupID();
	private LinkedList<Integer> viewGestures = new LinkedList<Integer>();
	
	private LinkedList<HighlightableItem> childItems = new LinkedList<HighlightableItem>();
	
	private TextItem caption;
	private TextItem menuItemCaption;
	private QTimer animationTimer = new QTimer();
	private double captionRotationIncrease = -1.0;
	private double menuItemRotationIncrease = -3.0;
	
	private LinkedList<String> menuItemStrings;
	
	public Signal1<String> itemSelected = new Signal1<String>();
	public Signal1<String> selectionFinished = new Signal1<String>();
	private CenterItem center;
	private String currentSelection = null;
	
	private class TextItem extends QGraphicsEllipseItem {
		private String caption;
		
		public TextItem(QRectF rect, String caption) {
			this.caption = caption;
			this.setRect(rect);
			this.setZValue(-10.0);
		}
		
		public void setCaption(String caption) {
			this.caption = caption;
			this.update(this.boundingRect());
		}
		
		public void paint(QPainter painter, QStyleOptionGraphicsItem option, QWidget widget) {
			if(caption.equals("")) {
				return;
			}
			
			QPainterPath path = new QPainterPath();
			//path.moveTo(0,0);

			String label = caption+"-";

			path.addEllipse(this.boundingRect().adjusted(5, 5, -5, -5));
			path.closeSubpath();

			QPen pen = new QPen();
			pen.setColor(QColor.black);
			painter.setPen(pen);

			double percentIncrease =  1.0/(30.0);
			double percent = 0;

			int j = 0;
			for ( int i = 0; percent < 1.0; i++ ) {

				if((((int)(1.0/percentIncrease)) - i < label.length()) && j == label.length()-1) {
					break;
				}

				percent += percentIncrease;
				if(percent > 1.0) {
					break;
				}

				QPointF point = path.pointAtPercent(percent);
				double angle = path.angleAtPercent(percent);


				String textChar;
				if(j >= label.length()) {
					j = 0;
				}

				textChar = label.substring(j, j+1);
				j++;


				painter.save();
				painter.translate(point);
				painter.rotate(-angle);
				painter.drawText(0,0, textChar);
				painter.restore();
			}
		}
	}
	
	private class PieItem extends QGraphicsPathItem implements TouchItemInterface, HighlightableItem {
		
		private LinkedList<Integer> viewGestures = new LinkedList<Integer>();
		
		private QBrush normalBrush;
		private QBrush highlightedBrush = new QBrush(QColor.white);
		
		private TouchItemInterface parent;

		private boolean highlighted = false;
		
		private PieItem(TouchItemInterface parent, QPainterPath path, QColor color) {
			this.parent = parent;
			setPath(path);
			
			normalBrush = new QBrush(color);
			
			setBrush(normalBrush);
			setZValue(-1.0);
			
			viewGestures.addLast(sparshui.gestures.GestureType.TOUCH_GESTURE.ordinal());
		}

		public void setHighlighted(boolean highlight) {
			if(highlight) {
				this.setBrush(highlightedBrush);
			} else {
				this.setBrush(normalBrush);
			}
			this.highlighted  = highlight;
		}
		
		public boolean isHighlighted() {
			return highlighted;
		}

		@Override
		public List<Integer> getAllowedGestures() {
			// pass to parent
			return parent.getAllowedGestures();
		}

		@Override
		public int getGroupID() {
			// pass to parent
			return parent.getGroupID();
		}

		@Override
		public boolean processEvent(Event event) {
			return parent.processEvent(event);
		}
	}
	
	private class CenterItem extends QGraphicsEllipseItem implements TouchItemInterface, HighlightableItem {

		private TouchItemInterface parent;
		private boolean highlighted = false;
		
		private QBrush normalBrush = new QBrush(QColor.lightGray);
		private QBrush readyBrush = new QBrush(QColor.green);
		private QBrush highlightBrush = new QBrush(QColor.white);
		private String caption;
		private QRectF size = new QRectF(-20,-20,40,40);
		
		private boolean isReady = false;
		
		public void setReady(boolean ready) {
			this.isReady = ready;
			if(ready) {
				this.setBrush(readyBrush);
			} else if(highlighted) {
				this.setBrush(highlightBrush);
			} else {
				this.setBrush(normalBrush);
			}
		}
		
		public boolean isReady() {
			return isReady;
		}
		
		public CenterItem(TouchItemInterface parent, String caption) {
			this.parent = parent;
			this.setBrush(normalBrush);
			this.caption = caption;
			this.setRect(size);
		}
		
		@Override
		public List<Integer> getAllowedGestures() {
			return parent.getAllowedGestures();
		}

		@Override
		public int getGroupID() {
			// TODO Auto-generated method stub
			return parent.getGroupID();
		}

		@Override
		public boolean processEvent(Event event) {
			// TODO Auto-generated method stub
			return parent.processEvent(event);
		}

		@Override
		public boolean isHighlighted() {
			return highlighted;
		}

		@Override
		public void setHighlighted(boolean b) {
			highlighted = b;
			if(highlighted) {
				this.setBrush(highlightBrush);
			} else if(isReady){
				this.setBrush(readyBrush);
			} else {
				this.setBrush(normalBrush);
			}
		}
		
		public void paint(QPainter painter, QStyleOptionGraphicsItem option, QWidget widget) {
			super.paint(painter, option, widget);
			painter.drawText(-20,-20,40,40,0x84 , caption);
		}
		
	}
	
	public ContextMenu(LinkedList<String> menuItemCaptions, String menuCaption, String centerCaption) {
		this.setRect(size.adjusted(-20, -20, 20, 20));
		this.setPen(new QPen(QColor.black));
		this.setBrush(new QBrush(QColor.yellow));
		
		menuItemStrings = menuItemCaptions;
		
		divisions = menuItemStrings.size();
		
		QColor currentColor = new QColor(startColor);
		for(int i=0; i < divisions ; i++) {
			PieItem item= new PieItem(this, piePath(size, (i*360/divisions)*16, (360/divisions)*16), currentColor);
			currentColor.setHsv(((i+1)*360/divisions), currentColor.saturation(), currentColor.value());
			childItems.add(item);
			System.out.println("i "+i+" "+menuItemStrings.get(i));
			item.setParentItem(this);
		}
		center = new CenterItem(this, centerCaption);
		center.setParentItem(this);
		childItems.add(center);
		
		caption = new TextItem(size.adjusted(-10,-10,10,10), menuCaption);
		caption.setParentItem(this);
		animationTimer.timeout.connect(this, "updateCaptionRotation()");
		animationTimer.setInterval(1000);
		animationTimer.start();
		
		menuItemCaption = new TextItem(size.adjusted(-30, -30, 30, 30), "");
		menuItemCaption.setParentItem(this);
		
		viewGestures.add(GestureType.TOUCH_GESTURE.ordinal());
		
	}
	
	private void updateCaptionRotation() {
		caption.rotate(captionRotationIncrease);
		menuItemCaption.rotate(menuItemRotationIncrease);
	}
	
	@Override
	public void paint(QPainter painter, QStyleOptionGraphicsItem option, QWidget widget) {
		/*super.paint(painter, option, widget);
		
		painter.drawEllipse(size);
		painter.setRenderHint(RenderHint.Antialiasing);
		*/
	}
	
	private QPainterPath piePath(QRectF r, int a, int alen)	{
		if (a > (360*16)) {
			a = a % (360*16);
		} else if (a < 0) {
			a = a % (360*16);
			if (a < 0) a += (360*16);
		}

		QRectF rect = r.normalized();

		QPainterPath path = new QPainterPath();
		path.moveTo(rect.center());
		path.arcTo(rect.x(), rect.y(), rect.width(), rect.height(), a/16.0, alen/16.0);
		path.closeSubpath();
		return path;
	}

	@Override
	public List<Integer> getAllowedGestures() {
		return viewGestures;
	}

	@Override
	public int getGroupID() {
		return id;
	}
	
	private void selectChild(HighlightableItem p) {
		int i = 0;
		for(HighlightableItem it: childItems) {
			if(p == it) {
				p.setHighlighted(true);
				if(p == center) {
					menuItemCaption.setCaption("");
				} else {
					String selection = menuItemStrings.get(i);
					currentSelection = selection;
					itemSelected.emit(selection);
					menuItemCaption.setCaption(selection);
					center.setReady(true);
				}
			} else {
				it.setHighlighted(false);
			}
			i++;
		}
	}

	public void resetUiState() {
		center.setReady(false);
	}
	
	@Override
	public boolean processEvent(Event event) {
		if(event instanceof TouchEvent) {
			TouchEvent e = (TouchEvent)event;
			
			QPointF coordinate = mapFromScene(lazerdoom.View.getInstance().convertScreenPos(((TouchEvent) event).getX(),((TouchEvent) event).getY()));
			//System.out.println(coordinate);
			for(HighlightableItem it: childItems) {
				if(it.shape().contains(coordinate)) {
					if(it == center && e.getState() == TouchState.DEATH) {
						if(center.isReady()) {
							//System.out.println("emit "+currentSelection);
							selectionFinished.emit(currentSelection);
							selectChild(it);
						}
					} else if(it != center) {
						selectChild(it);
					}
				}
			}
		}
		return false;
	}
}
