package upt.cti.svv.server;

import org.junit.Test;
import upt.cti.svv.app.Configuration;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URISyntaxException;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class HttpWebServerTest {
	@Test
	public void test() throws URISyntaxException, IOException {
		final ServerConfiguration config = new Configuration(
				null,
				false,
				0,
				"",
				ResourceUtil.loadResourceFile(getClass(), "www"),
				ResourceUtil.loadResourceFile(getClass(), "maintenance"));

		final ServerSocket socket = mock(ServerSocket.class);
		doNothing().when(socket).close();

		final HttpWebServer server = mock(HttpWebServer.class);
		when(server.getConfig()).thenReturn(config);
		when(server.bindToPort(anyInt())).thenReturn(socket);
		doCallRealMethod().when(server).start();
		doCallRealMethod().when(server).restart();
		doCallRealMethod().when(server).stop();

		server.start();
		server.restart();
		server.stop();
	}
}
