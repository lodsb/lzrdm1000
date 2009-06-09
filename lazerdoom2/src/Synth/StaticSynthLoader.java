package Synth;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import de.sciss.jcollider.Server;
import de.sciss.jcollider.Synth;
import de.sciss.jcollider.SynthDef;

public class StaticSynthLoader implements SynthLoaderInterface {
	private Server server;
	
	private HashMap<SynthInfo, SynthDef> synthInfoList = new HashMap<SynthInfo, SynthDef>();
	
	public StaticSynthLoader(Server server) {
		this.server = server;
	}
	
	private void createSynthDefs() {
		SynthInfo synthInfo;
		SynthDef synthDef;
		
		// TestSynth1
		
		synthInfo = new SynthInfo("SimpleSynth", "A simple Sine-Synth", )
		
	}
	
	public List<SynthInfo> getAvailableSynths() {
		LinkedList<SynthInfo> synthList = new LinkedList<SynthInfo>();
		
		for(SynthInfo si: synthInfoList.keySet()) {
			synthList.add(si);
		}
		
		return synthList;
	}
	
	@Override
	public Synth instantiateSynth(SynthInfo synthInfo) {
		// TODO Auto-generated method stub
		return null;
	}
}
