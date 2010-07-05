package gui.editor.commands;

import sequencer.EventSequenceInterface;
import synth.SynthInstance;
import gui.editor.BaseEditorCommand;
import gui.item.SequenceItem;
import gui.item.SynthConnection;
import gui.item.SynthInConnector;
import gui.item.SynthOutConnector;
import gui.scene.editor.EditorScene;
import lazerdoom.Core;
import lazerdoom.LzrDmObjectInterface;

import com.trolltech.qt.gui.QGraphicsScene;

import control.ParameterControlBus;


public class ConnectSynthCommand extends BaseEditorCommand {
	private LzrDmObjectInterface scene;
	private LzrDmObjectInterface synthIn;
	private LzrDmObjectInterface synthOut;
	
	public ConnectSynthCommand(SynthOutConnector src, SynthInConnector dst, EditorScene scene) {
		this.synthOut = src;
		this.synthIn = dst;
		this.scene = scene;
	}
	
	@Override
	public boolean execute() {
		boolean ret = false;
		
		EventSequenceInterface seq;
		ParameterControlBus bus = ((SynthInConnector)synthIn).getParameterControlBus();
		SynthInstance synth = ((SynthInConnector)synthIn).getSynthInstance();
		
		if(((SynthOutConnector)this.synthOut).getParent() instanceof SequenceItem) {
			if(((SequenceItem)((SynthOutConnector)this.synthOut).getParent()).getBaseSequence() instanceof EventSequenceInterface) {
				seq = (EventSequenceInterface) ((SequenceItem)((SynthOutConnector)this.synthOut).getParent()).getBaseSequence();
				
				ret = Core.getInstance().getSynthController().connect(seq, synth, bus);
				
				if(ret) {
					((EditorScene)this.scene).addItem(new SynthConnection((SynthInConnector)synthIn, (SynthOutConnector)synthOut));
				}
			}
		}
		
		return ret;
	}

}
