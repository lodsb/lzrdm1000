package synth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import session.SessionHandler;

import control.ControlServer;
import control.FreqSwitchingTwoParameterControlBus;
import control.MultiplexParameterControlBus;
import control.ParameterControlBus;
import control.PolyphonicTwoParameterControlBus;
import control.TwoParameterControlBus;
import control.types.*;


import lazerdoom.LzrDmObjectInterface;
import de.sciss.jcollider.ControlDesc;
import de.sciss.jcollider.Synth;

public class GroupedSynthInstance extends SynthInstance implements LzrDmObjectInterface {
	
	private SynthInfo info;
	private ParameterControlBus[] controlBusses;
	
	private Synth[] synths = null;
	
	public void free() {
		if(synths != null) {
			for(Synth s: synths) {
				try {
					s.free();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public GroupedSynthInstance(ControlServer server, SynthInfo info, float[] switchingFreqs, Synth[] synths, boolean triggerOnly) {
		this.synths = synths;
		//SessionHandler.getInstance().registerObject(this);
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
