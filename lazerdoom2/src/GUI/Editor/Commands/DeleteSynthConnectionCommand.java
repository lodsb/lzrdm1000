package GUI.Editor.Commands;

import lazerdoom.Core;

import com.trolltech.qt.gui.QGraphicsScene;

import GUI.Editor.BaseEditorCommand;
import GUI.Item.SequenceItem;
import GUI.Item.SynthConnection;
import GUI.Item.SynthesizerItem;
import GUI.Scene.Editor.EditorScene;
import Sequencer.BaseSequence;
import Sequencer.EventSequenceInterface;
import Synth.SynthInstance;

public class DeleteSynthConnectionCommand extends BaseEditorCommand {

	private QGraphicsScene scene;
	private SynthConnection connection;
	
	public DeleteSynthConnectionCommand(SynthConnection item, QGraphicsScene scene) {
		this.scene = scene;
		this.connection = item;
	}

	@Override
	public boolean execute() {
		boolean ret = false;
		BaseSequence seq = null;
		SynthInstance synth = null;
		
		if(connection.getSource().getParent() instanceof SynthesizerItem) {
			synth = ((SynthesizerItem)connection.getSource().getParent()).getSynthesizer();

			if(connection.getDestination().getParent() instanceof SequenceItem) {
				seq = ((SequenceItem)connection.getDestination().getParent()).getBaseSequence();
			}
		} else if(connection.getSource().getParent() instanceof SequenceItem) {
			seq = ((SequenceItem)connection.getSource().getParent()).getBaseSequence();

			if(connection.getDestination().getParent() instanceof SynthesizerItem) {
				synth = ((SynthesizerItem)connection.getDestination().getParent()).getSynthesizer();
			}
		}
		
		if(seq != null && synth != null) {
			if(seq instanceof EventSequenceInterface) {
				EventSequenceInterface eseq = (EventSequenceInterface) seq;
				ret = Core.getInstance().getSynthController().disconnect(eseq, synth);
				
				this.scene.removeItem(this.connection);
				this.connection.remove();
			}
		}
		
		return ret;
	}

}
