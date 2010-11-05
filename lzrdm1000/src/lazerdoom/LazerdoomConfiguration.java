package lazerdoom;

import com.trolltech.qt.gui.QPainter;

public class LazerdoomConfiguration {
	// constants for gesture-"callibration"
	public int gestureRecognitionPathUpdateResolution = 2;
	public double minimumDeleteGestureLength = 0.40;
	public boolean garbageCollectZombieTuioBlobs = false;
	public long maxUpdateIntervalUntilGarbageCollect = 5000;
	
	public double hStretch = 1.0;
	public double vStretch = 1.0;
	
	public int maxPolyphony = 16;
	
	public int frameRate = 25;
	
	// std config-filename
	public String configFileName = "lzrdmConfig.xml";
	
	// change to your favorite filename
	public String sessionFileName = "session";
	
	// change to your session-directory
	public String baseLzrdmPath = System.getProperty("user.home")+"/lzrdm/";
	
	// change to HighQualityAntialiasing if you want to: fps-drop for beauty.
	public QPainter.RenderHint viewRenderHint = QPainter.RenderHint.Antialiasing;
	
	// disabling it equals shooting your foot.
	// qt's rasterengine is high-quality but the current updatemode for graphicsview+some items
	// makes the fps drop to ~3
	public boolean enableOpenGl = true;
	
	// spam fo' real!
	// SPAM SPAM SPAM SPAM SPAM SPam ssssPAAAM SPAM SSssssssPPPPPPPPAM SPAAAAAAAAAM SPAAAM!
	// (c) 1969 Monty Python 
	public boolean enableDebugOutput = false;
	
	public boolean showSupercolliderConsole = true;
	
	private static LazerdoomConfiguration instance = null;
	
	public static LazerdoomConfiguration getInstance() {
		if(instance == null) {
			instance = new LazerdoomConfiguration();
		}
		return instance;
	}
	
	public void _setInstance(LazerdoomConfiguration lzrdmcfg) {
		LazerdoomConfiguration.instance = lzrdmcfg; 
	}
	
}
