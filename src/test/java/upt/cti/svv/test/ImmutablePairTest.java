package upt.cti.svv.test;

import org.junit.Test;
import upt.cti.svv.util.ImmutablePair;

import static org.junit.Assert.assertEquals;

public class ImmutablePairTest {
	@Test
	public void happy() {
		ImmutablePair<String, Integer> pair = ImmutablePair.of("test", 1);
		assertEquals("test", pair.getKey());
		assertEquals(Integer.valueOf(1), pair.getValue());
	}
}
