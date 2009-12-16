package sparshui.server;

import sparshui.gestures.DeleteGesture;
import sparshui.gestures.Flick;
import sparshui.gestures.Gesture;
import sparshui.gestures.GestureType;
import sparshui.gestures.GroupGesture;
import sparshui.gestures.MultiPointDragGesture;
import sparshui.gestures.RotateGesture;
import sparshui.gestures.SinglePointDragGesture;
//import sparshui.gestures.SpinGesture;
import sparshui.gestures.TapGesture;
import sparshui.gestures.TouchGesture;
import sparshui.gestures.ZoomGesture;
import sparshui.gestures.DblClkGesture;
import sparshui.gestures.ZoomGesture2D;

public class GestureFactory {

	private static GestureFactory _inst;
	
	private GestureFactory() {}
	
	/**
	 * Return the instance of the GestureFactory.  The GestureFactory
	 * is a singleton.
	 * @return
	 * 		The single instance of the gesture factory.
	 */
	public static synchronized GestureFactory getInstance() {
		if (_inst == null) {
			_inst = new GestureFactory();
		} 
		return _inst;
	}
	
	/**
	 * Return a Gesture specified by gestureID.  The gestureID
	 * is a string that is the class name of the gesture.
	 * 
	 * TODO: In the future we will want to change this implementation
	 * to allow for dynamically loaded classes using Java reflection
	 * 
	 * @return A new Gesture of type gestureID
	 */
	public Gesture createGesture(int gestureID) {

		if (gestureID == GestureType.DRAG_GESTURE.ordinal())
			return new SinglePointDragGesture();
		if (gestureID == GestureType.MULTI_POINT_DRAG_GESTURE.ordinal())
			return new MultiPointDragGesture();
		if (gestureID == GestureType.ROTATE_GESTURE.ordinal())
			return new RotateGesture();
		//if (gestureID == GestureType.SPIN_GESTURE.ordinal())
			//return new SpinGesture();
		if (gestureID == GestureType.TOUCH_GESTURE.ordinal())
			return new TouchGesture();
		if (gestureID == GestureType.ZOOM_GESTURE.ordinal())
			return new ZoomGesture();
		if(gestureID == GestureType.DBLCLK_GESTURE.ordinal())
			return new DblClkGesture();
		if(gestureID == GestureType.FLICK_GESTURE.ordinal())
			return new Flick();
		if(gestureID == GestureType.GROUP_GESTURE.ordinal()) 
			return new GroupGesture();
		if(gestureID == GestureType.DELETE_GESTURE.ordinal()) 
			return new DeleteGesture();
		if(gestureID == GestureType.ZOOM_GESTURE2D.ordinal()) 
			return new ZoomGesture2D();
		if(gestureID == GestureType.TAP_GESTURE.ordinal()) 
			return new TapGesture();
		
		System.err.println("[GestureFactory] Gesture not recognized: " + gestureID);
		return null;
	}

}
