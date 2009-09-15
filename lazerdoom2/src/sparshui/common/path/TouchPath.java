package sparshui.common.path;

import java.util.LinkedList;

import sparshui.common.Location;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.gui.QPainterPath;

public class TouchPath {
	private LinkedList<QPainterPath> pathSegments = new LinkedList<QPainterPath>();
	
	private QPointF lastLocation = null;
	
	public TouchPath(Location start) {
		this.lastLocation = new QPointF(start.getX(), start.getY());
	}
	
	public void addPoint(Location location) {
		QPointF point = new QPointF(location.getX(), location.getY());
		QPainterPath path = new QPainterPath();
		path.moveTo(this.lastLocation);
		path.lineTo(point);
		
		pathSegments.add(path);
		this.lastLocation = point;
	}
	
	public QPointF getIntersectionPoint() {
		QPointF intersectionPoint = null;
		
		LinkedList<QPainterPath> pathSegmentsCopy = (LinkedList<QPainterPath>) pathSegments.clone();	
		
		for(QPainterPath path1: pathSegments) {
			pathSegmentsCopy.removeFirst();
			for(QPainterPath path2: pathSegmentsCopy) {
				if(path1.intersects(path2)) {
					QPainterPath intersection = path1.intersected(path2);
					if(intersection.elementCount() == 0) {
						QPointF p1 = (path1.elementAt(0).toPoint());
						QPointF p2 = (path1.elementAt(1).toPoint());
						QPointF p3 = (path2.elementAt(0).toPoint());
						QPointF p4 = (path2.elementAt(1).toPoint());
						
						double x1 = p1.x();
						double x2 = p2.x();
						double x3 = p3.x();
						double x4 = p4.x();
						
						double y1 = p1.y();
						double y2 = p2.y();
						double y3 = p3.y();
						double y4 = p4.y();
						
						double ua = (((x4-x3)*(y1-y3))-((y4-y3)*(x1-x3)))/(((y4-y3)*(x2-x1))-((x4-x3)*(y2-y1)));
						
						double xi = x1 + (ua*(x2-x1));
						double yi = y1 + (ua*(y2-y1));
						
						QPointF pi = new QPointF(xi, yi);
						
						intersectionPoint = pi;
						
						//System.out.println(" elemcount "+intersection.elementCount()+" "+p1+" "+p2+" "+path1.elementCount()+" "+path2.elementCount()+" pi "+pi);
						
					}
					//intersectionPoint = intersection.elementAt(0).toPoint();
				}
			}
		}
		
		return intersectionPoint;
	}

}
