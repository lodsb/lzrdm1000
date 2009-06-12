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
import de.sciss.jcollider.UGenInfo;

public class StaticSynthLoader implements SynthLoaderInterface {
	private Server server;
	
	private HashMap<SynthInfo, SynthDef> synthInfoMap = new HashMap<SynthInfo, SynthDef>();
	
	public StaticSynthLoader(Server server) {
		this.server = server;
	}
	
	public void init() {
		this.createSynthDefs();
	}
	
	private void createSynthDefs() {
		SynthInfo synthInfo;
		SynthDef synthDef;
		
		try {
			UGenInfo.readBinaryDefinitions();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} 
		
		// TestSynth1
        Control ck = Control.kr( new String[] {"freq"}, new float[] { 0.0f});
        synthDef = new SynthDef( "SimpleSynth", UGen.ar( "Out", UGen.ir( 0 ), UGen.ar( "SinOsc", ck.getChannel(0))));
        ControlDesc ckd = ck.getDesc(0);
		synthInfo = new SynthInfo(synthDef.getName(), "A simple Sine-Synth", new ControlDesc[]{ckd});
		
       /* Control ck = Control.kr( new String[] { "freq", "out" }, new float
        		[] { 440f, 0f });
        		                try {
									new SynthDef( "tutorial-args", UGen.ar( "Out", ck.getChannel
    		( "out" ), UGen.ar( "*", UGen.ar( "SinOsc", ck.getChannel( "freq" )),  
    		UGen.ir( 0.2f )))).send( this.server );
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
        		                try {
									this.server.sync( 4f );
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
		*/
		
		synthInfoMap.put(synthInfo, synthDef);
		
		for(SynthDef sd: synthInfoMap.values()) {
			try {
				sd.send(this.server);
				System.out.println("Sent SynthDef "+sd.getName());
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
