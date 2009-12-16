package control;

import java.util.HashMap;
import java.util.Map.Entry;

import sequencer.BaseSequence;

import control.types.BaseType;

import de.sciss.jcollider.ControlDesc;
import de.sciss.jcollider.Synth;

public class PolyphonicTwoParameterControlBus<T extends BaseType> extends ParameterControlBus<T> {

	private ControlDesc desc1;
	protected ControlDesc desc2;
	private Synth[] synths;
	
	private HashMap<Float, Synth> voices = new HashMap<Float, Synth>();
	int currentSynth = 0;
	int polyphony = 0;
	
	public PolyphonicTwoParameterControlBus(ControlServer server, ControlDesc desc, ControlDesc desc2, Synth[] synths) {
		super(server, desc, null);
		this.desc2 = desc2;
		this.desc1 = desc;
		this.synths = synths;
		this.polyphony = this.synths.length-1;
	}
	
	@Override
	public void setValue(BaseSequence si, long tick, T baseType) {
		float gateValue = baseType.getFloatValue2();
		float freq = baseType.getFloatValue();
		if(gateValue == 0.0) { // gate off
			Synth gos = voices.get(freq);
			if(gos != null) {
				server.appendMessage(si, tick, gos.setMsg(this.desc1.getName(), freq));
				server.appendMessage(si, tick, gos.setMsg(this.desc2.getName(), gateValue));
				
				voices.remove(freq);
			}
		} else {
			Synth voice = this.synths[currentSynth];
			server.appendMessage(si, tick, voice.setMsg(this.desc1.getName(), freq));
			server.appendMessage(si, tick, voice.setMsg(this.desc2.getName(), gateValue));
			currentSynth = (currentSynth+1)%this.polyphony;
			this.voices.put(freq, voice);
		}
	}
	
	@Override
	public void setDefaultValue(BaseSequence si, long tick) {
		for(Entry<Float, Synth> entry: voices.entrySet()) {
			Synth currentSynth = entry.getValue();
			server.appendMessage(si, tick, currentSynth.setMsg(this.desc1.getName(), this.controlDescription.getDefaultValue()));
			server.appendMessage(si, tick, currentSynth.setMsg(this.desc2.getName(), this.desc2.getDefaultValue()));
		}
		
		this.voices.clear();
	}
}
