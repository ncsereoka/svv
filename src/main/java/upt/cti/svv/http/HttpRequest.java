package upt.cti.svv.http;

import upt.cti.svv.util.ImmutablePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
	private final HttpMethod method;
	private final String url;
	private final String httpVersion;
	private final Map<String, String> headers;

	public static HttpRequest from(BufferedReader reader) {
		return new HttpRequest(reader);
	}

	private HttpRequest(BufferedReader reader) {
		final HttpRequestFirstLine firstLine = parseFirstLineElements(reader);
		this.method = firstLine.method;
		this.url = firstLine.url;
		this.httpVersion = firstLine.httpVersion;
		this.headers = parseHeaders(reader);
	}

	public HttpMethod getMethod() {
		return method;
	}

	public String getUrl() {
		return url;
	}

	public String getHttpVersion() {
		return httpVersion;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder()
				.append("HTTP Request\n")
				.append("============")
				.append("\nMethod: ").append(this.method.name())
				.append("\nURL: ").append(this.url)
				.append("\nHTTP version: ").append(this.httpVersion)
				.append("\nHeaders:");
		for (String key : this.headers.keySet()) {
			builder.append(String.format("\n%s: %s", key, this.headers.get(key)));
		}

		return builder.toString();
	}

	private Map<String, String> parseHeaders(BufferedReader reader) {
		try {
			final Map<String, String> map = new HashMap<>();
			String requestLine;
			while ((requestLine = reader.readLine()) != null && !requestLine.equals("\r\n\r\n") && !requestLine.equals("")) {
				ImmutablePair<String, String> header = parseHeader(requestLine);
				map.put(header.getKey(), header.getValue());
			}

			return map;
		} catch (IOException e) {
			throw new InvalidRequestException("Error reading request headers");
		}
	}

	private ImmutablePair<String, String> parseHeader(String requestLine) {
		final String[] lineElements = requestLine.split(":", 2);
		if (lineElements.length != 2) {
			throw new InvalidRequestException("Unexpected header format");
		}

		final String key = lineElements[0].strip();
		if (key == null || key.equals("")) {
			throw new InvalidRequestException("Empty header");
		}

		final String value = lineElements[1].strip();
		if (value == null || value.equals("")) {
			throw new InvalidRequestException("Unspecified header value");
		}

		return ImmutablePair.of(key, value);
	}

	private HttpRequestFirstLine parseFirstLineElements(BufferedReader reader) {
		try {
			final String[] lineElements = reader.readLine().split(" ");
			if (lineElements.length != 3) {
				throw new InvalidRequestException("First line of the request has invalid format.");
			}

			final HttpMethod method = parseMethod(lineElements[0]);
			final String url = lineElements[1];
			final String version = lineElements[2];

			return new HttpRequestFirstLine(method, url, version);

		} catch (IOException e) {
			throw new ServerErrorException("Error reading request line.");
		}
	}

	private HttpMethod parseMethod(String string) {
		try {
			return HttpMethod.valueOf(string);
		} catch (IllegalArgumentException e) {
			throw new InvalidRequestException("Method not allowed.");
		}
	}

	private BufferedReader getSocketReader(Socket socket) {
		try {
			return new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			throw new ServerErrorException("Error creating socket reader.");
		}
	}

	private static class HttpRequestFirstLine {
		private final HttpMethod method;
		private final String url;
		private final String httpVersion;

		private HttpRequestFirstLine(HttpMethod method, String url, String httpVersion) {
			this.method = method;
			this.url = url;
			this.httpVersion = httpVersion;
		}
	}
}
