package gui.editor.commands.synthesizereditor;

import java.util.LinkedList;

import synth.SynthInstance;

import lazerdoom.Core;
import lazerdoom.LzrDmObjectInterface;

import edu.uci.ics.jung.graph.util.Pair;
import gui.editor.BaseEditorCommand;
import gui.editor.SequencerEditor;
import gui.editor.SynthesizerEditor;
import gui.editor.commands.ConnectSynthCommand;
import gui.editor.commands.DeleteSynthConnectionCommand;
import gui.item.SynthConnection;
import gui.item.SynthInConnector;
import gui.item.SynthOutConnector;
import gui.item.SynthesizerItem;


public class XchgSynthesizerCommand extends BaseEditorCommand {

	LzrDmObjectInterface synthItem;
	LzrDmObjectInterface oldSynth;
	String newSynthID;
	LzrDmObjectInterface editor;
	LzrDmObjectInterface synthEditor;
	
	public XchgSynthesizerCommand(SynthesizerItem synthesizerItem,
			SynthInstance currentSynth, String  uniqueID, SynthesizerEditor synthEditor,
			SequencerEditor sequencerEditor) {
		
		this.synthItem = synthesizerItem;
		this.oldSynth = currentSynth;
		this.newSynthID = uniqueID;
		this.editor = sequencerEditor;
		this.synthEditor = synthEditor;
		System.out.println("FIXME: disable prohibitSaveToSessionFile() in XChg");
		this.prohibitSaveToSessionFile();
	}

	@Override
	public boolean execute() {
		LinkedList<Pair<Object>> oldInConnections = new LinkedList<Pair<Object>>();
		LinkedList<SynthConnection> toDeleteConnections = new LinkedList<SynthConnection>();
		
		SynthInstance newSynth = Core.getInstance().getSynthController().createSynthInstance(this.newSynthID);
		
		for(SynthInConnector inC : ((SynthesizerItem)synthItem).getSynthInConnectors()) {
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
			((SequencerEditor)this.editor).executeCommand(new DeleteSynthConnectionCommand(delCon, ((SequencerEditor)this.editor).getScene()));
		}
		
		// changes synthItem's ports
		((SynthesizerEditor)this.synthEditor).setCurrentSynth((SynthInstance)newSynth);
		
		for(Pair<Object> obj: oldInConnections) {
			String name = (String) obj.getFirst();
			
			for(SynthInConnector inC: ((SynthesizerItem)synthItem).getSynthInConnectors()) {
				if(inC.getParameterName().equals(name)) {
					((SequencerEditor)this.editor).executeCommand(new ConnectSynthCommand((SynthOutConnector)obj.getSecond(), inC, ((SequencerEditor)this.editor).getScene()));
				}
			}
		}
		
		return true;
	}

}
