package Synth;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import Control.ControlServer;
import Control.ParameterControlBus;
import Sequencer.EventSequenceInterface;
import Synth.Graph.SynthesizerGraph;

import de.sciss.jcollider.*;

public class SynthController {
	private Server server;
	
	private LinkedList<SynthLoaderInterface> synthLoaderList = new LinkedList<SynthLoaderInterface>();
	private LinkedList<SynthInfo> availableSynths = new LinkedList<SynthInfo>();
	private LinkedList<Synth> loadedSynths = new LinkedList<Synth>();
	private HashMap<SynthInfo, SynthLoaderInterface> synthLoaderMap = new HashMap<SynthInfo, SynthLoaderInterface>();
	private HashMap<Synth, SynthInfo> synthInfoMap = new HashMap<Synth, SynthInfo>();
	
	private SynthesizerGraph graph = new SynthesizerGraph();
	
	private StaticSynthLoader staticSynthLoader;
	private ControlServer controlServer;
	
	public SynthController(Server server, ControlServer controlServer) {
		this.server = server;
		this.controlServer = controlServer;
		staticSynthLoader = new StaticSynthLoader(this.server);
	}
	
	public void init() {
		try {
			this.server.sync(1f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		staticSynthLoader.init();
		this.addSynthLoader(staticSynthLoader);
	}
	
	public List<SynthInfo> getAvailableSynths() {
		return (List<SynthInfo>) availableSynths.clone();
	}
	
	public List<Synth> getLoadedSynths() {
		return (List<Synth>) loadedSynths.clone();
	}
	
	public boolean connect(EventSequenceInterface seq, SynthInstance synth, ParameterControlBus controlBus) {
		return this.graph.connect(seq, synth, controlBus);
	}
	
	public SynthInstance createSynthInstance(SynthInfo info) {
		Synth synth = null;
		
		SynthLoaderInterface sli = synthLoaderMap.get(info);
		
		if(info != null) {
			synth = sli.instantiateSynth(info);
			if(synth != null) {
				loadedSynths.add(synth);
				synthInfoMap.put(synth, info);
			}
		}
		
		SynthInstance synthInstance = null;
		
		if(info != null) {
			synthInstance = new SynthInstance(this.controlServer, info, synth);
		}
			
		return synthInstance;
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
