package upt.cti.svv.app;

import java.io.InputStream;
import java.util.Properties;

public final class Configuration {
	private static final boolean runSilently;
	private static final int defaultPort;
	private static final String defaultAddress;

	static {
		boolean silent;
		int port;
		try (InputStream in = Svvitch.class.getClassLoader().getResourceAsStream("application.properties");) {
			Properties prop = new Properties();
			if (in != null) {
				prop.load(in);
			}
			final String guiString = prop.getProperty("runSilently");
			silent = guiString != null && !guiString.equals("");

			final String guiPort = prop.getProperty("defaultPort");
			port = Integer.parseInt(guiPort);

		} catch (Exception e) {
			silent = false;
			port = 3000;
		}
		runSilently = silent;
		defaultPort = port;

		defaultAddress = "127.0.0.1";
	}

	public static boolean runSilently() {
		return runSilently;
	}

	public static int defaultPort() {
		return defaultPort;
	}

	public static String getDefaultAddress() {
		return defaultAddress;
	}

	private Configuration() {
		throw new UnsupportedOperationException();
	}
}
