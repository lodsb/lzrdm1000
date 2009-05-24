package sparshui.common.utils;


import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import sparshui.common.Location;


public class CoordinateConverter {

	Point screenPoint;
	Container container;
	Dimension screen;
	//int screen_width;
	//int screen_height;
	
	// 0 of the JFrame is 0 of the content pane ( - title bar)
	CoordinateConverter( JFrame frame)
	{
		container = frame.getContentPane();		
		//screen_width;
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		//screen_height = screen_height_p;
	}
	public Location frameToScreen(Location loc)
	{
		Point p = new Point((int)loc.getX(),(int)loc.getY());
		SwingUtilities.convertPointToScreen(p, container);
		return new Location(p.x,p.y);
	}
	public Location frameToInputDevice( Location loc)
	{
		// input device coords 0 - 1
		Location screenLoc = frameToScreen(loc);
		float newX = screenLoc.getX()/screen.width;
		float newY = screenLoc.getY()/screen.height;
		// loc / screen_height, screen_width
		
		return new Location(newX,newY);
		
	}
	public Location screenToFrame(Location loc)
	{
		Point p = new Point((int)loc.getX(), (int)loc.getY());
		SwingUtilities.convertPointFromScreen(p, container);
		return new Location(p.x,p.y);
	}
}
