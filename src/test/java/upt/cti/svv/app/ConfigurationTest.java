package upt.cti.svv.app;

import org.junit.Before;
import org.junit.Test;
import upt.cti.svv.server.ResourceUtil;
import upt.cti.svv.server.ServerConfiguration;
import upt.cti.svv.server.ServerStatus;
import upt.cti.svv.server.exception.ConfigurationException;

import java.io.File;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class ConfigurationTest {
	private File configFile;
	private ServerConfiguration config;

	@Before
	public void setConfiguration() throws URISyntaxException {
		configFile = ResourceUtil.loadResourceFile(getClass(), "config.properties");
		config = new Configuration(
				configFile,
				false,
				0,
				"",
				ResourceUtil.loadResourceFile(getClass(), "www"),
				ResourceUtil.loadResourceFile(getClass(), "maintenance"));
	}

	@Test
	public void storing_works() throws URISyntaxException {
		assertEquals(0, config.getPort());
		config.setPort(3000);
		config.store();

		final ServerConfiguration savedConfig = ConfigurationLoader.fromFile(configFile.getAbsolutePath());
		assertEquals(3000, savedConfig.getPort());
	}

	@Test
	public void loud_starts_stopped() {
		assertEquals(ServerStatus.STOPPED, config.getStatus());
	}

	@Test
	public void update_to_running() {
		config.updateToRunning(3000);
		assertEquals(ServerStatus.RUNNING, config.getStatus());
		assertEquals(3000, config.getPort());
	}

	@Test
	public void update_to_maintenance() {
		config.updateToMaintenance(3000);
		assertEquals(ServerStatus.MAINTENANCE, config.getStatus());
		assertEquals(3000, config.getPort());

	}

	@Test(expected = ConfigurationException.class)
	public void loader_fails_for_nonexistent_config_file() {
		ConfigurationLoader.fromFile("gobblegook");
	}
}
