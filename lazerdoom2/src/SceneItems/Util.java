package SceneItems;

import com.trolltech.qt.gui.QPainterPath;
import com.trolltech.qt.gui.QPainterPathStroker;
import com.trolltech.qt.gui.QPen;

public class Util {
	private static int currentID = 2;
	
	public static int getGroupID() {
		return currentID++;
	}
	
	public static QPainterPath createShapeFromPath(QPainterPath path, QPen pen) {
	    double penWidthZero = 0.00000001;

	    QPainterPathStroker ps = new QPainterPathStroker();
	    if(pen == null) {
	    	pen = new QPen();
	    	ps.setCapStyle(pen.capStyle());
	    	ps.setWidth(penWidthZero);
	    } else {
	    	if (pen.widthF() <= 0.0) {
	    		ps.setWidth(penWidthZero);
	    	} else {
	    		ps.setWidth(pen.widthF());
	    	} 
	    }
	    ps.setJoinStyle(pen.joinStyle());
	    ps.setMiterLimit(pen.miterLimit());
	    QPainterPath p = ps.createStroke(path);
	    p.addPath(path);
	    return p;
	}
	
}
