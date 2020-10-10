package upt.cti.svv.server;

import upt.cti.svv.server.exception.InternalServerErrorException;
import upt.cti.svv.server.exception.InvalidRequestException;
import upt.cti.svv.util.ImmutablePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The request message consists of the following:
 * - a request line (e.g., GET /images/logo.png HTTP/1.1, which requests a resource called /images/logo.png from the server)
 * - request header fields (e.g., Accept-Language: en)
 * - an empty line
 * - an optional message body
 *
 * @see <a href="https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol#Message_format">Wikipedia</a>
 */
public final class HttpRequestParser {
	/**
	 * Parse input from a reader object to (hopefully) obtain an HttpRequest
	 *
	 * @param reader the input object
	 * @return a new HttpRequest
	 */
	public static HttpRequest from(BufferedReader reader) {
		final HttpRequestLine requestLine = parseRequestLineElements(reader);
		return new HttpRequest(
				requestLine.method,
				requestLine.url,
				requestLine.httpVersion,
				parseHeaders(reader)
		);
	}

	private static Map<String, String> parseHeaders(BufferedReader reader) {
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

	private static ImmutablePair<String, String> parseHeader(String requestLine) {
		final String[] lineElements = requestLine.split(":", 2);
		if (lineElements.length != 2) {
			throw new InvalidRequestException("Unexpected header format");
		}

		final String key = lineElements[0].strip();
		if (key == null || key.equals("") || key.contains(" ")) {
			throw new InvalidRequestException("Empty header");
		}

		final String value = lineElements[1].strip();
		if (value == null || value.equals("")) {
			throw new InvalidRequestException("Unspecified header value");
		}

		return ImmutablePair.of(key, value);
	}

	private static HttpRequestLine parseRequestLineElements(BufferedReader reader) {
		try {
			final String[] lineElements = reader.readLine().split(" ");
			if (lineElements.length != 3) {
				throw new InvalidRequestException("First line of the request has invalid format.");
			}

			final HttpMethod method = parseMethod(lineElements[0]);
			final String url = parseUrl(lineElements[1]);
			final String version = parseVersion(lineElements[2]);

			return new HttpRequestLine(method, url, version);

		} catch (IOException e) {
			throw new InternalServerErrorException("Error reading request line.");
		}
	}

	private static String parseVersion(String version) {
		if (!version.equals(HttpVersion.HTTP_1_1.getName())) {
			throw new InvalidRequestException("Invalid HTTP version");
		}
		return version;
	}

	private static String parseUrl(String url) {
		if (url.charAt(0) != '/') {
			throw new InvalidRequestException("Invalid request URL");
		}
		return url;
	}

	private static HttpMethod parseMethod(String string) {
		try {
			return HttpMethod.valueOf(string);
		} catch (IllegalArgumentException e) {
			throw new InvalidRequestException("Method not allowed.");
		}
	}

	private static class HttpRequestLine {
		private final HttpMethod method;
		private final String url;
		private final String httpVersion;

		private HttpRequestLine(HttpMethod method, String url, String httpVersion) {
			this.method = method;
			this.url = url;
			this.httpVersion = httpVersion;
		}
	}

	private HttpRequestParser() {
		throw new UnsupportedOperationException();
	}
}