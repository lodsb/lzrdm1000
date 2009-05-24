package SceneItems;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import sparshui.common.Event;
import sparshui.common.TouchState;
import sparshui.common.messages.events.DblClkEvent;
import sparshui.common.messages.events.TouchEvent;
import sparshui.gestures.GestureType;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSizeF;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.Qt.PenCapStyle;
import com.trolltech.qt.core.Qt.PenJoinStyle;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPainterPath;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;


//http://www.onjava.com/pub/a/onjava/2006/05/10/real-time-java-introduction.html

public class CurveItem  extends TouchableGraphicsItem {

	private QPainterPath curve;
	private QPainterPath shape;
	private QRectF       size;

	private QPen pen;

	private LinkedList<Integer> viewGestures = new LinkedList<Integer>();
	
	private LinkedList<String> contextMenuOptions = new LinkedList<String>();
	
	private boolean isStartPointSelected = false;
	private boolean isEndPointSelected = false;
	
	private ContextMenu contextMenu; 
	
	private HashMap<Integer, CurvePointItem> currentLineEdits = new HashMap<Integer, CurvePointItem>();
	private CurvePointItem cpiWithOpenedContextMenu = null;
	private CurvePointItem rootNode = null;

	
	private CurvePointItem startPoint;
	private CurvePointItem endPoint;
	
	public CurveItem(QPointF startPoint, QPointF endPoint) {

		viewGestures.add(GestureType.TOUCH_GESTURE.ordinal());
		viewGestures.add(GestureType.DBLCLK_GESTURE.ordinal());
		
		contextMenuOptions.add("Move Node");
		contextMenuOptions.add("Move Predecessors");
		contextMenuOptions.add("Move Successors");
		

		contextMenu = new ContextMenu(contextMenuOptions, "Node Options", "ok");
		contextMenu.setParentItem(this);
		contextMenu.setVisible(false);
		contextMenu.selectionFinished.connect(this, "contextMenuSelectionFinished(String)");
		
		pen = new QPen(QColor.red);
		pen.setWidth(20);
		pen.setJoinStyle(PenJoinStyle.RoundJoin);
		pen.setCapStyle(PenCapStyle.RoundCap);

		CurvePoint sp = this.addPointAfter(null, startPoint, false);
		this.startPoint = this.addCurvePointItem(sp);
		CurvePoint ep = this.addPointAfter(startPoint, endPoint, false);
		this.endPoint = this.addCurvePointItem(ep);
		this.updatePath();
	}

	private void contextMenuSelectionFinished(String selection) {
		System.out.println("selection "+selection);
		if(!selection.equals(contextMenuOptions.get(0))) {
			if(selection.equals(contextMenuOptions.get(1))) {
				cpiWithOpenedContextMenu.setMode(1, true);
				
				//predecessors
				for(CurvePoint cp: curvePoints) {
					if(cp.curvePointItem != null) {
						if(cp.curvePointItem != cpiWithOpenedContextMenu) {
							cp.curvePointItem.setMode(1, false);
						} else {
							break;
						}
					}
				}
			} else if(selection.equals(contextMenuOptions.get(2))) {
				cpiWithOpenedContextMenu.setMode(2, true);
				
				boolean found = false;
				
				//successors
				for(CurvePoint cp: curvePoints) {
					if(cp.curvePointItem != null) {
						if(found) {
							cp.curvePointItem.setMode(2, false);
						} else if(cp.curvePointItem == cpiWithOpenedContextMenu) {
							found = true;
						}
					}
				}

			}
		} else {
			for(CurvePoint cp: curvePoints) {
				if(cp.curvePointItem != null) {
					cp.curvePointItem.setMode(0, false);
				}
			}
		}
		
		cpiWithOpenedContextMenu = null;
		contextMenu.setVisible(false);
	}
	
	private class CurvePoint {
		boolean isControlPoint = false;
		QPointF point = null;
		CurvePointItem curvePointItem = null;
	}

	private class CurvePointItem extends TouchableGraphicsItem {

		private QRectF normalSize = new QRectF(-20,-20,40,40);
		private QRectF specialModeSize = normalSize.adjusted(-10, -10, 10, 10);
		private QRectF size = normalSize;
		private int mode = 0;
		private boolean specialMode = false;


		QColor penColor =  new QColor(50, 100, 120, 200);
		QPen pen = new QPen(QColor.lightGray, 0, Qt.PenStyle.SolidLine);
		QColor normalColor = new QColor(200, 200, 210, 120);
		QColor greenColor = new QColor(200, 255, 210, 120);
		QColor redColor = new QColor(255, 200, 210, 120);
		QColor blueColor = new QColor(200, 200, 255, 120);
		
		QColor brushColor = normalColor;
		
		public CurvePoint curvePoint = null;
		@Override
		public QRectF boundingRect() {
			return size;
		}

		@Override
		public void paint(QPainter painter, QStyleOptionGraphicsItem option,
				QWidget widget) {
			painter.setPen(penColor);
			painter.setBrush(brushColor);
			painter.drawEllipse(size);
			
			if(specialMode) {
				painter.setBrush(normalColor);
				painter.drawEllipse(normalSize);
			}
		}
		
		public void setMode(int mode, boolean isRoot) {
			switch(mode) {
			case 0:
				brushColor = normalColor;
				break;
			case 1:
				brushColor = greenColor;
				break;
			case 2:
				brushColor = redColor;
				break;
			case 3:
				brushColor = blueColor;
				break;
			}
			
			if(isRoot && isRoot != specialMode) {
				this.prepareGeometryChange();
				this.size = specialModeSize;
				specialMode = true;
				this.update();
			} else if(isRoot != specialMode){
				this.prepareGeometryChange();
				this.size = normalSize;
				specialMode = false;
				this.update();				
			}
			
			this.update();
			
			this.mode = mode;
		}

		public boolean isRootNode() {
			return this.specialMode;
		}
		
		@Override
		public QSizeF getPreferedSize() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setSize(QSizeF size) {
			// TODO Auto-generated method stub	
		}

	}


	LinkedList<CurvePoint> curvePoints = new LinkedList<CurvePoint>();

	@Override
	public QRectF boundingRect() {
		return size;
	}

	public QPainterPath shape() {
		return shape;
	}

	private void updateGeometry() {
		shape = Util.createShapeFromPath(curve, pen);
		size = shape.controlPointRect();
	}

	private CurvePointItem addCurvePointItem(CurvePoint cp) {
		CurvePointItem cpi = new CurvePointItem();
		
		cp.curvePointItem = cpi;
		cpi.curvePoint = cp;
		
		cpi.setPos(cp.point);
		cpi.setZValue(5.0);
		cpi.setParent(this);
		cpi.setParentItem(this);
		return cpi;
	}

	private CurvePoint addPointAfter(QPointF after, QPointF point, boolean isControlPoint) {	
		int i = 0;
		if(after != null) {
			for(; i < curvePoints.size(); i++) {
				if(curvePoints.get(i).point == after) {
					i++;
					break;
				}
			}
		}
		CurvePoint cp = new CurvePoint();
		cp.isControlPoint = isControlPoint;
		cp.point = point;
		curvePoints.add(i, cp);

		return cp;

	}

	private void updatePath() {
		curve = new QPainterPath();
		if(!curvePoints.isEmpty()) {
			curve.moveTo(curvePoints.get(0).point);

			QPointF controlPoint1 = null;
			QPointF controlPoint2 = null;
			CurvePoint cp;

			for(int i = 1; i < curvePoints.size(); i++) {
				cp = curvePoints.get(i);

				if(cp.isControlPoint) {
					if(controlPoint1 == null) {
						controlPoint1 = cp.point;
					} else {
						controlPoint2 = cp.point;
					}
				} else {
					if(controlPoint1 != null && controlPoint2 != null) {
						curve.cubicTo(controlPoint1, controlPoint2, cp.point);

						controlPoint1 = null;
						controlPoint2 = null;
					} else {
						curve.lineTo(cp.point);
					}

				}
			}
		}		
		prepareGeometryChange();
		updateGeometry();
		update();
	}

	@Override
	public void paint(QPainter painter, QStyleOptionGraphicsItem option,
			QWidget widget) {
		painter.setPen(pen);
		painter.drawPath(curve);
	}


	@Override
	public QSizeF getPreferedSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSize(QSizeF size) {
		// TODO Auto-generated method stub

	}


	public List<Integer> getAllowedGestures() {
		return viewGestures;
	}

	private void setCurvePointItemPositionNotUpdatingPath(CurvePointItem cpi, QPointF coordinate) {
		int i = curvePoints.indexOf(cpi.curvePoint);
		
		CurvePoint predecessorItem = null;
		CurvePoint successorItem = null;
		
		if(i > 0) {
			 predecessorItem = curvePoints.get(i-1);
		}
		
		if(i+1 < curvePoints.size()) {
			successorItem = curvePoints.get(i+1);	
		}
		
		
		if(predecessorItem != null && successorItem != null) {
			QPointF predecessor = predecessorItem.point;
			QPointF successor = successorItem.point;
			if(coordinate.x() > predecessor.x() && coordinate.x() < successor.x()) {
				cpi.setPos(coordinate);
				cpi.curvePoint.point = coordinate;
			} else {
				QPointF pos = cpi.pos();
				QPointF newPos = new QPointF(pos.x(), coordinate.y());
				cpi.setPos(newPos);
				cpi.curvePoint.point = newPos;
			}
		} else if(predecessorItem == null) {
			QPointF successor = successorItem.point;
			if(coordinate.x() < successor.x()) {
				cpi.setPos(coordinate);
				cpi.curvePoint.point = coordinate;
			} else {
				QPointF pos = cpi.pos();
				QPointF newPos = new QPointF(pos.x(), coordinate.y());
				cpi.setPos(newPos);
				cpi.curvePoint.point = newPos;
			}
		} else {
			QPointF predecessor = predecessorItem.point;			
			if(coordinate.x() > predecessor.x()) {
				cpi.setPos(coordinate);
				cpi.curvePoint.point = coordinate;
			} else {
				QPointF pos = cpi.pos();
				QPointF newPos = new QPointF(pos.x(), coordinate.y());
				cpi.setPos(newPos);
				cpi.curvePoint.point = newPos;
			}
		}
	}
	
	private void setCurvePointItemPosition(CurvePointItem cpi, QPointF coordinate) {
		this.setCurvePointItemPositionNotUpdatingPath(cpi, coordinate);
		this.updatePath();
	}

	public boolean processEvent(Event event) {
		if(event instanceof TouchEvent) {
			TouchEvent e = (TouchEvent) event;
			
			QPointF coordinate = mapFromScene(lazerdoom.View.getInstance().convertScreenPos(((TouchEvent) event).getX(),((TouchEvent) event).getY()));
			
			//CurvePoint
			if(e.getSource() != null && e.getSource() instanceof CurvePointItem) {
				CurvePointItem cpi = (CurvePointItem)e.getSource();
				
				//MOVE
				if(!cpi.isRootNode()) {
					if(cpi.curvePoint != null) {
						this.setCurvePointItemPosition(cpi, coordinate);
					}
				} else {
					
					double xDiff = coordinate.x()-cpi.pos().x();
					double yDiff = coordinate.y()-cpi.pos().y();
					System.out.println("diff "+xDiff+" "+yDiff);
					int i = 0;
					for(CurvePoint cp:  curvePoints) {
						cpi = cp.curvePointItem; 
						if(cpi.curvePoint != null && (cpi.mode == 1 || cpi.mode == 2)) {
							QPointF cpiPos = cpi.pos();
							double x = cpiPos.x();
							double y = cpiPos.y();
							
							QPointF newCoordinate = new QPointF(x+xDiff, y+yDiff);
							
							System.out.println("Move item "+i+" from "+cpi.pos()+" to "+newCoordinate);
							this.setCurvePointItemPositionNotUpdatingPath(cpi, newCoordinate);
							//cpi.setPos(newCoordinate);
							//cpi.curvePoint.point = newCoordinate;
						}
						i++;
					}
					this.updatePath();
				}
			} 
			// line
			else if (e.getSource() == null) {
				if(e.getState() == TouchState.BIRTH && this.shape().contains(coordinate)) {
					// add line segment
					
					int i = 0;
					for(CurvePoint cp: curvePoints) {
						if(!cp.isControlPoint) {
							if(cp.point.x() > coordinate.x()) {
								break;
							}
						}
						i++;
					}
					
					CurvePoint cp = new CurvePoint();
					cp.isControlPoint = false;
					cp.point = coordinate;
					curvePoints.add(i, cp);
					
					CurvePointItem newCpi = this.addCurvePointItem(cp);
					currentLineEdits.put(e.getTouchID(), newCpi);
					
					this.updatePath();
				} else if(e.getState() == TouchState.MOVE) {
					CurvePointItem cpi;
					if((cpi = currentLineEdits.get(e.getTouchID())) != null) {
						this.setCurvePointItemPosition(cpi, coordinate);	
					}
				} else if(e.getState() == TouchState.DEATH) {
					currentLineEdits.remove(e.getTouchID());
				} 
			}
		} else if(event instanceof DblClkEvent) {
			//CurvePoint
			DblClkEvent e = (DblClkEvent) event;
			
			if(e.getSource() != null && e.getSource() instanceof CurvePointItem) {
				CurvePointItem cpi = (CurvePointItem)e.getSource();
			
				cpiWithOpenedContextMenu = cpi;
				
				contextMenu.setPos(cpi.pos() );
				contextMenu.setVisible(true);
				contextMenu.setZValue(100);
			} 
		}
		
		return true;
	}
}
