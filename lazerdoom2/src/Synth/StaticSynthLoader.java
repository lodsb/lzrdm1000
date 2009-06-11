package Synth;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import de.sciss.jcollider.Control;
import de.sciss.jcollider.ControlDesc;
import de.sciss.jcollider.Group;
import de.sciss.jcollider.Server;
import de.sciss.jcollider.Synth;
import de.sciss.jcollider.SynthDef;
import de.sciss.jcollider.UGen;

public class StaticSynthLoader implements SynthLoaderInterface {
	private Server server;
	
	private HashMap<SynthInfo, SynthDef> synthInfoMap = new HashMap<SynthInfo, SynthDef>();
	
	public StaticSynthLoader(Server server) {
		this.server = server;
		
		this.createSynthDefs();
	}
	
	private void createSynthDefs() {
		SynthInfo synthInfo;
		SynthDef synthDef;
		
		// TestSynth1
        Control ck = Control.kr( new String[] { "freq"}, new float[] { 0.0f});
        synthDef = new SynthDef( "SimpleSynth", UGen.ar( "SinOsc", ck.getChannel( "freq" )));
        ControlDesc ckd = ck.getDesc(0);
		synthInfo = new SynthInfo(synthDef.getName(), "A simple Sine-Synth", new ControlDesc[]{ckd});
		
		synthInfoMap.put(synthInfo, synthDef);
		
		for(SynthDef sd: synthInfoMap.values()) {
			try {
				sd.send(this.server);
			} catch (IOException e) {
				System.err.println("Error sending SynthDef!");
				e.printStackTrace();
			}
		}
		try {
			this.server.sync(4f);
		} catch (IOException e) {
			e.printStackTrace();
		}

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
			synth = Synth.basicNew(synthInfo.getName(), this.server);
		}
 		
		return synth; 
	}
}
