package upt.cti.svv.server;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class HttpRequestTest {
	private HttpRequest request;

	@Before
	public void setRequest() {
		final Map<String, String> headers = new HashMap<>();
		headers.put("Custom-Header", "Custom-Value");
		request = new HttpRequest(HttpMethod.GET, "/", HttpVersion.HTTP_1_1.name, headers);
	}

	@Test
	public void accessors() {
		assertEquals(HttpMethod.GET, request.getMethod());
		assertEquals("/", request.getUrl());
		assertEquals(HttpVersion.HTTP_1_1.name, request.getHttpVersion());

		final Map<String, String> headers = new HashMap<>();
		headers.put("Custom-Header", "Custom-Value");
		assertEquals(headers, request.getHeaders());
	}

	@Test
	public void string_representation() {
		final String expected = "HTTP Request\n" +
				"============\n" +
				"Method: GET\n" +
				"URL: /\n" +
				"HTTP version: HTTP/1.1\n" +
				"Headers:\n" +
				"\tCustom-Header: Custom-Value";
		assertEquals(expected, request.toString());
	}

	@Test
	public void as_incoming_request() {
		final String expected = "GET / HTTP/1.1\n" +
				"Custom-Header: Custom-Value\n";
		assertEquals(expected, request.asIncomingRequest());
	}
}
