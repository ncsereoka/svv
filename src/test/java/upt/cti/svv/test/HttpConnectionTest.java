package upt.cti.svv.test;

import org.junit.Before;
import org.junit.Test;
import upt.cti.svv.app.Configuration;
import upt.cti.svv.server.*;
import upt.cti.svv.util.ByteUtil;
import upt.cti.svv.util.MimeUtil;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HttpConnectionTest {
	private ServerConfiguration config;
	private Socket clientSocket;
	private HttpConnection httpConnection;
	private ByteArrayOutputStream outputStream;

	@Before
	public void setupConnection() throws IOException, URISyntaxException {
		config = new Configuration(
				null,
				false,
				0,
				"",
				ResourceUtil.loadResourceFile(getClass(), "www"),
				ResourceUtil.loadResourceFile(getClass(), "maintenance"));
		config.updateToRunning(0);

		outputStream = new ByteArrayOutputStream();
		clientSocket = mock(Socket.class);
		when(clientSocket.getOutputStream()).thenReturn(outputStream);
		httpConnection = new HttpConnection(clientSocket, new HttpRequestHandler(config));
	}

	@Test
	public void request_to_root_returns_index_file() throws IOException, URISyntaxException {
		when(clientSocket.getInputStream()).thenReturn(requestInputStream("/"));
		httpConnection.run();
		assertArrayEquals(buildOkResponse("www/index.html"), outputStream.toByteArray());
	}

	@Test
	public void request_to_non_root_returns_file() throws IOException, URISyntaxException {
		when(clientSocket.getInputStream()).thenReturn(requestInputStream("/a.html"));
		httpConnection.run();
		assertArrayEquals(buildOkResponse("www/a.html"), outputStream.toByteArray());
	}

	@Test
	public void unknown_location_returns_not_found() throws IOException {
		when(clientSocket.getInputStream()).thenReturn(requestInputStream("/z"));
		httpConnection.run();
		assertArrayEquals(HttpRequestHandler.RESPONSE_404, outputStream.toByteArray());
	}

	@Test
	public void successful_request_while_in_maintenance() throws IOException, URISyntaxException {
		config.updateToMaintenance(0);
		when(clientSocket.getInputStream()).thenReturn(requestInputStream("/"));
		httpConnection.run();
		assertArrayEquals(buildOkResponse("maintenance/index.html"), outputStream.toByteArray());
	}

	private InputStream requestInputStream(String url) {
		final String request = newBasicGet(url);
		return new BufferedInputStream(new ByteArrayInputStream(request.getBytes()));
	}

	private byte[] buildOkResponse(String filePath) throws IOException, URISyntaxException {
		final byte[] head = getOkResponseHeaderPart(filePath);
		final byte[] body = ResourceUtil.readResourceFile(getClass(), filePath);
		return ByteUtil.merged(head, body);
	}

	private byte[] getOkResponseHeaderPart(String path) {
		final String contentType = MimeUtil.getContentType(path);
		final String headString = "HTTP/1.1 200 OK\nConnection: close\nContent-Type: " + contentType + "\n\n";
		return headString.getBytes();
	}

	private String newBasicGet(String url) {
		return new HttpRequest(HttpMethod.GET, url, HttpVersion.HTTP_1_1.name, null).asIncomingRequest();
	}
}
