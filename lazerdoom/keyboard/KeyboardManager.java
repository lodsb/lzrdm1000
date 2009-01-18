package keyboard;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QKeySequence;

public class KeyboardManager {
	// TODO: propper exceptions
	
	// unregister qobjects?
	
	// Maps for children
	private Map<QObject, List<QAction>> childrenToActions = new HashMap<QObject, List<QAction>>(); 
	private Map<QAction, List<QObject>> actionsToChildren = new HashMap<QAction, List<QObject>>();
	
	private static Map<String, QKeySequence> defaultBindings = new HashMap<String, QKeySequence>(); 
	static {
		defaultBindings.put("ZoomInH", new QKeySequence("Ctrl++"));
		defaultBindings.put("ZoomOutH", new QKeySequence("Ctrl+-"));
		defaultBindings.put("ZoomInV", new QKeySequence("Ctrl+Shift++"));
		defaultBindings.put("ZoomOutV", new QKeySequence("Ctrl+Shift+-"));
	};
	
	// Maps for active KeyBindings
	private Map<String, QKeySequence> activeBindings = new HashMap<String, QKeySequence>();
	private Map<QKeySequence, List<QAction>> keybindingsToActions = new HashMap<QKeySequence, List<QAction>>();
	
	private static KeyboardManager instance = null;
	
	public KeyboardManager() {
		// copy default bindings to active bindings
		for(Map.Entry<String, QKeySequence> entry: defaultBindings.entrySet()) {
			activeBindings.put(entry.getKey(), entry.getValue());
		}
	}
	
	public static KeyboardManager getInstance() {
		if(instance == null) {
			instance = new KeyboardManager();
		}
		
		return instance;
	}
	
	public QKeySequence getKeyBinding(String actionName) {
		return activeBindings.get(actionName);
	}
	
	public void changeOrAddKeyBinding(String binding, QKeySequence newKeySeq) {
		QKeySequence oldKeySeq;
		
		if((oldKeySeq = activeBindings.get(binding)) != null) {
			List<QAction> actionList;
			
			if((actionList = keybindingsToActions.get(oldKeySeq)) != null) {
				keybindingsToActions.remove(oldKeySeq);
				
				keybindingsToActions.put(newKeySeq, actionList);
				
				for(QAction action: actionList) {
					action.setShortcut(newKeySeq);
				}
			} 
		} else {
			activeBindings.put(binding, newKeySeq);
		}
	}
	
	private void addActionChildAndKeySeq(QAction action, QObject child, QKeySequence keySeq) {
		List<QAction> actionList;
		List<QObject> childrenList;
		
		if((actionList = childrenToActions.get(child)) != null) {
			actionList.add(action);
		} else {
			actionList = new LinkedList<QAction>();
			actionList.add(action);
			
			childrenToActions.put(child, actionList);
			
			// add keybinding
			keybindingsToActions.put(keySeq, actionList);
		}

		if((childrenList = actionsToChildren.get(action)) != null) {
			childrenList.add(action);
		} else {
			childrenList = new LinkedList<QObject>();
			childrenList.add(action);
			
			actionsToChildren.put(action, childrenList);
		}
		
	}
	
	public void registerKeyBinding(String actionName, QAction action, QObject child) throws Exception {
		QKeySequence keySequence;
		
		if((keySequence = activeBindings.get(actionName)) != null) {
			addActionChildAndKeySeq(action, child, keySequence);
			action.setShortcut(keySequence);
		} else {
			throw new Exception("Keybinding unknown.");
		}
	}
	
	public QAction registerKeyBinding(String actionName, QObject child) throws Exception {
		QAction action = new QAction(child);
		
		registerKeyBinding(actionName, action, child);
		
		return action;
	}
	
	public void unregisterChild(QObject child) {
		System.out.println("registerKeyBinding: not yet implemented");
	}
	
}
