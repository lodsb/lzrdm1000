package Synth;

import java.io.IOException;

import de.sciss.jcollider.ControlDesc;
import de.sciss.jcollider.Server;
import de.sciss.jcollider.Synth;
import de.sciss.jcollider.SynthDef;
import Control.ControlServer;

public class PolyphonicSC3SynthInfo extends SynthInfo {
	private Server server = null;
	private SynthDef sdef = null;
	private int polyphony = 0;

	public PolyphonicSC3SynthInfo(Server scServer, String name, String description, ControlDesc[] controlParameters, int polyphony) {
		super(name, description, controlParameters);
		this.server = scServer;
		//this.sdef = sdef;
		this.polyphony = polyphony;
	}
	
	public int getPolyphony() {
		return this.polyphony;
	}
	
	private Synth[] synths;
	
	@Override
	public SynthInstance createNewInstance(ControlServer cs) {
		synths = new Synth[this.polyphony];
		
		System.out.println("[StaticSynthLoader] loading synth: "+this);
			try {
				for(int i = 0; i < synths.length; i++) {
					synths[i] = Synth.head(this.server.asTarget(), this.getName());
					System.out.println("New synth as polyphonic.... "+i);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(synths);
			
		return new PolyphonicSynthInstance(cs, this, synths);
	}

}
