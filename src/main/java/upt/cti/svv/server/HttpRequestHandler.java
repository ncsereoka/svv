package upt.cti.svv.server;

import upt.cti.svv.util.ByteUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public final class HttpRequestHandler {
	private static final List<String> defaultPaths = List.of("index.html", "index.htm", "index.html");
	private static final byte[] RESPONSE_404 =
			("HTTP/1.1 404 Not Found\nContent-Type: text/html\nConnection: close\n\n" +
					"<html><body><h1>404 NOT FOUND</h1></body></html").getBytes();

	private final ServerConfiguration config;

	public HttpRequestHandler(ServerConfiguration config) {
		this.config = config;
	}

	public byte[] handle(HttpRequest request) throws IOException {
		final String url = process(request.getUrl());
		if (url.equals("/")) {
			return resolveRequestToRoot(getRequestedUrl(url));
		} else {
			return resolveCommonRequest(getRequestedUrl(url));
		}
	}

	private String process(String url) {
		return url.replace("%20", " ");
	}

	private byte[] resolveCommonRequest(Path requestedPath) throws IOException {
		if (requestedPath.toFile().exists()) {
			return buildOkResponse(requestedPath);
		} else {
			return RESPONSE_404;
		}
	}

	private byte[] resolveRequestToRoot(Path requestedPath) throws IOException {
		for (String defaultPathString : defaultPaths) {
			final Path indexPath = requestedPath.resolve(defaultPathString);
			if (indexPath.toFile().exists()) {
				return buildOkResponse(indexPath);
			}
		}
		return RESPONSE_404;
	}

	private byte[] buildOkResponse(Path path) throws IOException {
		final byte[] head = getOkResponseHeaderPart(path);
		final byte[] body = Files.readAllBytes(path);
		return ByteUtil.merged(head, body);
	}

	private byte[] getOkResponseHeaderPart(Path path) {
		final String contentType = getContentType(path.toString());
		final String headString = "HTTP/1.1 200 OK\nConnection: close\nContent-Type: " + contentType + "\n\n";
		return headString.getBytes();
	}

	private String getContentType(String path) {
		if (path.endsWith(".html") || path.endsWith(".htm")) {
			return "text/html";
		} else if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
			return "image/jpeg";
		} else if (path.endsWith(".txt")) {
			return "text/plain";
		} else {
			return "application/octet-stream";
		}
	}

	private Path getRequestedUrl(String url) {
		final String dir = config.getStatus().equals(ServerStatus.RUNNING) ? config.getWebRootDirForGui() :
				config.getMaintenanceDirForGui();
		return Paths.get(dir, url);
	}
}
