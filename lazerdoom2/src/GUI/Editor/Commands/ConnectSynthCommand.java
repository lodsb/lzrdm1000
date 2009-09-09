package GUI.Editor.Commands;

import lazerdoom.Core;

import com.trolltech.qt.gui.QGraphicsScene;

import Control.ParameterControlBus;
import GUI.Editor.BaseEditorCommand;
import GUI.Item.SequenceItem;
import GUI.Item.SynthConnection;
import GUI.Item.SynthInConnector;
import GUI.Item.SynthOutConnector;
import Sequencer.EventSequenceInterface;
import Synth.SynthInstance;

public class ConnectSynthCommand extends BaseEditorCommand {
	private QGraphicsScene scene;
	private SynthInConnector synthIn;
	private SynthOutConnector synthOut;
	
	public ConnectSynthCommand(SynthOutConnector src, SynthInConnector dst, QGraphicsScene scene) {
		this.synthOut = src;
		this.synthIn = dst;
		this.scene = scene;
	}
	
	@Override
	public boolean execute() {
		boolean ret = false;
		
		EventSequenceInterface seq;
		ParameterControlBus bus = synthIn.getParameterControlBus();
		SynthInstance synth = synthIn.getSynthInstance();
		
		if(this.synthOut.getParent() instanceof SequenceItem) {
			if(((SequenceItem)this.synthOut.getParent()).getBaseSequence() instanceof EventSequenceInterface) {
				seq = (EventSequenceInterface) ((SequenceItem)this.synthOut.getParent()).getBaseSequence();
				
				ret = Core.getInstance().getSynthController().connect(seq, synth, bus);
				
				if(ret) {
					this.scene.addItem(new SynthConnection(synthIn, synthOut));
				}
			}
		}
		
		return ret;
	}

}
