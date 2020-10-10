package upt.cti.svv;

import org.junit.Test;
import upt.cti.svv.server.DefaultServerSettings;
import upt.cti.svv.server.HttpWebServer;
import upt.cti.svv.server.ServerStatus;
import upt.cti.svv.server.exception.InternalServerErrorException;

import static org.junit.Assert.fail;

public class HttpWebServerTest {
	@Test
	public void request_to_root_returns_index_file() {
		fail("Not yet implemented");
	}

	@Test
	public void request_to_non_root_returns_file() {
		fail("Not yet implemented");
	}

	@Test
	public void unknown_location_returns_not_found() {
		fail("Not yet implemented");
	}

	@Test
	public void successful_request_while_in_maintenance() {
		fail("Not yet implemented");
	}

	@Test(expected = InternalServerErrorException.class)
	public void server_listens_on_invalid_port() {
		final DefaultServerSettings settings = new DefaultServerSettings(
				true,
				ServerStatus.RUNNING,
				"0.0.0.0",
				22,
				null,
				null);
		final HttpWebServer server = new HttpWebServer(settings);
		server.start();
	}
}
