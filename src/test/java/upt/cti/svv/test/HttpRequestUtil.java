package upt.cti.svv.test;

import upt.cti.svv.server.HttpMethod;
import upt.cti.svv.server.HttpRequest;
import upt.cti.svv.server.HttpVersion;

/**
 * Utility class for tests
 */
final class HttpRequestUtil {
	static String newBasicGet(String url) {
		return new HttpRequest(HttpMethod.GET, url, HttpVersion.HTTP_1_1.name, null).asIncomingRequest();
	}

	private HttpRequestUtil() {
		throw new UnsupportedOperationException();
	}
}
