package upt.cti.svv.util;

public final class MimeUtil {
	public static String getContentType(String path) {
		if (path.endsWith(".html") || path.endsWith(".htm")) {
			return "text/html";
		} else if (path.endsWith(".css")) {
			return "text/css";
		} else if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
			return "image/jpeg";
		} else if (path.endsWith(".txt")) {
			return "text/plain";
		} else {
			return "application/octet-stream";
		}
	}

	private MimeUtil() {
		throw new UnsupportedOperationException();
	}
}
