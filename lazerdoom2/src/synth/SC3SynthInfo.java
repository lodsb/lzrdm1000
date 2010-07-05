package synth;

import java.io.IOException;

import control.ControlServer;

import de.sciss.jcollider.ControlDesc;
import de.sciss.jcollider.Server;
import de.sciss.jcollider.Synth;
import de.sciss.jcollider.SynthDef;

public class SC3SynthInfo extends SynthInfo {
	private Server server = null;
	private SynthDef sdef = null;

	public SC3SynthInfo(Server scServer, SynthDef sdef, String name, String description, ControlDesc[] controlParameters) {
		super(name, description, controlParameters);
		this.server = scServer;
		this.sdef = sdef;
	}
	
	@Override
	public SynthInstance createNewInstance(ControlServer cs) {
		Synth synth = null;
		System.out.println("[StaticSynthLoader] loading synth: "+this);
			try {
				synth = Synth.head(this.server.asTarget(), this.getName());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(synth);
			
		return new SynthInstance(cs, this, synth);
	}

}
