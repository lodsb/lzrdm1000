package GUI.Editor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import lazerdoom.Core;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.gui.QGraphicsItemInterface;

import edu.uci.ics.jung.graph.util.Pair;

import sparshui.common.Event;
import sparshui.common.messages.events.DeleteEvent;
import sparshui.common.messages.events.DragEvent;
import sparshui.common.messages.events.TouchEvent;

import Control.Types.DoubleType;
import GUI.Editor.BaseSequencerItemEditor;
import GUI.Editor.Commands.DeleteEditorCursor;
import GUI.Editor.Commands.DeleteSequenceConnectionCommand;
import GUI.Editor.Commands.DeleteSequenceItemCommand;
import GUI.Editor.Commands.DeleteSequencePlayerCommand;
import GUI.Editor.Commands.DeleteSynthConnectionCommand;
import GUI.Editor.Commands.DeleteSynthItemCommand;
import GUI.Editor.Commands.SequenceEditor.CreateDoublePointSequenceItem;
import GUI.Editor.Commands.SequenceEditor.MoveDoublePointSequenceItem;
import GUI.Editor.Commands.SequenceEditor.RemoveDoublePointSequenceItem;
import GUI.Item.BaseSequencerItem;
import GUI.Item.EditorCursor;
import GUI.Item.SequenceConnection;
import GUI.Item.SequenceItem;
import GUI.Item.SequencePlayerItem;
import GUI.Item.SynthConnection;
import GUI.Item.SynthesizerItem;
import GUI.Item.Editor.SequenceDataEditor.TouchableDoubleTypeSequenceDataItem;
import GUI.Multitouch.TouchItemInterface;
import GUI.Multitouch.TouchableGraphicsItem;
import GUI.Scene.Editor.BaseSequenceScene;
import GUI.Scene.Editor.EditorScene;
import GUI.Scene.Editor.EventPointsDoubleSequenceScene;
import GUI.Scene.Editor.PauseScene;
import GUI.Scene.Editor.SequenceInitScene;
import GUI.Scene.Editor.SequencePlayerScene;
import GUI.View.SequencerView;
import SceneItems.Util;
import Sequencer.BaseSequence;
import Sequencer.EventPointsSequence;
import Sequencer.EventSequenceInterface;
import Sequencer.Pause;
import Sequencer.SequenceEvent;
import Sequencer.SequenceEventListenerInterface;
import Sequencer.SequenceEvent.SequenceEventType;

public class SequenceEditor extends BaseSequencerItemEditor {
	
	private abstract class SequenceEditMode {
		private SequenceEditor editor;
		public SequenceEditMode(SequenceEditor editor) {
			this.editor = editor;
		}
		
		public SequenceEditor getEditor() {
			return this.editor;
		}
		
		public abstract EditorScene getScene();
		public abstract boolean allowViewportChange();
		public abstract boolean handleTouchEvent(TouchEvent e);
		public abstract boolean handleDragEvent(DragEvent e);
		public abstract boolean handleDeleteEvent(DeleteEvent e);
	}
	
	private class SequenceInitMode extends SequenceEditMode {
		private SequenceInitScene scene;
		private int currentInitSequenceTypeIndex = 0;
	
		private String[] sequenceTypeArray = new String[] {
				"Pause",
				"EventPoints",
		};
		
		public SequenceInitMode(SequenceEditor editor) {
			super(editor);
			this.scene = new SequenceInitScene(editor);
			this.scene.nextPressed.connect(this, "nextPressed()");
			this.scene.prevPressed.connect(this, "prevPressed()");
			this.scene.createSequencePressed.connect(this, "createSequencePressed()");
		}
		
		private void setSequenceTypeName() {
			this.scene.setCurrentSequenceType(sequenceTypeArray[currentInitSequenceTypeIndex]);
		}
		
		private void createSequencePressed() {
			System.out.println("create sequence pressed!");
			
			switch(currentInitSequenceTypeIndex) {
				case 0: 
					System.out.println("Pause");
					sequence = Core.getInstance().getSequenceController().createPauseSequence(Core.getInstance().oneBarInPPQ());
					setItemType(sequence);
					setCurrentMode(new PauseMode(this.getEditor(), sequence));
				break;
				case 1:
					System.out.println("EventPoints");
					sequence = Core.getInstance().getSequenceController().createDoubleTypeEventPointsSequence();
					((EventPointsSequence)sequence).setLength(Core.getInstance().oneBarInPPQ());
					setItemType(sequence);
					setCurrentMode(new EventPointsMode(this.getEditor(), sequence));

				break;
			}
		}
		
		private void nextPressed() {
			if(currentInitSequenceTypeIndex < sequenceTypeArray.length-1) {
				currentInitSequenceTypeIndex++;
				this.setSequenceTypeName();
				
				if(currentInitSequenceTypeIndex == sequenceTypeArray.length-1) {
					this.scene.enableNextButton(false);
				} 
			}
			this.scene.enablePrevButton(true);
		}
		
		private void prevPressed() {
			if(currentInitSequenceTypeIndex > 0) {
				currentInitSequenceTypeIndex--;
				
				this.setSequenceTypeName();
				
				if(currentInitSequenceTypeIndex == 0) {
					this.scene.enablePrevButton(false);
				}
			}
			this.scene.enableNextButton(true);
		}
		
		@Override
		public EditorScene getScene() {
			return this.scene;
		}

		@Override
		public boolean handleDeleteEvent(DeleteEvent e) {
			return false;
		}

		@Override
		public boolean handleDragEvent(DragEvent e) {
			return false;
		}

		@Override
		public boolean handleTouchEvent(TouchEvent e) {
			QGraphicsItemInterface item;
			if((item = this.scene.itemAt(e.getSceneLocation())) instanceof TouchableGraphicsItem) {
				((TouchableGraphicsItem) item).processEvent(e);
			}
			return true;
		}

		@Override
		public boolean allowViewportChange() {
			return false;
		}		
	}
	
	private abstract class SequenceEditModeForSequences extends SequenceEditMode implements SequenceEventListenerInterface {
		
		// ugly but needed to filter events for the handling touchpoints and gestures
		QTimer clearTableTimer = new QTimer();
		HashMap<Integer, Integer> filteredEvents = new HashMap<Integer, Integer>();
		public void focusCurrentEvent(Event event) {
			clearTableTimer.start(2000);
			filteredEvents.put(event.getTouchID(), event.getEventType());
		}
		
		protected boolean filterEvent(Event event) {
			Integer type = null;
			
			clearTableTimer.start(2000);
			if((type = filteredEvents.get(event.getTouchID())) != null) {
				if(type != event.getEventType()) {
					return true;
				}
			}	
			
			return false;
		}
		
		private void clearFEMap() {
			filteredEvents.clear();
		}
		
		private BaseSequence sequence;
		public SequenceEditModeForSequences(SequenceEditor editor, BaseSequence baseSequence) {
			super(editor);
			this.sequence = baseSequence;
			
			clearTableTimer.timeout.connect(this, "clearFEMap()");
			
		}
		
		public BaseSequence getBaseSequence() {
			return this.sequence;
		}
	}
	
	private class PauseMode extends SequenceEditModeForSequences {

		BaseSequenceScene scene;
		public PauseMode(SequenceEditor editor, BaseSequence baseSequence) {
			super(editor, baseSequence);
			
			scene = new BaseSequenceScene();
			scene.lengthMoved.connect(this, "lengthMoved(Long, Boolean)");
			scene.setLengthCursor(baseSequence.size());
		}
		
		public PauseMode(SequenceEditor editor, BaseSequence baseSequence, BaseSequenceScene scene) {
			super(editor, baseSequence);
			
			this.scene = scene;
			scene.lengthMoved.connect(this, "lengthMoved(Long, Boolean)");
			scene.setLengthCursor(baseSequence.size());
		}
		
		
		private void lengthMoved(Long tick, Boolean successful) {
			if(tick > 0) {
				this.scene.setLengthCursor(tick);
				if(successful) {
					((Pause)this.getBaseSequence()).setPauseTicks(tick);
				}
			}
		}
		
		@Override
		public boolean allowViewportChange() {
			return true;
		}

		@Override
		public EditorScene getScene() {
			return scene;
		}

		private QRectF crossRect = new QRectF(-10, -10, 20,20);
		
		@Override
		public boolean handleDeleteEvent(DeleteEvent event) {
			return false;
		}
		
		private HashMap<Integer, TouchableGraphicsItem> dragStartPoints = new HashMap<Integer, TouchableGraphicsItem>();
		@Override
		public boolean handleDragEvent(DragEvent e) {
			TouchableGraphicsItem target;
			if(dragStartPoints.containsKey(e.getTouchID())) {
				if((target = dragStartPoints.get(e.getTouchID())) != null) {
					if(!this.filterEvent(e)) {
						target.processEvent(e);
					}
				}
			} else {
				QGraphicsItemInterface item = null;
				if((item = this.scene.itemAt(e.getSceneLocation())) instanceof TouchableGraphicsItem) {
					
					this.focusCurrentEvent(e);
					
					dragStartPoints.put(e.getTouchID(), (TouchableGraphicsItem) item);
				} else {
					dragStartPoints.put(e.getTouchID(), null);
				}
				
				System.out.println(item);
			}
			if(!e.isOngoing()) {
				dragStartPoints.remove(e.getTouchID());
			}
			
			return true;
		}

		@Override
		public boolean handleTouchEvent(TouchEvent e) {
			return false;
		}

		@Override
		public void dispatchSequenceEvent(SequenceEvent se) {
			if(se.getSequenceEventType() == SequenceEventType.EVALUATED_LOW_FREQ) {
				long tick = (Long )se.getArgument();
				this.scene.setPlayCursor(tick);
			}
		}
		
	}
	
	private class EventPointsMode extends PauseMode {
		
		public EventPointsMode(SequenceEditor editor, BaseSequence baseSequence) {
			super(editor, baseSequence, new EventPointsDoubleSequenceScene());
			((BaseSequenceScene)this.getScene()).startMoved.connect(this, "startMoved(Long, Boolean)");
			((BaseSequenceScene)this.getScene()).setStartOffsetCursor(((EventPointsSequence)baseSequence).getStartOffset());
		}
		
		private void lengthMoved(Long tick, Boolean successful) {
			if(((EventPointsSequence)this.getBaseSequence()).getStartOffset() < tick && tick > 0) {
				((BaseSequenceScene)this.getScene()).setLengthCursor(tick);
				if(successful) {
					((EventPointsSequence)this.getBaseSequence()).setLength(tick);
				}
			}
		}
		
		private void eventPointDragged(TouchableDoubleTypeSequenceDataItem item, QPointF position, Boolean successful) {
			if(!successful) {
				item.setPosition(position);
			} else {
				executeCommand(new MoveDoublePointSequenceItem<DoubleType>((EventPointsSequence<DoubleType>)this.getBaseSequence(), item, position));
			}
		}
		
		private void startMoved(Long tick, Boolean successful) {
			if(((EventPointsSequence)this.getBaseSequence()).getLength() > tick) {
				if(tick < 0) {
					tick = (long)0;
				}
				
				((BaseSequenceScene)this.getScene()).setStartOffsetCursor(tick);
				if(successful) {
					((EventPointsSequence)this.getBaseSequence()).setStartOffset(tick);
				}
			}
		}
		
		@Override
		public boolean handleTouchEvent(TouchEvent e) {
			QPointF pos = e.getSceneLocation();
			
			if(!this.filterEvent(e) && !e.isFocused() && pos.x() > 0 && !(this.getScene().itemAt(pos) instanceof TouchableGraphicsItem) && !e.isOngoing()) {
				System.out.println("touched "+e.getTouchID());
				executeCommand(new CreateDoublePointSequenceItem<DoubleType>((EventPointsSequence)this.getBaseSequence(), pos,this.getEditor(), this.getEditor().getScene(), this, "eventPointDragged(TouchableDoubleTypeSequenceDataItem, QPointF, Boolean)"));				
			}
			return true;
		}
		
		QRectF crossRect = new QRectF(0,0,20,20);
		@Override
		public boolean handleDeleteEvent(DeleteEvent event) {
			System.out.println("DELETE");
			if(!filterEvent(event)) {
				if(event.isSuccessful()) {
					this.focusCurrentEvent(event);
					System.out.println("SUCCessFuL"+event.getTouchID()+" "+event.isFocused());
					QPointF crossPoint = event.getSceneCrossPoint();

					System.out.println(crossPoint+" -- "+event.getSceneLocation());
					crossRect.setX(crossPoint.x()-10.0);
					crossRect.setY(crossPoint.y()-10.0);
					crossRect.setWidth(20);
					crossRect.setHeight(20);
					List<QGraphicsItemInterface> items = this.getEditor().getScene().items(crossRect);
					System.out.println(items);
					for(QGraphicsItemInterface item: items) {
						if(item instanceof TouchableDoubleTypeSequenceDataItem) {
							executeCommand(new RemoveDoublePointSequenceItem<DoubleType>((EventPointsSequence<DoubleType>) this.getBaseSequence(), (TouchableDoubleTypeSequenceDataItem) item, this.getEditor().getScene()));
						}
					}
				}	
				return true;
			} else {
				return false;
			}
		}
		
	}
	
	
	private int id = Util.getGroupID();
	private SequenceEditMode currentMode;
	
	private LinkedList<Integer> allowedGestures = new LinkedList<Integer>();
	
	
	public SequenceEditor() {
		super();
		
		allowedGestures.add(sparshui.gestures.GestureType.DRAG_GESTURE.ordinal());
		allowedGestures.add(sparshui.gestures.GestureType.DELETE_GESTURE.ordinal());
		allowedGestures.add(sparshui.gestures.GestureType.TOUCH_GESTURE.ordinal());
		
		this.setCurrentMode(new SequenceInitMode(this));
	
	}
	
	private void setCurrentMode(SequenceEditMode mode) {
		this.setScene(mode.getScene());
		this.sceneChanged.emit();
		
		if(mode instanceof SequenceEditModeForSequences) {
			Core.getInstance().getSequenceController().registerSequenceInterfaceEventListener(sequence, (SequenceEditModeForSequences) mode);
		}
		
		this.currentMode = mode;
	}
	
	private BaseSequence sequence;

	
	private void setItemType(BaseSequence sequence) {
		if(this.sequenceItem != null) {
			this.sequenceItem.setContentObject(sequence);
		}
	}

	@Override
	public boolean allowViewpointChange() {
		return currentMode.allowViewportChange();
	}

	@Override
	public List<Integer> getAllowedGestures() {
		return this.allowedGestures;
	}

	@Override
	public Integer getGroupID() {
		return this.id;
	}

	SequenceItem sequenceItem = null;
	@Override
	public void setItem(BaseSequencerItem item) {
		if(item instanceof SequenceItem) {
			this.sequenceItem = (SequenceItem) item;
		}

	}
	
	@Override
	public void handleDeleteEvent(DeleteEvent e) {
		if(currentMode.handleDeleteEvent(e)) {
			super.handleDeleteEvent(e);
		}
	}
	
	@Override
	public void handleTouchEvent(TouchEvent e) {
		currentMode.handleTouchEvent(e);
	}
	
	
	public void handleDragEvent(DragEvent e) {
		currentMode.handleDragEvent(e);
	}
}
