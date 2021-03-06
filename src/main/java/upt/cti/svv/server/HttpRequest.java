package upt.cti.svv.server;

import java.util.Map;
import java.util.Optional;

public class HttpRequest {
	private final HttpMethod method;
	private final String url;
	private final String httpVersion;
	private final Map<String, String> headers;

	public HttpRequest(HttpMethod method, String url, String httpVersion, Map<String, String> headers) {
		this.method = method;
		this.url = url;
		this.httpVersion = httpVersion;
		this.headers = headers;
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
				.append("HTTP Request")
				.append("\n============")
				.append("\nMethod: ").append(this.method.name())
				.append("\nURL: ").append(this.url)
				.append("\nHTTP version: ").append(this.httpVersion)
				.append("\nHeaders:");
		Optional.ofNullable(this.headers)
				.map(Map::entrySet)
				.ifPresent(entries ->
						entries.stream()
								.map(entry -> String.format("\n\t%s: %s", entry.getKey(), entry.getValue()))
								.forEach(builder::append));

		return builder.toString();
	}

	public String asIncomingRequest() {
		final StringBuilder builder = new StringBuilder()
				.append(this.method)
				.append(" ")
				.append(this.url)
				.append(" ")
				.append(this.httpVersion)
				.append("\n");
		Optional.ofNullable(this.headers)
				.map(Map::entrySet)
				.ifPresent(entries ->
						entries.stream()
								.map(entry -> String.format("%s: %s\n", entry.getKey(), entry.getValue()))
								.forEach(builder::append));
		return builder.toString();
	}
}
