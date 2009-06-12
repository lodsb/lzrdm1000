package Sequencer;

import java.nio.FloatBuffer;
import java.util.Iterator;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import de.gulden.application.jjack.JJack;
import de.gulden.framework.jjack.JJackAudioChannel;
import de.gulden.framework.jjack.JJackAudioEvent;
import de.gulden.framework.jjack.JJackClient;
import de.gulden.framework.jjack.JJackSystem;

public class JackClock extends JJackClient implements ClockInterface {
	private Sequencer sequencer;
	private int samplerate;
	private double samplesToInterval;
	
	public JackClock(long interval, Sequencer sequencer) {
		this.sequencer = sequencer;
		this.interval = interval;
		this.samplerate = JJackSystem.getSampleRate();
		this.samplesToInterval = ((double) interval)/(((double)nanoInS)*(double)(1.0/((double) this.samplerate)));
		
	}
	
	private static long nanoInMs = 1000000;
	private static long nanoInS = nanoInMs*1000;
		
	public static double beatMeasureToMs(int beat, int measure, double bpm) {
		return (240000*((double)beat/(double)measure)/bpm);
	}
	
	public long oneBarToTicks(long nanoInterval, double bpm) {
		return (long) ((beatMeasureToMs(1, 1, bpm)*nanoInterval)/nanoInMs);
	} 
	
	private boolean isStarted = false;
	
	long interval;
	long runs = 1;

	@Override
	public void setInterval(long interval) {
		this.interval = interval;
	}

	@Override
	public void setSequencer(Sequencer sequencer) {
		this.sequencer = sequencer;
	}
	
	@Override
	public void start() {
		this.isStarted = true;
	}

	@Override
	public void process(JJackAudioEvent e) {
		if(this.isStarted) {
			Iterator it = e.getChannels().iterator();
			JJackAudioChannel ch = (JJackAudioChannel) it.next();
			FloatBuffer in = ch.getPortBuffer(INPUT);
			
		}
		
	}

}
