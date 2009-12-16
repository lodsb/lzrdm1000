package sequencer;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.trolltech.qt.core.QObject;

public class HighResolutionPollingClock implements ClockInterface, Runnable {
	private SequencerInterface sequencer;
	private ScheduledThreadPoolExecutor stpe;
	
	public HighResolutionPollingClock(long interval, SequencerInterface sequencer) {
		this.sequencer = sequencer;
		
		this.interval = interval;
		tmpInt = this.interval;
		
		this.stpe = new ScheduledThreadPoolExecutor(1);
	}
	
	private static long nanoInMs = 1000000;
		
	public static double beatMeasureToMs(int beat, int measure, double bpm) {
		return (240000*((double)beat/(double)measure)/bpm);
	}
	
	public long oneBarToTicks(long nanoInterval, double bpm) {
		return (long) ((beatMeasureToMs(1, 1, bpm)*nanoInterval)/nanoInMs);
	} 
	
	long interval;
	long tmpInt;
	long currentNanos = 0;
	long startNanos = System.nanoTime();
	long deltaAcc = 0;
	long latency = 0;
	
	long latencyAcc = 0;
	long latencyAvg = 0;
	long msRuns = 1000;
	long runs = 1;
	long cycles = 0;
	double deltaAvg = 0;
	long oldTime;
	
	@Override
	public void run() {
			startNanos = System.nanoTime();
			deltaAcc = (startNanos - currentNanos);
			cycles = 0;
			while(deltaAcc < tmpInt) {
				startNanos = System.nanoTime();
				deltaAcc = (startNanos - currentNanos);
				cycles++;
			}
			currentNanos = startNanos;
			latency = deltaAcc-this.interval;
			
			latencyAcc = latencyAcc+ latency;
			
			runs++;
			
			if((runs%msRuns)== 0) {
				//System.out.println(System.currentTimeMillis()-oldTime);
				oldTime = System.currentTimeMillis();
				
				latencyAvg = latencyAcc/msRuns;
				if(latencyAvg + this.interval > this.interval) {
					tmpInt = this.interval-latencyAvg;
				} else {
					tmpInt = this.interval;
				}
				latencyAcc = 0;
			}
			sequencer.processTick(runs);
	}

	@Override
	public void setInterval(long interval) {
		this.interval = interval;
		this.stpe.remove(this);
		this.stpe.scheduleAtFixedRate(this, interval, interval-(2/9*interval), TimeUnit.NANOSECONDS);
	}

	@Override
	public void setSequencer(SequencerInterface sequencer) {
		this.sequencer = sequencer;
	}
	
	@Override
	public void start() {
		stpe.scheduleAtFixedRate(this, 1000000000, interval-(2/9*interval), TimeUnit.NANOSECONDS);
	}

}
