package upt.cti.svv.util;

import upt.cti.svv.http.ServerErrorException;

import java.io.Closeable;

public final class ExceptionWrapper {
	public static void close(Closeable closeable, String messageOnError) {
		try {
			closeable.close();
		} catch (Exception e) {
			throw new ServerErrorException(messageOnError);
		}
	}

	public static void close(Closeable closeable) {
		close(closeable, "Internal server error");
	}
}
