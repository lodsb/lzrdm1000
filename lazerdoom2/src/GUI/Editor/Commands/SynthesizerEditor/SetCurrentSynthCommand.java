package GUI.Editor.Commands.SynthesizerEditor;

import GUI.Editor.BaseEditorCommand;
import GUI.Editor.SynthesizerEditor;
import Synth.SynthInstance;

public class SetCurrentSynthCommand extends BaseEditorCommand {

	private SynthesizerEditor synthEd;
	private SynthInstance synth;
	
	public SetCurrentSynthCommand(SynthInstance synth,
			SynthesizerEditor synthesizerEditor) {
		
		this.synthEd = synthesizerEditor;
		this.synth = synth;
	}

	@Override
	public boolean execute() {
		this.synthEd.setCurrentSynth(this.synth);
		return true;
	}

}
