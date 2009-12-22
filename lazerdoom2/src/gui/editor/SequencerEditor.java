package gui.editor;

import gui.editor.commands.*;
import gui.item.BaseSequenceViewItem;
import gui.item.BaseSequencerItem;
import gui.item.ConnectableSequenceInterface;
import gui.item.ConnectableSynthInterface;
import gui.item.EditorCursor;
import gui.item.SequenceConnection;
import gui.item.SequenceConnector;
import gui.item.SequenceItem;
import gui.item.SequencePlayerItem;
import gui.item.SequencerMenuButton;
import gui.item.SynthConnection;
import gui.item.SynthConnector;
import gui.item.SynthInConnector;
import gui.item.SynthOutConnector;
import gui.item.SynthesizerItem;
import gui.item.editor.TouchableEditor;
import gui.item.editor.TouchableItemGroupItem;
import gui.multitouch.TouchableGraphicsItem;
import gui.scene.editor.EditorScene;
import gui.view.SequencerView;

import java.util.LinkedList;
import java.util.List;

import lazerdoom.LzrDmObjectInterface;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QPainterPath;

import sequencer.SequencerInterface;
import session.SessionHandler;
import sparshui.common.messages.events.DeleteEvent;
import sparshui.common.messages.events.DragEvent;
import sparshui.common.messages.events.GroupEvent;

public class SequencerEditor extends Editor implements LzrDmObjectInterface {

	public SequencerEditor(EditorScene scene, boolean showTouchPoints) {
		super(scene, showTouchPoints);
		SessionHandler.getInstance().registerObject(this);
		SessionHandler.getInstance().registerObject(scene);
	}

	@Override
	protected void handleGroupEvent(GroupEvent event) {
		if(event.isSuccessful()) {
			QPainterPath groupGesturePath = event.getPath();
			this.executeCommand(new GroupCommand(groupGesturePath, this.getScene()));
		}
	}
	
	public void openEditor(EditorCursor cursor, BaseSequencerItem item) {
		if(!SequencerView.getInstance().getItemEditorController().editorExists(item)) {
			this.executeCommand(new CreateBaseSequencerItemEditor(item));
		}
		
		BaseSequencerItemEditor editor = SequencerView.getInstance().getItemEditorController().getEditor(item);
		if(editor != null) {
			cursor.showTouchableEditor(editor);
		}
	}
	
	public void closeEditor(EditorCursor cursor) {
		cursor.hideTouchableEditor();
	}
	private QRectF crossRect = new QRectF(-10, -10, 20,20);
	@Override
	protected void handleDeleteEvent(DeleteEvent event) {
		super.handleDeleteEvent(event);
		
		if(event.isSuccessful()) {
			QPointF crossPoint = event.getSceneCrossPoint();
			
			crossRect.setX(crossPoint.x()-10.0);
			crossRect.setY(crossPoint.y()-10.0);
			crossRect.setWidth(20);
			crossRect.setHeight(20);
			List<QGraphicsItemInterface> items = this.getScene().items(crossRect);
			
			for(QGraphicsItemInterface item: items) {
				if(item instanceof TouchableGraphicsItem) {
					
					if(((TouchableGraphicsItem) item).belongsToGroup() != null) {
						break;
					}
					
					if(item instanceof TouchableItemGroupItem) {
						this.executeCommand(new UnGroupCommand((TouchableItemGroupItem)item));
						System.out.println("UNGROUP SOUP SOUP");
					}
					if(item instanceof SequenceItem) {
						System.out.println("delete item!");
						this.executeCommand(new DeleteSequenceItemCommand((SequenceItem)item, this.getScene()));
						break;
					}
					
					if(item instanceof SequencePlayerItem) {
						this.executeCommand(new DeleteSequencePlayerCommand((SequencePlayerItem)item, this.getScene()));
						break;
					}
					
					if(item instanceof SequenceConnection) {
						this.executeCommand(new DeleteSequenceConnectionCommand((SequenceConnection)item, this.getScene()));
						break;
					}
					
					if(item instanceof SynthConnection) {
						this.executeCommand(new DeleteSynthConnectionCommand((SynthConnection)item, this.getScene()));
						break;
					}
					
					if(item instanceof SynthesizerItem) {
						this.executeCommand(new DeleteSynthItemCommand((SynthesizerItem)item , this.getScene()));
						break;
					}
					
					if(item instanceof EditorCursor) {
						this.executeCommand(new DeleteEditorCursor((EditorCursor)item, this.getScene()));
						break;
					}
				}
			}
		}
	}
	
	@Override
	protected void handleDragEvent(DragEvent event) {
		System.out.println(event);
		System.out.println(event.getSource());
		if(event.getSource() instanceof TouchableGraphicsItem) {
			if(((TouchableGraphicsItem)event.getSource()).belongsToGroup() != null) {
				//System.out.println("BELONGS TO GROUP");
				return;
			}
		}
		
		if(!event.isFocused() && event.getSource() instanceof TouchableGraphicsItem) {
			SequencerView.getInstance().focusCurrentEvent(event.getSource(), event);
		}
		
		if(event.getSource() instanceof BaseSequencerItem) {
			BaseSequencerItem item = (BaseSequencerItem) event.getSource();
			if(event.isSuccessful()) {
				this.executeCommand(new MoveTouchableGraphicsItemCommand(new QPointF(event.getSceneLocation().x()-item.boundingRect().width()/2, event.getSceneLocation().y()-item.boundingRect().height()/2), item));
			} else {
				item.setPosition(new QPointF(event.getSceneLocation().x()-item.boundingRect().width()/2, event.getSceneLocation().y()-item.boundingRect().height()/2));
			}
		} else if(event.getSource() instanceof EditorCursor) {
			EditorCursor cursor = (EditorCursor) event.getSource();
			if(event.isSuccessful()) {
				this.executeCommand(new MoveTouchableGraphicsItemCommand(event.getSceneLocation(), cursor));
			} else {
				cursor.setPosition(event.getSceneLocation());//new QPointF(event.getSceneLocation().x()-cursor.boundingRect().width()/2, event.getSceneLocation().y()-cursor.boundingRect().height()/2));
			}
		} else if(event.getSource() instanceof TouchableItemGroupItem) {
			TouchableItemGroupItem group = (TouchableItemGroupItem) event.getSource();
			if(event.isSuccessful()) {
				this.executeCommand(new MoveTouchableGraphicsItemCommand(event.getSceneLocation(), group));
			} else {
				group.setPosition(event.getSceneLocation());
			}
		}
		
		if(event.isSuccessful()) {
			if(event.getSource() instanceof SequencerMenuButton) {
				// obj creation
				SequencerMenuButton smb = (SequencerMenuButton) event.getSource();

				switch(smb.getActionType()) {
				case addSequence:
					this.executeCommand(new CreateSequenceCommand(event.getSceneLocation(), this.getScene()));
					break;
				case addSequencePlayer:
					this.executeCommand(new CreateSequencePlayerCommand(event.getSceneLocation(), this.getScene()));
					break;
				case addSynth:
					this.executeCommand(new CreateSynthInstanceCommand(event.getSceneLocation(), this.getScene()));
					break;
				case addEditor:
					this.executeCommand(new CreateEditorCommand(event.getSceneLocation(), this.getScene(), this));
					break;
				}
			} // obj connection
			else if(event.getSource() instanceof SequenceConnector) {
				ConnectableSequenceInterface src = (ConnectableSequenceInterface) ((SequenceConnector) event.getSource()).getParent();
				
				if(this.getScene().itemAt(event.getSceneLocation()) instanceof SequenceConnector) {
					ConnectableSequenceInterface dst = (ConnectableSequenceInterface) ((SequenceConnector)this.getScene().itemAt(event.getSceneLocation())).getParent();
					
					if(((SequenceConnector) event.getSource()).isInConnector()) {
						ConnectableSequenceInterface xchg = dst;
						dst = src;
						src = xchg;
					}
					
					this.executeCommand(new ConnectSequencesCommand(src, dst, this.getScene()));
				}
			}
			else if(event.getSource() instanceof SynthConnector) {
				SynthConnector src = (SynthConnector) event.getSource();
				
				if(this.getScene().itemAt(event.getSceneLocation()) instanceof SynthConnector) {
					SynthConnector dst = (SynthConnector) this.getScene().itemAt(event.getSceneLocation());
					
					if(src.isInPort() && !dst.isInPort() || !src.isInPort() && dst.isInPort()) {
						SynthConnector xchg;
						
						if(src.isInPort()) {
							xchg = dst;
							dst = src;
							src = xchg;
						}
						System.out.println("connect synth");
						this.executeCommand(new ConnectSynthCommand((SynthOutConnector)src, (SynthInConnector)dst, this.getScene()));
						
					}
				}
			}
		}
	}
}
