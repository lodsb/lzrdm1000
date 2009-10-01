package GUI.Editor.Commands.SynthesizerEditor;

import lazerdoom.Core;
import lazerdoom.LzrDmObjectInterface;
import GUI.Editor.BaseEditorCommand;
import GUI.Editor.SynthesizerEditor;
import Synth.SynthInstance;

public class SetCurrentSynthCommand extends BaseEditorCommand {

	private LzrDmObjectInterface synthEd;
	private String uniqueID;
	
	public SetCurrentSynthCommand(String uniqueID, SynthesizerEditor synthesizerEditor) {
		
		this.synthEd = synthesizerEditor;
		this.uniqueID = uniqueID;
	}

	@Override
	public boolean execute() {
		SynthInstance synth = Core.getInstance().getSynthController().createSynthInstance(this.uniqueID);
		System.out.println(uniqueID+" synth "+synth + " "+this.synthEd);
		((SynthesizerEditor)this.synthEd).setCurrentSynth((SynthInstance)synth);
		return true;
	}

}
