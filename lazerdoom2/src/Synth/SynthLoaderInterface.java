package Synth;


import java.util.List;

import de.sciss.jcollider.Synth;

public interface SynthLoaderInterface {
	public List<SynthInfo> getAvailableSynths();
	public Synth instantiateSynth(SynthInfo synthInfo);
	public void init();
}
