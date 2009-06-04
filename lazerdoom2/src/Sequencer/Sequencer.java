package Sequencer;

import java.util.LinkedList;
import java.util.concurrent.*;

import Control.ControlServer;

public class Sequencer implements SequenceInterface {

	private SequenceInterface mainSequence;
	private ControlServer controlServer;
	private boolean isRunning  = false;
	
	public Sequencer(SequenceInterface mainSequence, ControlServer controlServer) {
		this.mainSequence = mainSequence;
		this.controlServer= controlServer;
	}
	
	@Override
	public SequenceInterface deepCopy() {
		return new Sequencer(this.mainSequence.deepCopy(), this.controlServer);
	}

	@Override
	public boolean eval(long tick) {
		isRunning = mainSequence.eval(tick);
		controlServer.flushMessages();
		return isRunning;
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public void reset() {
		this.mainSequence.reset();
	}

	@Override
	public long size() {
		return mainSequence.size();
	}

}
