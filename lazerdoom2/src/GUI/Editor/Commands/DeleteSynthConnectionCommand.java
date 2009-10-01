package GUI.Editor.Commands;

import lazerdoom.Core;
import lazerdoom.LzrDmObjectInterface;

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

	private LzrDmObjectInterface scn;
	private LzrDmObjectInterface cnn;
	
	public DeleteSynthConnectionCommand(SynthConnection item, EditorScene scene) {
		this.scn = scene;
		this.cnn = item;
	}

	@Override
	public boolean execute() {
		boolean ret = false;
		BaseSequence seq = null;
		SynthInstance synth = null;
		
		SynthConnection connection = (SynthConnection) this.cnn;
		EditorScene scene = (EditorScene) this.scn;
		
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
				
				scene.removeItem(connection);
				connection.remove();
			}
		}
		
		return ret;
	}

}
