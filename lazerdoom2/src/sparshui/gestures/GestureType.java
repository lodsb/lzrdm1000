package sparshui.gestures;

/**
 * This class is a place for the enum GestureType to stand.
 * If you modify this class, you must also modify the corresponding
 * enum in GestureType.h in the C++ Gesture Server.  ALWAYS assign
 * this enum value to a 4-byte network-ordered integer before attempting
 * to transmit this value over the network.
 * 
 * @author Jay Roltgen
 *
 */
public enum GestureType {
	DRAG_GESTURE,
	MULTI_POINT_DRAG_GESTURE,
	ROTATE_GESTURE,
	SPIN_GESTURE,
	TOUCH_GESTURE,
	ZOOM_GESTURE,
	DBLCLK_GESTURE,
	FLICK_GESTURE, 
	GROUP_GESTURE, 
	DELETE_GESTURE,
	ZOOM_GESTURE2D,
	TAP_GESTURE
}
