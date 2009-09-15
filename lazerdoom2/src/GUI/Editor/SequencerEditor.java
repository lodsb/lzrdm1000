package GUI.Editor;

import java.util.LinkedList;
import java.util.List;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.gui.QGraphicsItemInterface;

import sparshui.common.messages.events.DeleteEvent;
import sparshui.common.messages.events.DragEvent;
import GUI.Editor.Commands.*;
import GUI.Item.BaseSequenceViewItem;
import GUI.Item.BaseSequencerItem;
import GUI.Item.ConnectableSequenceInterface;
import GUI.Item.ConnectableSynthInterface;
import GUI.Item.EditorCursor;
import GUI.Item.SequenceConnection;
import GUI.Item.SequenceConnector;
import GUI.Item.SequenceItem;
import GUI.Item.SequencePlayerItem;
import GUI.Item.SequencerMenuButton;
import GUI.Item.SynthConnection;
import GUI.Item.SynthConnector;
import GUI.Item.SynthInConnector;
import GUI.Item.SynthOutConnector;
import GUI.Item.SynthesizerItem;
import GUI.Item.Editor.TouchableEditor;
import GUI.Multitouch.TouchableGraphicsItem;
import GUI.Scene.Editor.EditorScene;
import GUI.View.SequencerView;
import Sequencer.SequencerInterface;

public class SequencerEditor extends Editor {

	public SequencerEditor(EditorScene scene, boolean showTouchPoints) {
		super(scene, showTouchPoints);
	}

	private QRectF crossRect = new QRectF(-10, -10, 20,20);
	
	public void openEditor(EditorCursor cursor, BaseSequencerItem item) {
		BaseSequencerItemEditor editor = SequencerView.getInstance().getItemEditorController().getEditor(item);
		if(editor != null) {
			cursor.showTouchableEditor(editor);
		}
	}
	
	public void closeEditor(EditorCursor cursor) {
		cursor.hideTouchableEditor();
	}
	
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
					if(item instanceof SequenceItem) {
						System.out.println("delete item!");
						this.executeCommand(new DeleteSequenceItemCommand((SequenceItem)item, this.getScene()));
						break;
					}
					
					if(item instanceof SequencePlayerItem) {
						this.executeCommand(new DeleteSequencePlayerCommand((SequencePlayerItem)item, this.getScene()));
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
		if(event.getSource() instanceof BaseSequencerItem) {
			BaseSequencerItem item = (BaseSequencerItem) event.getSource();
			item.setPosition(new QPointF(event.getSceneLocation().x()-item.boundingRect().width()/2, event.getSceneLocation().y()-item.boundingRect().height()/2));
		} else if(event.getSource() instanceof EditorCursor) {
			EditorCursor cursor = (EditorCursor) event.getSource();
			cursor.setPosition(new QPointF(event.getSceneLocation().x()-cursor.boundingRect().width()/2, event.getSceneLocation().y()-cursor.boundingRect().height()/2));
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
