package Synth;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import de.sciss.jcollider.*;

public class SynthManager {
	private Server server;
	
	private LinkedList<SynthLoaderInterface> synthLoaderList = new LinkedList<SynthLoaderInterface>();
	private LinkedList<SynthInfo> availableSynths = new LinkedList<SynthInfo>();
	private LinkedList<Synth> loadedSynths = new LinkedList<Synth>();
	private HashMap<SynthInfo, SynthLoaderInterface> synthLoaderMap = new HashMap<SynthInfo, SynthLoaderInterface>();
	private HashMap<Synth, SynthInfo> synthInfoMap = new HashMap<Synth, SynthInfo>();
	
	private StaticSynthLoader staticSynthLoader;
	
	public SynthManager(Server server) {
		this.server = server;
		staticSynthLoader = new StaticSynthLoader(this.server);
		
		this.addSynthLoader(staticSynthLoader);
	}
	
	public List<SynthInfo> getAvailableSynths() {
		return (List<SynthInfo>) availableSynths.clone();
	}
	
	public List<Synth> getLoadedSynths() {
		return (List<Synth>) loadedSynths.clone();
	}
	
	public Synth createInstance(SynthInfo info) {
		Synth synth = null;
		
		SynthLoaderInterface sli = synthLoaderMap.get(info);
		
		if(info != null) {
			synth = sli.instantiateSynth(info);
			if(synth != null) {
				loadedSynths.add(synth);
				synthInfoMap.put(synth, info);
			}
		}
		
		return synth;
	}
	
	public SynthInfo getSynthInfo(Synth synth) {
		return synthInfoMap.get(synth);
	}
	
	private void addSynthLoader(SynthLoaderInterface synthLoader) {
		List<SynthInfo> synthList = (synthLoader.getAvailableSynths());
		
		synthLoaderList.add(synthLoader);
		availableSynths.addAll(synthList);
		
		for(SynthInfo si: synthList) {
			synthLoaderMap.put(si, synthLoader);
		}
	}
	
}
