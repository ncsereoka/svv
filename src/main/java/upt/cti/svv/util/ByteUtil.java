package upt.cti.svv.util;

public final class ByteUtil {
	/**
	 * Merge two byte arrays in order
	 *
	 * @param first  the first byte array
	 * @param second the second byte array
	 * @return a merged array with the contents of the first one appearing at the beginning
	 */
	public static byte[] merged(byte[] first, byte[] second) {
		final byte[] result = new byte[first.length + second.length];
		System.arraycopy(first, 0, result, 0, first.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	private ByteUtil() {
		throw new UnsupportedOperationException();
	}
}
