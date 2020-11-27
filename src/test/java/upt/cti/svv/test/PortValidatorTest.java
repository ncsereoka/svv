package upt.cti.svv.test;

import org.junit.Test;
import upt.cti.svv.server.exception.ConfigurationException;
import upt.cti.svv.util.PortValidator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;

public class PortValidatorTest {
	@Test
	public void valid_ports() {
		assertEquals(1025, PortValidator.read("1025"));
		assertEquals(12333, PortValidator.read("12333"));
		assertEquals(65535, PortValidator.read("65535"));
	}

	@Test(expected = ConfigurationException.class)
	public void invalid_number() {
		PortValidator.read("1025a");
	}

	@Test(expected = ConfigurationException.class)
	public void invalid_port_lower() {
		PortValidator.read("80");
	}

	@Test(expected = ConfigurationException.class)
	public void invalid_port_higher() {
		PortValidator.read("90000");
	}

	@Test(expected = InvocationTargetException.class)
	public void instantiation_fails() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
		Constructor<PortValidator> c = PortValidator.class.getDeclaredConstructor();
		c.setAccessible(true);
		c.newInstance();
	}
}
