package upt.cti.svv.test;

import org.junit.Test;
import upt.cti.svv.util.ByteUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;

public class ByteUtilTest {
	@Test
	public void test() {
		final byte[] first = "first".getBytes();
		final byte[] second = "second".getBytes();
		final byte[] merged = ByteUtil.merged(first, second);
		assertEquals("firstsecond", new String(merged));
	}

	@Test(expected = InvocationTargetException.class)
	public void instantiation_fails() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
		Constructor<ByteUtil> c = ByteUtil.class.getDeclaredConstructor();
		c.setAccessible(true);
		c.newInstance();
	}
}
