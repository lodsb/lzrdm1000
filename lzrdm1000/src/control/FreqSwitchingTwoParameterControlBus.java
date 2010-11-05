package control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import sequencer.BaseSequence;

import control.types.BaseType;

import de.sciss.jcollider.ControlDesc;
import de.sciss.jcollider.Synth;
import edu.uci.ics.jung.graph.util.Pair;

public class FreqSwitchingTwoParameterControlBus<T extends BaseType> extends ParameterControlBus<T> {

	private List<ControlDesc> freqs;
	protected List<ControlDesc> gates;
	private Synth[] synths;
	//private float[] switchingFreqs;
	
	private HashMap<Float, Pair<Object>> voices = new HashMap<Float, Pair<Object>>();
	int polyphony = 0;
	boolean trigOnly = false;
	
	@Override
	public ControlDesc getControlDesc() {
		return gates.get(0);
	}
	
	public FreqSwitchingTwoParameterControlBus(ControlServer server, ArrayList<ControlDesc> freqs, ArrayList<ControlDesc> gates, float switchingFreqs[], Synth[] synths, boolean trigOnly) {
		this.freqs = freqs;
		this.gates = gates;
		this.synths = synths;
		this.polyphony = this.synths.length-1;
		this.server = server;
		
		int i = 0;
		// quick hack- pair
		for(float freq: switchingFreqs) {
			System.out.println("s "+synths[i]);
			System.out.println("g "+gates.get(i));
			voices.put(freq, new Pair<Object>(synths[i], gates.get(i)));
			i++;
		}
		
		this.trigOnly = trigOnly;
	}
	
	@Override
	public void setValue(BaseSequence si, long tick, T baseType) {
		float gateValue = baseType.getFloatValue2();
		float freq = baseType.getFloatValue();
		Pair<Object> currentPair = null;
		
		if((currentPair = voices.get(freq)) != null) {
			if(!trigOnly) {
				server.appendMessage(si, tick, ((Synth)currentPair.getFirst()).setMsg(((ControlDesc)currentPair.getSecond()).getName(), baseType.getFloatValue()));
			}
			server.appendMessage(si, tick, ((Synth)currentPair.getFirst()).setMsg(((ControlDesc)currentPair.getSecond()).getName(), baseType.getFloatValue2()));
		}
		
	}
	
	@Override
	public void setDefaultValue(BaseSequence si, long tick) {
		for(Entry<Float, Pair<Object>> entry: voices.entrySet()) {
			Pair<Object> currentPair = entry.getValue();
			if(!trigOnly) { 
				server.appendMessage(si, tick, ((Synth)currentPair.getFirst()).setMsg(((ControlDesc)currentPair.getSecond()).getName(), ((ControlDesc)currentPair.getSecond()).getDefaultValue()));
			}
			server.appendMessage(si, tick, ((Synth)currentPair.getFirst()).setMsg(((ControlDesc)currentPair.getSecond()).getName(), ((ControlDesc)currentPair.getSecond()).getDefaultValue()));
		}
	}
}
