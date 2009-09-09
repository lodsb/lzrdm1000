package GUI.Editor;

import com.trolltech.qt.core.QPointF;

import sparshui.common.messages.events.DragEvent;
import GUI.Editor.Commands.ConnectSequencesCommand;
import GUI.Editor.Commands.ConnectSynthCommand;
import GUI.Editor.Commands.CreateSequenceCommand;
import GUI.Editor.Commands.CreateSequencePlayerCommand;
import GUI.Editor.Commands.CreateSynthInstanceCommand;
import GUI.Item.BaseSequenceViewItem;
import GUI.Item.BaseSequencerItem;
import GUI.Item.ConnectableSequenceInterface;
import GUI.Item.ConnectableSynthInterface;
import GUI.Item.SequenceConnector;
import GUI.Item.SequenceItem;
import GUI.Item.SequencerMenuButton;
import GUI.Item.SynthConnector;
import GUI.Item.SynthInConnector;
import GUI.Item.SynthOutConnector;
import GUI.Scene.Editor.EditorScene;

public class SequencerEditor extends Editor {

	public SequencerEditor(EditorScene scene, boolean showTouchPoints) {
		super(scene, showTouchPoints);
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
