package Synth;

import java.util.ArrayList;
import java.util.LinkedList;

import lazerdoom.LzrDmObjectInterface;
import de.sciss.jcollider.ControlDesc;
import de.sciss.jcollider.Synth;
import Control.ControlServer;
import Control.FreqSwitchingTwoParameterControlBus;
import Control.MultiplexParameterControlBus;
import Control.ParameterControlBus;
import Control.PolyphonicTwoParameterControlBus;
import Control.TwoParameterControlBus;
import Control.Types.*;

public class GroupedSynthInstance extends SynthInstance {
	
	private SynthInfo info;
	private ParameterControlBus[] controlBusses;
	
	public GroupedSynthInstance(ControlServer server, SynthInfo info, float[] switchingFreqs, Synth[] synths, boolean triggerOnly) {
		this.info = info;
		
		// quick hack... should check for different parameter-types
		//int numberOfControlBusses = 1;//info.getControlParameters().length;
		controlBusses = new ParameterControlBus[1];
		
		// check whether the synth provides a freq and gate control-bus
		ControlDesc freq = null;
		ControlDesc gate = null;
		
		int busIdx = 0;
		
		//LinkedList<ParameterControlBus> groupBusses = new LinkedList<ParameterControlBus>();
		
		System.out.println("LOAD SYNTH");
		
		ArrayList<ControlDesc> gates = new ArrayList<ControlDesc>();
		ArrayList<ControlDesc> freqs = new ArrayList<ControlDesc>();
		
		for(ControlDesc desc: info.getControlParameters()) {
			if(desc.getName().equals("freq")) {
				freqs.add(desc);
				System.out.println("found freq");
			}
			
			if(desc.getName().equals("gate")) {
				gates.add(desc);
				gate = desc;
				System.out.println("found gate");
			}
		}
		
		if(gates.size() > 0) {
			//numberOfControlBusses--;
			busIdx++;
			System.out.println(gates);
			controlBusses[0] = new FreqSwitchingTwoParameterControlBus<DoubleType>(server, freqs, gates, switchingFreqs, synths, triggerOnly);
		}
		
/*		controlBusses = new ParameterControlBus[numberOfControlBusses];
		
		int i = 0;
		for(ParameterControlBus groupBus: groupBusses) {
			controlBusses[i] = groupBus;
		}
		System.out.println(groupBusses);
	*/	
		/*for(ControlDesc desc: info.getControlParameters()) {
			if(!(desc.getName().equals("freq") || desc.getName().equals("gate"))) {
				controlBusses[busIdx] = new MultiplexParameterControlBus<DoubleType>(server, desc, synths);
				System.out.println(desc.getName());
			}
		}*/
		
		for(ParameterControlBus controlBus: controlBusses) {
			System.out.println("---"+controlBus);
		}
		System.out.println("DR "+controlBusses);
	}
	
	public SynthInfo getSynthInfo() {
		return this.info;
	}
	
	public ParameterControlBus[] getControlBusses() {
		return this.controlBusses;
	}
}
