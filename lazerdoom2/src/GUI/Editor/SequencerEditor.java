package GUI.Editor;

import java.util.LinkedList;
import java.util.List;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.gui.QGraphicsItemInterface;

import sparshui.common.messages.events.DeleteEvent;
import sparshui.common.messages.events.DragEvent;
import GUI.Editor.Commands.ConnectSequencesCommand;
import GUI.Editor.Commands.ConnectSynthCommand;
import GUI.Editor.Commands.CreateSequenceCommand;
import GUI.Editor.Commands.CreateSequencePlayerCommand;
import GUI.Editor.Commands.CreateSynthInstanceCommand;
import GUI.Editor.Commands.DeleteSequenceConnectionCommand;
import GUI.Editor.Commands.DeleteSequenceItemCommand;
import GUI.Editor.Commands.DeleteSynthConnectionCommand;
import GUI.Item.BaseSequenceViewItem;
import GUI.Item.BaseSequencerItem;
import GUI.Item.ConnectableSequenceInterface;
import GUI.Item.ConnectableSynthInterface;
import GUI.Item.SequenceConnection;
import GUI.Item.SequenceConnector;
import GUI.Item.SequenceItem;
import GUI.Item.SequencerMenuButton;
import GUI.Item.SynthConnection;
import GUI.Item.SynthConnector;
import GUI.Item.SynthInConnector;
import GUI.Item.SynthOutConnector;
import GUI.Multitouch.TouchableGraphicsItem;
import GUI.Scene.Editor.EditorScene;

public class SequencerEditor extends Editor {

	public SequencerEditor(EditorScene scene, boolean showTouchPoints) {
		super(scene, showTouchPoints);
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
					if(item instanceof SequenceItem) {
						this.executeCommand(new DeleteSequenceItemCommand((SequenceItem)item, this.getScene()));
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
				}
			}
		}
	}
	
	@Override
	protected void handleDragEvent(DragEvent event) {
		if(event.getSource() instanceof BaseSequencerItem) {
			BaseSequencerItem item = (BaseSequencerItem) event.getSource();
			item.setPos(new QPointF(event.getSceneLocation().x()-item.boundingRect().width()/2, event.getSceneLocation().y()-item.boundingRect().height()/2));
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
