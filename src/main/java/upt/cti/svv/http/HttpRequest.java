package upt.cti.svv.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Map;

public class HttpRequest {
	private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

	private final HttpMethod method;
	private final String url;
	private final String httpVersion;

	private Map<String, String> headers;

	HttpRequest(Socket socket) {
		BufferedReader reader = getSocketReader(socket);
		HttpRequestFirstLine firstLine = parseFirstLineElements(reader);
		this.method = firstLine.method;
		this.url = firstLine.url;
		this.httpVersion = firstLine.httpVersion;

	}

	private HttpRequestFirstLine parseFirstLineElements(BufferedReader reader) {
		try {
			String[] lineElements = reader.readLine().split(" ");
			if (lineElements.length > 3) {
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

		public HttpMethod getMethod() {
			return method;
		}

		public String getUrl() {
			return url;
		}

		public String getHttpVersion() {
			return httpVersion;
		}
	}
}
