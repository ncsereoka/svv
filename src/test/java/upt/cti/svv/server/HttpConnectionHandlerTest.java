package upt.cti.svv.server;

import org.junit.Test;
import org.mockito.stubbing.Answer;
import upt.cti.svv.app.Configuration;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HttpConnectionHandlerTest {
	@Test
	public void test() throws URISyntaxException, IOException {
		final ServerConfiguration configuration = new Configuration(
				null,
				true,
				0,
				"",
				ResourceUtil.loadResourceFile(getClass(), "www"),
				ResourceUtil.loadResourceFile(getClass(), "maintenance"));

		final Socket client = mock(Socket.class);
		when(client.getOutputStream()).thenReturn(new ByteArrayOutputStream());
		when(client.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[]{}));

		final ServerSocket serverSocket = mock(ServerSocket.class);
		when(serverSocket.accept()).thenAnswer((Answer<Socket>) invocation -> {
			configuration.updateToStopped();
			return client;
		});

		new HttpConnectionHandler(serverSocket, configuration).run();
	}
}
