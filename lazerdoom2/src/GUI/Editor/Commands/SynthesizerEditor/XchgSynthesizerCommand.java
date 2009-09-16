package GUI.Editor.Commands.SynthesizerEditor;

import java.util.LinkedList;

import edu.uci.ics.jung.graph.util.Pair;

import GUI.Editor.BaseEditorCommand;
import GUI.Editor.SequencerEditor;
import GUI.Editor.SynthesizerEditor;
import GUI.Editor.Commands.ConnectSynthCommand;
import GUI.Editor.Commands.DeleteSynthConnectionCommand;
import GUI.Item.SynthConnection;
import GUI.Item.SynthInConnector;
import GUI.Item.SynthOutConnector;
import GUI.Item.SynthesizerItem;
import Synth.SynthInstance;

public class XchgSynthesizerCommand extends BaseEditorCommand {

	SynthesizerItem synthItem;
	SynthInstance oldSynth;
	SynthInstance newSynth;
	SequencerEditor editor;
	SynthesizerEditor synthEditor;
	
	public XchgSynthesizerCommand(SynthesizerItem synthesizerItem,
			SynthInstance currentSynth, SynthInstance synth, SynthesizerEditor synthEditor,
			SequencerEditor sequencerEditor) {
		
		this.synthItem = synthesizerItem;
		this.oldSynth = currentSynth;
		this.newSynth = synth;
		this.editor = sequencerEditor;
		this.synthEditor = synthEditor;
	}

	@Override
	public boolean execute() {
		LinkedList<Pair<Object>> oldInConnections = new LinkedList<Pair<Object>>();
		LinkedList<SynthConnection> toDeleteConnections = new LinkedList<SynthConnection>();
		
		for(SynthInConnector inC : synthItem.getSynthInConnectors()) {
			String name = inC.getParameterName();
			for(SynthConnection con: inC.getConnections()) {
				toDeleteConnections.add(con);
				if(con.getDestination() instanceof SynthOutConnector) {
					oldInConnections.add(new Pair<Object>(name, con.getDestination()));
				} else if(con.getSource() instanceof SynthOutConnector) {
					oldInConnections.add(new Pair<Object>(name, con.getSource()));
				}
			}
		}
		
		for(SynthConnection delCon: toDeleteConnections) {
			this.editor.executeCommand(new DeleteSynthConnectionCommand(delCon, this.editor.getScene()));
		}
		
		// changes synthItem's ports
		this.synthEditor.setCurrentSynth(newSynth);
		
		for(Pair<Object> obj: oldInConnections) {
			String name = (String) obj.getFirst();
			
			for(SynthInConnector inC: synthItem.getSynthInConnectors()) {
				if(inC.getParameterName().equals(name)) {
					this.editor.executeCommand(new ConnectSynthCommand((SynthOutConnector)obj.getSecond(), inC, this.editor.getScene()));
				}
			}
		}
		
		return true;
	}

}
