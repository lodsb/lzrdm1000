package GUI.Editor;

import com.trolltech.qt.core.QPointF;

import sparshui.common.messages.events.DragEvent;
import GUI.Editor.Commands.ConnectSequencesCommand;
import GUI.Editor.Commands.CreateSequenceCommand;
import GUI.Editor.Commands.CreateSequencePlayerCommand;
import GUI.Item.BaseSequenceViewItem;
import GUI.Item.ConnectableSequenceInterface;
import GUI.Item.SequenceConnector;
import GUI.Item.SequenceItem;
import GUI.Item.SequencerMenuButton;
import GUI.Scene.Editor.EditorScene;

public class SequencerEditor extends Editor {

	public SequencerEditor(EditorScene scene, boolean showTouchPoints) {
		super(scene, showTouchPoints);
	}

	@Override
	protected void handleDragEvent(DragEvent event) {
		if(event.getSource() instanceof BaseSequenceViewItem) {
			BaseSequenceViewItem item = (BaseSequenceViewItem) event.getSource();
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
		}
	}
}
