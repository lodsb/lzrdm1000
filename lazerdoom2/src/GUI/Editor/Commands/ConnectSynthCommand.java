package GUI.Editor.Commands;

import lazerdoom.Core;
import lazerdoom.LzrDmObjectInterface;

import com.trolltech.qt.gui.QGraphicsScene;

import Control.ParameterControlBus;
import GUI.Editor.BaseEditorCommand;
import GUI.Item.SequenceItem;
import GUI.Item.SynthConnection;
import GUI.Item.SynthInConnector;
import GUI.Item.SynthOutConnector;
import GUI.Scene.Editor.EditorScene;
import Sequencer.EventSequenceInterface;
import Synth.SynthInstance;

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
