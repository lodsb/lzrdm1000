package sparshui.common.messages.events;

/**
 * This is an enumeration of all event types currently available.
 * If you add a new event, you must modify this enumeration as well
 * as the enumeration in the C++ version, should you want to add
 * the event there as well.
 * @author root
 *
 */
public enum EventType {
	DRAG_EVENT,
	ROTATE_EVENT,
	SPIN_EVENT,
	TOUCH_EVENT,
	ZOOM_EVENT,
	DBLCLK_EVENT,
	FLICK_EVENT,
	GROUP_EVENT
}
