package Synth;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import de.sciss.jcollider.Server;
import de.sciss.jcollider.Synth;
import de.sciss.jcollider.SynthDef;

public class StaticSynthLoader implements SynthLoaderInterface {
	private Server server;
	
	private HashMap<SynthInfo, SynthDef> synthInfoMap = new HashMap<SynthInfo, SynthDef>();
	
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
		
		for(SynthInfo si: synthInfoMap.keySet()) {
			synthList.add(si);
		}
		
		return synthList;
	}
	
	@Override
	public Synth instantiateSynth(SynthInfo synthInfo) {
		SynthDef synthDef = synthInfoMap.get(synthInfo);
		Synth synth = null;
		
		if(synthDef != null) {
			
		}
 		
		return synth; 
	}
}
