package lazerdoom;

public class UtilMath {
	public static int nearestPowerOfTwo(int v) {
		int ret = 0;
		
		v = Integer.signum(v)*v;
		
		int highestBitValue = Integer.highestOneBit(v);
		int lowerBoundBitValue = highestBitValue >> 1;
		int upperBoundBitValue = highestBitValue << 1;

		if(highestBitValue == v) {
			return v;
		}
		
		int distanceToUpperBoundVal = upperBoundBitValue - v;
		int distanceToLowerBoundVal = v - lowerBoundBitValue;
		int distanceToHighestBitVal = v - highestBitValue;
		
		if(distanceToUpperBoundVal > distanceToLowerBoundVal) {
			if(distanceToHighestBitVal > distanceToLowerBoundVal) {
				ret = lowerBoundBitValue;
			} else {
				ret = highestBitValue;
			}
		} else {
			if(distanceToHighestBitVal > distanceToUpperBoundVal) {
				ret = upperBoundBitValue;
			} else {
				ret = highestBitValue;
			}
		}
		
		return ret;
		
	}
}
