package Sequencer;

import java.util.LinkedList;
import java.util.concurrent.*;

public class Sequencer implements Runnable, SequenceInterface {
	
	private static long nanoInMs = 1000000;
	
	public static void main(String[] args) {
		Sequencer seq = new Sequencer(100000);
	}
	
	public static double beatMeasureToMs(int beat, int measure, double bpm) {
		return (240000*((double)beat/(double)measure)/bpm);
	}
	
	public long oneBarToTicks(long nanoInterval, double bpm) {
		return (long) ((beatMeasureToMs(1, 1, bpm)*nanoInterval)/nanoInMs);
	} 
			
	public Sequencer(long interval) {
		this.interval = interval;
		tmpInt = this.interval;
		
		ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(1);
		stpe.scheduleAtFixedRate(this, 1000000000, 100000, TimeUnit.NANOSECONDS);
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
				System.out.println(System.currentTimeMillis()-oldTime);
				oldTime = System.currentTimeMillis();
				
				latencyAvg = latencyAcc/msRuns;
				if(latencyAvg + this.interval > this.interval) {
					tmpInt = this.interval-latencyAvg;
				} else {
					tmpInt = this.interval;
				}
				latencyAcc = 0;
			}
			eval(runs);
	}

	@Override
	public boolean eval(long tick) {
		
		return false;
	}

	@Override
	public boolean isRunning() {
		return true;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long size() {
		// TODO Auto-generated method stub
		return 0;
	}

}
