package Synth;

import java.io.IOException;

import de.sciss.jcollider.ControlDesc;
import de.sciss.jcollider.Server;
import de.sciss.jcollider.Synth;
import de.sciss.jcollider.SynthDef;
import Control.ControlServer;

public class GroupedSC3SynthInfo extends SynthInfo {
	private Server server = null;
	private String[] synthNames;
	private float[] switchFreqs;
	private int polyphony = 0;
	private boolean triggerOnly = false;

	public GroupedSC3SynthInfo(Server scServer, String name , String[] names, float switchFreqs[], String description, ControlDesc[] controlParameters, boolean triggerOnly) {
		super(name, description, controlParameters);
		this.server = scServer;
		//this.sdef = sdef;
		this.polyphony = names.length;
		this.synthNames = names;
		this.switchFreqs = switchFreqs;
		this.triggerOnly = triggerOnly;
	}
	
	public int getPolyphony() {
		return this.polyphony;
	}
	
	@Override
	public SynthInstance createNewInstance(ControlServer cs) {
		Synth[] synths = new Synth[this.polyphony];
		
		System.out.println("[StaticSynthLoader] loading synth: "+this);
			try {
				for(int i = 0; i < synths.length; i++) {
					synths[i] = Synth.head(this.server.asTarget(), synthNames[i]);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(synths);
			
		return new GroupedSynthInstance(cs, this, switchFreqs.clone(), synths, this.triggerOnly);
	}

}
