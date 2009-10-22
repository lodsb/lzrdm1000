package lazerdoom;

import com.trolltech.qt.gui.QPainter;

public class LazerdoomConfiguration {
	public static int gestureRecognitionPathUpdateResolution = 2;
	public static double minimumDeleteGestureLength = 0.40;
	
	public static String sessionFileName = "session";
	public static String sessionPath = "/home/lodsb/lzrdm/";
	
	public static QPainter.RenderHint viewRenderHint = QPainter.RenderHint.Antialiasing;
	public static boolean enableOpenGl = true;
	
}
