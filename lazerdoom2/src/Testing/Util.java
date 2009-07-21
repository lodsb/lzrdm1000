package Testing;

import Control.Types.BaseType;

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
}
