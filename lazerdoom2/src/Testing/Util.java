package Testing;

import Control.Types.BaseType;
import java.util.Random;
import Control.Types.DoubleType;
import Sequencer.EventPointsSequence;

public class Util {
	public static int hashSequenceEntry(long tick, BaseType value) {
		return (int) (7*(new Long(tick).hashCode()))+(31*(new Float(value.getFloatValue()).hashCode()));
	}
	
	public static final byte[] intToByteArray(int value) {
		return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
	}
	
	public static void fillEventSequenceWithRandomEvents(EventPointsSequence<DoubleType> eventSequence, int numberOfEvents, long maxTick) {
		Random randomSeed = new Random();
		
		for(int i = 0; i < numberOfEvents; i++) {
			long tick = Math.abs(randomSeed.nextLong()) % maxTick;
			double value = Math.abs(randomSeed.nextDouble());
			
			eventSequence.insert(new DoubleType(value), tick);
		}
	}
}
