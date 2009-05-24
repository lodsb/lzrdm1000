package SceneItems;

import java.util.List;

import sparshui.client.Client;
import sparshui.common.Event;
import sparshui.common.Location;

public interface TouchItemInterface {

	public List<Integer> getAllowedGestures();

	public int getGroupID();

	public boolean processEvent(Event event);

}
