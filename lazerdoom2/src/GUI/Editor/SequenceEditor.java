package GUI.Editor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import java.util.Iterator;

import lazerdoom.Core;
import lazerdoom.LzrDmObjectInterface;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QGraphicsPathItem;
import com.trolltech.qt.gui.QGraphicsTextItem;
import com.trolltech.qt.gui.QPainterPath;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QGraphicsItem.GraphicsItemFlag;

import edu.uci.ics.jung.graph.util.Pair;

import sparshui.common.Event;
import sparshui.common.messages.events.DeleteEvent;
import sparshui.common.messages.events.DragEvent;
import sparshui.common.messages.events.ExtendedGestureEvent;
import sparshui.common.messages.events.GroupEvent;
import sparshui.common.messages.events.TapEvent;
import sparshui.common.messages.events.TouchEvent;

import Control.Types.DoubleType;
import Control.Types.NoteType;
import GUI.Editor.BaseSequencerItemEditor;
import GUI.Editor.Commands.DeleteEditorCursor;
import GUI.Editor.Commands.DeleteSequenceConnectionCommand;
import GUI.Editor.Commands.DeleteSequenceItemCommand;
import GUI.Editor.Commands.DeleteSequencePlayerCommand;
import GUI.Editor.Commands.DeleteSynthConnectionCommand;
import GUI.Editor.Commands.DeleteSynthItemCommand;
import GUI.Editor.Commands.SequenceEditor.CreateBaseSequence;
import GUI.Editor.Commands.SequenceEditor.CreateDoublePointSequenceDataItem;
import GUI.Editor.Commands.SequenceEditor.CreateNoteTypeSequenceDataItem;
import GUI.Editor.Commands.SequenceEditor.MoveNoteTypeSequenceDataItem;
import GUI.Editor.Commands.SequenceEditor.MoveSequenceDataItem;
import GUI.Editor.Commands.SequenceEditor.RemoveNoteTypeSequenceDataItem;
import GUI.Editor.Commands.SequenceEditor.RemoveSequenceDataItem;
import GUI.Item.BaseSequencerItem;
import GUI.Item.EditorCursor;
import GUI.Item.SequenceConnection;
import GUI.Item.SequenceItem;
import GUI.Item.SequencePlayerItem;
import GUI.Item.SynthConnection;
import GUI.Item.SynthesizerItem;
import GUI.Item.Editor.SequenceDataEditor.TouchableDoubleTypeSequenceDataItem;
import GUI.Item.Editor.SequenceDataEditor.TouchableNoteTypeSequenceDataItem;
import GUI.Item.Editor.SequenceDataEditor.TouchableSequenceDataItem;
import GUI.Multitouch.TouchItemInterface;
import GUI.Multitouch.TouchableGraphicsItem;
import GUI.Scene.Editor.BaseSequenceScene;
import GUI.Scene.Editor.EditorScene;
import GUI.Scene.Editor.EventPointsDoubleSequenceScene;
import GUI.Scene.Editor.NoteEventScene;
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
import Session.BaseSequenceDataDescriptor;
import Session.DoubleEventPointsDataDescriptor;

public class SequenceEditor extends BaseSequencerItemEditor implements LzrDmObjectInterface {
	
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
		
		public boolean handleTouchEvent(TouchEvent e, int vGridSnap, int hGridSnap) {
			return this.handleTouchEvent(e);
		}
		
		public boolean handleTouchEvent(TouchEvent e){return false;}
		
		public boolean handleDragEvent(DragEvent e, int vGridSnap, int hGridSnap) {
			return this.handleDragEvent(e);
		}
		
		public boolean handleDragEvent(DragEvent e){return false;}
		
		public boolean handleDeleteEvent(DeleteEvent e, int vGridSnap, int hGridSnap) {
			return this.handleDeleteEvent(e);
		}
		
		public boolean handleDeleteEvent(DeleteEvent e){return false;}
	}
	
	private class SequenceInitMode extends SequenceEditMode {
		private SequenceInitScene scene;
		private int currentInitSequenceTypeIndex = 0;
	
		private String[] sequenceTypeArray = new String[] {
				"Pause",
				"EventPoints",
				"Note"
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
					/*System.out.println("Pause");
					sequence = Core.getInstance().getSequenceController().createPauseSequence(Core.getInstance().oneBarInPPQ());
					setItemType(sequence);
					setCurrentMode(new PauseMode(this.getEditor(), sequence));
					*/
					
					executeCommand(new CreateBaseSequence(this.getEditor(), SequenceType.PAUSE));
				break;
				case 1:
					/*System.out.println("EventPoints");
					sequence = Core.getInstance().getSequenceController().createDoubleTypeEventPointsSequence();
					((EventPointsSequence)sequence).setLength(Core.getInstance().oneBarInPPQ());
					setItemType(sequence);
					setCurrentMode(new EventPointsMode(this.getEditor(), sequence));
					*/
					
					executeCommand(new CreateBaseSequence(this.getEditor(), SequenceType.EVENT_POINTS));
				break;
				case 2:
					/*System.out.println("NoteEvents");
					sequence = Core.getInstance().getSequenceController().createDoubleTypeEventPointsSequence();
					((EventPointsSequence)sequence).setLength(Core.getInstance().oneBarInPPQ());
					setItemType(sequence);
					setCurrentMode(new NoteEventsMode(this.getEditor(), sequence));
					*/
					executeCommand(new CreateBaseSequence(this.getEditor(), SequenceType.NOTE));
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
		
		protected double snapPosition(double pos, int snap) {
			return pos - ((int) pos % snap);
		}
		
		@Override
		public boolean handleDragEvent(DragEvent e, int vSnap, int hSnap) {
			TouchableGraphicsItem target;
			if(dragStartPoints.containsKey(e.getTouchID())) {
				if((target = dragStartPoints.get(e.getTouchID())) != null) {
					if(!this.filterEvent(e)) {
						
						if(vSnap != -1 || hSnap != -1) {
							double xPos = e.getSceneLocation().x();
							double yPos = e.getSceneLocation().y();

							if(vSnap != -1) {
								xPos = this.snapPosition(xPos, vSnap);
							}

							if(hSnap != -1) {
								yPos = this.snapPosition(yPos, hSnap);
							}
							
							e.setSceneLocation(new QPointF(xPos, yPos));
						}
						
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
	
	public enum SequenceType {
		PAUSE,
		EVENT_POINTS,
		NOTE,
	};
	
	public void setSequence(BaseSequence seq, SequenceType type, BaseSequenceDataDescriptor descriptor) {
		switch(type) {
			case PAUSE:
				this.sequence = seq;
				setItemType(sequence);
				setCurrentMode(new PauseMode(this, sequence));
				break;
			case EVENT_POINTS:
				this.sequence = seq;
				setItemType(sequence);
				setCurrentMode(new EventPointsMode(this, sequence));
				break;
			case NOTE:
				this.sequence = seq;
				setItemType(sequence);
				setCurrentMode(new NoteEventsMode(this, sequence));				
				break;
		}
	}
	
	private class EventPointsMode extends PauseMode {
		
		public EventPointsMode(SequenceEditor editor, BaseSequence baseSequence) {
			super(editor, baseSequence, new EventPointsDoubleSequenceScene());
			((BaseSequenceScene)this.getScene()).startMoved.connect(this, "startMoved(Long, Boolean)");
			((BaseSequenceScene)this.getScene()).setStartOffsetCursor(((EventPointsSequence)baseSequence).getStartOffset());
			this.initializeContents((EventPointsSequence<DoubleType>)baseSequence);
		}
		
		private void initializeContents(EventPointsSequence<DoubleType> eps) {
			Iterator<Entry<Long, CopyOnWriteArrayList<DoubleType>>> it = eps.getIterator();
			while(it.hasNext()) {
				Entry<Long, CopyOnWriteArrayList<DoubleType>> entry = it.next();
				
				CopyOnWriteArrayList<DoubleType> currentList = entry.getValue();

				for(DoubleType e: currentList) {
					TouchableDoubleTypeSequenceDataItem item = new TouchableDoubleTypeSequenceDataItem(entry.getKey(), e, this.getEditor());
					this.getScene().addItem(item);
					QPointF pos = item.getPositionFromTickValue(entry.getKey(), e.getValue());
					System.out.println(pos);
					item.setPosition(pos);
					item.dragged.connect(this, "itemDragged(TouchableSequenceDataItem, QPointF, Boolean)");
					//System.out.println("addedItem######################");
				}
			}
		}
		
		public EventPointsMode(SequenceEditor editor, BaseSequence baseSequence, BaseSequenceScene scene) {
			super(editor, baseSequence, scene);
			((BaseSequenceScene)this.getScene()).startMoved.connect(this, "startMoved(Long, Boolean)");
			((BaseSequenceScene)this.getScene()).setStartOffsetCursor(((EventPointsSequence)baseSequence).getStartOffset());
			this.initializeContents((EventPointsSequence<DoubleType>)baseSequence);
		}
		
		private void lengthMoved(Long tick, Boolean successful) {
			if(((EventPointsSequence)this.getBaseSequence()).getStartOffset() < tick && tick > 0) {
				((BaseSequenceScene)this.getScene()).setLengthCursor(tick);
				if(successful) {
					((EventPointsSequence)this.getBaseSequence()).setLength(tick);
				}
			}
		}
		
		private HashMap<TouchableSequenceDataItem, QGraphicsTextItem> labelMap = new HashMap<TouchableSequenceDataItem, QGraphicsTextItem>();
		private QFont labelFont = new QFont("Helvetica [Cronyx]", 24);
		
		protected void itemDragged(TouchableSequenceDataItem item, QPointF position, Boolean successful) {
			if(!successful) {
				item.setPosition(position);
				
				QGraphicsTextItem label = null;
				if((label = labelMap.get(item)) == null) {
					label = new QGraphicsTextItem();
					label.setFont(labelFont);
					labelMap.put(item, label);
					this.getScene().addItem(label);
				}
				
				label.setPos(item.pos().x()-50, item.pos().y()-80);
				label.setPlainText(item.getValueLabelText());
				label.setVisible(true);
				
			} else {
				executeCommand(new MoveSequenceDataItem<DoubleType>((EventPointsSequence<DoubleType>)this.getBaseSequence(), (TouchableSequenceDataItem)item, position));
				
				QGraphicsTextItem label = null;
				if((label = labelMap.get(item)) != null) {
					label.setVisible(false);
				}
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
		public boolean handleTouchEvent(TouchEvent e, int vSnap, int hSnap) {
			if(e instanceof TapEvent) { 
			
			QPointF pos = e.getSceneLocation();
			
			if(!this.filterEvent(e) && !e.isFocused() && pos.x() > 0 && !(this.getScene().itemAt(pos) instanceof TouchableGraphicsItem) && !e.isOngoing()) {
				System.out.println("touched "+e.getTouchID());
				
				if(vSnap != -1 || hSnap != -1) {
					double xPos = e.getSceneLocation().x();
					double yPos = e.getSceneLocation().y();

					if(vSnap != -1) {
						xPos = this.snapPosition(xPos, vSnap);
					}

					if(hSnap != -1) {
						yPos = this.snapPosition(yPos, hSnap);
					}
					pos = new QPointF(xPos, yPos);
				}
				
				this.createItem(pos, vSnap, hSnap);
			}
		}
			return true;
		}
		
		protected void createItem(QPointF pos, int vSnap, int hSnap) {
			executeCommand(new CreateDoublePointSequenceDataItem<DoubleType>((EventPointsSequence)this.getBaseSequence(), pos,this.getEditor(), this.getEditor().getScene(), this, "itemDragged(TouchableSequenceDataItem, QPointF, Boolean)"));
		}
		
		protected void deleteItem(TouchableSequenceDataItem item) {
			executeCommand(new RemoveSequenceDataItem<DoubleType>((EventPointsSequence<DoubleType>) this.getBaseSequence(), (TouchableSequenceDataItem) item, this.getEditor().getScene()));
		}
		
		QRectF crossRect = new QRectF(0,0,20,20);
		@Override
		public boolean handleDeleteEvent(DeleteEvent event) {
			if(!filterEvent(event)) {
				if(event.isSuccessful()) {
					this.focusCurrentEvent(event);
					QPointF crossPoint = event.getSceneCrossPoint();

					System.out.println(crossPoint+" -- "+event.getSceneLocation());
					crossRect.setX(crossPoint.x()-10.0);
					crossRect.setY(crossPoint.y()-10.0);
					crossRect.setWidth(20);
					crossRect.setHeight(20);
					List<QGraphicsItemInterface> items = this.getEditor().getScene().items(crossRect);
					System.out.println(items);
					for(QGraphicsItemInterface item: items) {
						if(item instanceof TouchableSequenceDataItem) {
							this.deleteItem((TouchableSequenceDataItem)item);
							labelMap.remove(item);
							break;
						}
					}
				}	
				return true;
			} else {
				return false;
			}
		}
		
	}
	
	private class NoteEventsMode extends EventPointsMode {

		NoteEventScene scene;
		public NoteEventsMode(SequenceEditor editor, BaseSequence baseSequence) {
			super(editor, baseSequence, new NoteEventScene());
			this.initializeContents((EventPointsSequence<NoteType>) baseSequence);
		}
		
		private void initializeContents(EventPointsSequence<NoteType> eps) {
			Iterator<Entry<Long, CopyOnWriteArrayList<NoteType>>> it = eps.getIterator();
			while(it.hasNext()) {
				Entry<Long, CopyOnWriteArrayList<NoteType>> entry = it.next();
				
				CopyOnWriteArrayList<NoteType> currentList = entry.getValue();

				for(NoteType e: currentList) {
/*					TouchableDoubleTypeSequenceDataItem item = new TouchableDoubleTypeSequenceDataItem(entry.getKey(), e, this.getEditor());
					this.getScene().addItem(item);
					QPointF pos = item.getPositionFromTickValue(entry.getKey(), e.getValue());
					System.out.println(pos);
					item.setPosition(pos);
					item.dragged.connect(this, "itemDragged(TouchableSequenceDataItem, QPointF, Boolean)");
*/
					if(!e.isNoteOff()) {
						TouchableNoteTypeSequenceDataItem noteOnItem = new TouchableNoteTypeSequenceDataItem(entry.getKey(), e, this.getEditor(), null); // note-on
						this.getScene().addItem(noteOnItem);
						QPointF noteOnPos = noteOnItem.getPositionFromTickValue(entry.getKey(), e);
						noteOnItem.setPosition(noteOnPos);
						noteOnItem.dragged.connect( this, "itemDragged(TouchableSequenceDataItem, QPointF, Boolean)");

						TouchableNoteTypeSequenceDataItem noteOffItem = new TouchableNoteTypeSequenceDataItem(entry.getKey()+e.getLength(), e.getNoteOff() , this.getEditor(), noteOnItem); // note-off
						this.getScene().addItem(noteOffItem);
						QPointF noteOffPos = noteOnItem.getPositionFromTickValue(entry.getKey()+e.getLength(), e);
						noteOffItem.setPosition(noteOffPos);
						noteOffItem.dragged.connect( this, "itemDragged(TouchableSequenceDataItem, QPointF, Boolean)");

					}
					//System.out.println("addedItem######################");
				}
			}
		}
		
		protected void createItem(QPointF pos, int vSnap, int hSnap) {
			// error?
			executeCommand(new CreateNoteTypeSequenceDataItem<NoteType>((EventPointsSequence)this.getBaseSequence(), pos,this.getEditor(), vSnap, this.getEditor().getScene(), this, "itemDragged(TouchableSequenceDataItem, QPointF, Boolean)"));
		}
		
		protected void deleteItem(TouchableSequenceDataItem item) {
			// error ?
			executeCommand(new RemoveNoteTypeSequenceDataItem<NoteType>((EventPointsSequence<NoteType>) this.getBaseSequence(), (TouchableNoteTypeSequenceDataItem) item, this.getEditor().getScene()));
		}
		
		
		private HashMap<TouchableSequenceDataItem, QGraphicsTextItem> labelMap = new HashMap<TouchableSequenceDataItem, QGraphicsTextItem>();
		private QFont labelFont = new QFont("Helvetica [Cronyx]", 24);
		
		protected void itemDragged(TouchableSequenceDataItem item, QPointF position, Boolean successful) {
				TouchableNoteTypeSequenceDataItem note = (TouchableNoteTypeSequenceDataItem) item;
				
				if(note.isNoteOff()) {
					if(note.noteOn().pos().x() < position.x()) {
						QPointF newPos = new QPointF(position.x(), note.noteOn().y());
						if(!successful) {
							item.setPosition(newPos);
						} else {
							executeCommand(new MoveNoteTypeSequenceDataItem<NoteType>((EventPointsSequence<NoteType>)this.getBaseSequence(), note, newPos));
						}
					}
				} else {
					TouchableNoteTypeSequenceDataItem noteOff = note.noteOff();
					double noteOffOffset = noteOff.pos().x() - note.x();
					QPointF noteOffPos = new QPointF(position.x() +noteOffOffset, position.y());
					
					if(!successful) {
						note.setPosition(position);
						noteOff.setPosition(noteOffPos);
						
						
						QGraphicsTextItem label = null;
						if((label = labelMap.get(item)) == null) {
							label = new QGraphicsTextItem();
							label.setFont(labelFont);
							labelMap.put(item, label);
							this.getScene().addItem(label);
						}
						
						label.setPos(item.pos().x()-50, item.pos().y()-80);
						label.setPlainText(item.getValueLabelText());
						label.setVisible(true);
						
					} else {
						executeCommand(new MoveNoteTypeSequenceDataItem<NoteType>((EventPointsSequence<NoteType>)this.getBaseSequence(), note, position));
						QGraphicsTextItem label = null;
						if((label = labelMap.get(item)) != null) {
							label.setVisible(false);
						}
						//executeCommand(new MoveSequenceDataItem<DoubleType>((EventPointsSequence<DoubleType>)this.getBaseSequence(), (TouchableSequenceDataItem)noteOff, noteOffPos));
					}
				}
//			} else {
//				executeCommand(new MoveSequenceDataItem<DoubleType>((EventPointsSequence<DoubleType>)this.getBaseSequence(), (TouchableSequenceDataItem)item, position));
//			}
		}
		
	}
	
	private int id = Util.getGroupID();
	private SequenceEditMode currentMode;
	
	private LinkedList<Integer> allowedGestures = new LinkedList<Integer>();
	
	
	public SequenceEditor() {
		super();
		
		allowedGestures.add(sparshui.gestures.GestureType.DRAG_GESTURE.ordinal());
		allowedGestures.add(sparshui.gestures.GestureType.DELETE_GESTURE.ordinal());
		allowedGestures.add(sparshui.gestures.GestureType.TAP_GESTURE.ordinal());
		
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
	
	public void handleExtendedGestureEvent(ExtendedGestureEvent event, int vSnap, int hSnap) {
		this.updateGestureVisualization(event);
		if(event instanceof GroupEvent) {
			GroupEvent e = (GroupEvent) event;
			this.handleGroupEvent(e);
		} else if(event instanceof DeleteEvent) {
			DeleteEvent e = (DeleteEvent) event;
			if(currentMode.handleDeleteEvent(e, vSnap, hSnap)) {
				super.handleDeleteEvent(e);
			}
			
		} else if(event instanceof DragEvent) {
			DragEvent e = (DragEvent) event;
			currentMode.handleDragEvent(e, vSnap, hSnap);
		}
	}
	
	@Override
	public void handleTouchEvent(TouchEvent e, int vSnap, int hSnap) {
		currentMode.handleTouchEvent(e, vSnap, hSnap);
	}
	
	@Override
	protected QPainterPath updateGestureVisualization(ExtendedGestureEvent event) {
		QPainterPath ppath = null;
		QGraphicsPathItem p;
		
		if(event.isOngoing()) {
			QPointF point = event.getSceneLocation();

			if((p = gestureVisualizationsMap.get(event.getTouchID())) == null) {
				p = new QGraphicsPathItem();
				//p.setFlag(GraphicsItemFlag.ItemIgnoresTransformations, true);
				p.setZValue(-1000.0);
				this.scene.addItem(p);
				//p.setPos(point);
				//this.scene().addEllipse(point.x(), point.y(), 100,100);
				QPen pen = new QPen(QColor.yellow);
				//pen.setCosmetic(true);
				pen.setWidth(4);
				p.setPen(pen);
				QPainterPath path = new QPainterPath();
				path.addEllipse(point, 50,50);
				path.moveTo(point);
				p.setPath(path);
				gestureVisualizationsMap.put(event.getTouchID(), p);
			} 

			QPainterPath path = new QPainterPath(p.path());
			path.lineTo(point);
			p.setPath(path);
		} else {
			if((p = gestureVisualizationsMap.get(event.getTouchID())) != null) {
				this.scene.removeItem(gestureVisualizationsMap.get(event.getTouchID()));
				this.gestureVisualizationsMap.remove(event.getTouchID());
				if(event instanceof GroupEvent) {
					System.out.println(event+" "+p);
					((GroupEvent)event).setPath(p.path());
				}
			}
		}
		
		if(p != null) {
			ppath =  p.path();
		}
		
		return ppath;
	}
	
}
