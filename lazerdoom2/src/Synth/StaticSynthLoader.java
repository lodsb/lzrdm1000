package Synth;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import de.sciss.jcollider.Control;
import de.sciss.jcollider.ControlDesc;
import de.sciss.jcollider.GraphElem;
import de.sciss.jcollider.Group;
import de.sciss.jcollider.Server;
import de.sciss.jcollider.Synth;
import de.sciss.jcollider.SynthDef;
import de.sciss.jcollider.UGen;
import de.sciss.jcollider.UGenInfo;
import Control.Types.NoteType;

public class StaticSynthLoader implements SynthLoaderInterface {
	private Server server;
	
	private HashMap<SynthInfo, SynthDef> synthInfoMap = new HashMap<SynthInfo, SynthDef>();
	
	private Group group;
	
	public StaticSynthLoader(Server server) {
		this.server = server;
		this.group = this.server.asTarget();
	}
	
	public void init() {
		this.createSynthDefs();
	}
	
	private void createAndRegisterFunkLead() {
		try {
			SynthDef synthDefs[] = SynthDef.readDefFile(new File(System.getProperty("user.dir")+"/src/Synth/SC3Synths/funklead.scsyndef"));
			Control ck = Control.kr( new String[] {"freq","gate", "amp", "cutoff", "rez", "lfospeed"}, new float[] { 0.0f, 0.0f, 1.0f, 20000.0f, 1.0f, 0.0f});
			SynthInfo synthInfo = new PolyphonicSC3SynthInfo(this.server, synthDefs[0].getName(), "TrashLead", new ControlDesc[]{ck.getDesc(0), ck.getDesc(1), ck.getDesc(2), ck.getDesc(3), ck.getDesc(4), ck.getDesc(5)}, 8);
			synthInfoMap.put(synthInfo, synthDefs[0]);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createAndRegisterStringMachine() {
		try {
			SynthDef synthDefs[] = SynthDef.readDefFile(new File(System.getProperty("user.dir")+"/src/Synth/SC3Synths/strings.scsyndef"));
			Control ck = Control.kr( new String[] {"freq","gate", "amp"}, new float[] { 0.0f, 0.0f, 1.0f});
			SynthInfo synthInfo = new PolyphonicSC3SynthInfo(this.server, synthDefs[0].getName(), "StringMachine!", new ControlDesc[]{ck.getDesc(0), ck.getDesc(1), ck.getDesc(2)}, 8);
			synthInfoMap.put(synthInfo, synthDefs[0]);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void createAndRegisterPiano() {
		try {
			SynthDef synthDefs[] = SynthDef.readDefFile(new File(System.getProperty("user.dir")+"/src/Synth/SC3Synths/piano.scsyndef"));
			Control ck = Control.kr( new String[] {"freq","gate"}, new float[] { 0.0f, 0.0f});
			SynthInfo synthInfo = new PolyphonicSC3SynthInfo(this.server, synthDefs[0].getName(), "Pianoooo!", new ControlDesc[]{ck.getDesc(0), ck.getDesc(1)}, 8);
			synthInfoMap.put(synthInfo, synthDefs[0]);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void createAndRegisterDrumMachine() {
		try {
			SynthDef synthDefs[] = SynthDef.readDefFile(new File(System.getProperty("user.dir")+"/src/Synth/SC3Synths/kick.scsyndef"));
			SynthDef kickSynth = synthDefs[0];
			
			SynthInfo kickInfo = new SynthInfo("kick", "kickdrum", null);
			kickInfo.setHidden();
			synthInfoMap.put(kickInfo, kickSynth);
			
			synthDefs = SynthDef.readDefFile(new File(System.getProperty("user.dir")+"/src/Synth/SC3Synths/snare.scsyndef"));
			SynthDef snareSynth = synthDefs[0];
			
			SynthInfo snareInfo = new SynthInfo("snare", "snaredrum", null);
			snareInfo.setHidden();
			synthInfoMap.put(snareInfo, snareSynth);
			
			synthDefs = SynthDef.readDefFile(new File(System.getProperty("user.dir")+"/src/Synth/SC3Synths/tom.scsyndef"));
			SynthDef tomSynth = synthDefs[0];
			
			SynthInfo tomInfo = new SynthInfo("tom", "tomdrum", null);
			tomInfo.setHidden();
			synthInfoMap.put(tomInfo, tomSynth);
			
			synthDefs = SynthDef.readDefFile(new File(System.getProperty("user.dir")+"/src/Synth/SC3Synths/hihat.scsyndef"));
			SynthDef hihatSynth = synthDefs[0];
			
			SynthInfo hihatInfo = new SynthInfo("hihat", "hihatdrum", null);
			hihatInfo.setHidden();
			synthInfoMap.put(hihatInfo, hihatSynth);

			Control ck = Control.kr( new String[] {"gate","gate", "gate", "gate"}, new float[] { 0.0f, 0.0f, 0.0f, 0.0f});
			ControlDesc kickGate = ck.getDesc(0);
			ControlDesc snareGate = ck.getDesc(1);
			ControlDesc tomGate = ck.getDesc(2);
			ControlDesc hihatGate = ck.getDesc(3);
			
			SynthInfo info = new GroupedSC3SynthInfo(this.server, "DrumMachine", new String[]{"kick","snare", "tom", "hihat"}, 
							new float[]{(float)NoteType.noteArray[NoteType.NoteIndex.C4.ordinal()],
							            (float)NoteType.noteArray[NoteType.NoteIndex.CSharp4.ordinal()],
							            (float)NoteType.noteArray[NoteType.NoteIndex.D4.ordinal()],
							            (float)NoteType.noteArray[NoteType.NoteIndex.DSharp4.ordinal()]},
							"Simple DrumMachine", new ControlDesc[]{kickGate, snareGate, tomGate, hihatGate}, true);
			synthInfoMap.put(info, null);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*GraphElem snare =  UGen.ar("-", UGen.ar("SinOsc", UGen.ir(40.0f)), UGen.ar("WhiteNoise", UGen.ir(0.5f)));
		GraphElem snareEnv = 
		GraphElem snareOut =*/ 
		
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
		
		/*// TestSynth1
        Control ck = Control.kr( new String[] {"freq","gate"}, new float[] { 0.0f, 0.0f});
        synthDef = new SynthDef( "SimpleSineSynth", UGen.ar( "Out", UGen.ir( 0 ), UGen.ar("Pan2",UGen.ar("*", UGen.ar("*",ck.getChannel(1), UGen.ar( "SinOsc", ck.getChannel(0))), UGen.ir(0.5f)))));
        ControlDesc ckd = ck.getDesc(0);
        ControlDesc ckd1 = ck.getDesc(1);
		synthInfo = new SC3SynthInfo(this.server, synthDef, synthDef.getName(), "A simple Sine-Synth", new ControlDesc[]{ckd, ckd1});
		synthInfoMap.put(synthInfo, synthDef);
		
		// TestSynth2
        Control ck2 = Control.kr( new String[] {"freq","gate", "filter_freq"}, new float[] { 0.0f, 0.0f, 20000.0f});
        synthDef = new SynthDef( "PolySawSynth", UGen.ar( "Out", UGen.ir( 0 ), UGen.ar("Pan2", UGen.ar("*",UGen.ar("*", ck2.getChannel(1), UGen.ar("RLPF", UGen.ar("Saw", ck2.getChannel(0)), ck2.getChannel(2)) ), UGen.ir(0.5f)))));
        ControlDesc ckd2 = ck2.getDesc(0);
        ControlDesc ckd3 = ck2.getDesc(1);
        ControlDesc ckd4 = ck2.getDesc(2);
		synthInfo = new PolyphonicSC3SynthInfo(this.server, synthDef.getName(), "A polyphonic Saw-Synth", new ControlDesc[]{ckd2, ckd3, ckd4}, 8);
		synthInfoMap.put(synthInfo, synthDef);
		*/
		this.createAndRegisterDrumMachine();
		this.createAndRegisterFunkLead();
		this.createAndRegisterStringMachine();
		this.createAndRegisterPiano();
		
		
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
		
		//synthInfoMap.put(synthInfo, synthDef);
		
		for(SynthDef sd: synthInfoMap.values()) {
			if(sd != null) {
				try {
					sd.send(this.server);
					System.out.println("Sent SynthDef "+sd.getName());
				} catch (IOException e) {
					System.err.println("Error sending SynthDef!");
					e.printStackTrace();
				}
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
			if(!si.isHidden()) {
				synthList.add(si);
			}
		}
		
		return synthList;
	}
	
	@Override
	public Synth instantiateSynth(SynthInfo synthInfo) {
		SynthDef synthDef = synthInfoMap.get(synthInfo);
		Synth synth = null;
		System.out.println("[StaticSynthLoader] loading synth: "+synthInfo);
		if(synthDef != null) {
			try {
				synth = Synth.head(this.group, synthInfo.getName());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(synth);
		}
 		
		return synth; 
	}
}
