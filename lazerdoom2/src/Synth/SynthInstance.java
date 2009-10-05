package Synth;

import java.util.LinkedList;

import Session.SessionHandler;

import lazerdoom.LzrDmObjectInterface;
import de.sciss.jcollider.ControlDesc;
import de.sciss.jcollider.Synth;
import Control.ControlServer;
import Control.ParameterControlBus;
import Control.TwoParameterControlBus;
import Control.Types.*;

public class SynthInstance implements LzrDmObjectInterface {
	
	private SynthInfo info;
	private ParameterControlBus[] controlBusses;
	
	public SynthInstance() {}
	
	public SynthInstance(ControlServer server, SynthInfo info, Synth synth) {
		//SessionHandler.getInstance().registerObject(this);
		this.info = info;
		
		int numberOfControlBusses = info.getControlParameters().length;
		
		// check whether the synth provides a freq and gate control-bus
		ControlDesc freq = null;
		ControlDesc gate = null;
		
		int busIdx = 0;
		
		LinkedList<ParameterControlBus> groupBusses = new LinkedList<ParameterControlBus>();
		
		System.out.println("LOAD SYNTH");
		
		for(ControlDesc desc: info.getControlParameters()) {
			if(desc.getName().equals("freq")) {
				freq = desc;
				System.out.println("found freq");
			}
			
			if(desc.getName().equals("gate")) {
				gate = desc;
				System.out.println("found gate");
			}
		}
		
		if(freq != null && gate != null) {
			numberOfControlBusses--;
			busIdx++;
			groupBusses.add(new TwoParameterControlBus<DoubleType>(server, freq, gate, synth));
		}
		
		controlBusses = new ParameterControlBus[numberOfControlBusses];
		
		int i = 0;
		for(ParameterControlBus groupBus: groupBusses) {
			controlBusses[i] = groupBus;
		}
		System.out.println(groupBusses);
		
		for(ControlDesc desc: info.getControlParameters()) {
			if(!(desc.getName().equals("freq") || desc.getName().equals("gate"))) {
				controlBusses[busIdx] = new ParameterControlBus<DoubleType>(server, desc, synth);
				System.out.println(desc.getName());
			}
		}
		for(ParameterControlBus controlBus: controlBusses) {
			System.out.println("---"+controlBus);
		}
	}
	
	public SynthInfo getSynthInfo() {
		return this.info;
	}
	
	public ParameterControlBus[] getControlBusses() {
		return this.controlBusses;
	}
}
