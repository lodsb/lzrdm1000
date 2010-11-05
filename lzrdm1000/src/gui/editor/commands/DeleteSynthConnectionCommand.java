package gui.editor.commands;

import sequencer.BaseSequence;
import sequencer.EventSequenceInterface;
import synth.SynthInstance;
import gui.editor.BaseEditorCommand;
import gui.item.SequenceItem;
import gui.item.SynthConnection;
import gui.item.SynthesizerItem;
import gui.scene.editor.EditorScene;
import lazerdoom.Core;
import lazerdoom.LzrDmObjectInterface;

import com.trolltech.qt.gui.QGraphicsScene;


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
