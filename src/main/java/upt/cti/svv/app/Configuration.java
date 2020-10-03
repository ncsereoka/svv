package upt.cti.svv.app;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public final class Configuration {
	private static final boolean silent;
	private static final int port;
	private static final String address;
	private static final File webRoot;
	private static final File maintenance;

	static {
		String configSilent = null;
		String configPort = null;
		String configWebRoot = null;
		String configMaintenance = null;

		try (InputStream in = Svvitch.class.getClassLoader().getResourceAsStream("application.properties");) {
			Properties prop = new Properties();
			if (in != null) {
				prop.load(in);
			}
			configSilent = prop.getProperty("silent");
			configPort = prop.getProperty("port");
			configWebRoot = prop.getProperty("webroot");
			configMaintenance = prop.getProperty("maintenance");
		} catch (Exception ignored) {
		}

		silent = configSilent != null && !configSilent.isBlank();
		port = getPort(configPort);
		address = "127.0.0.1";

		final File workingDirFile = new File(Paths.get(System.getProperty("user.dir")).toUri());
		webRoot = getWebRootDir(configWebRoot, workingDirFile);
		maintenance = getMaintenanceDir(configMaintenance, workingDirFile);
	}

	private static int getPort(String configPort) {
		int parsedPort = 3000;
		try {
			if (configPort != null) {
				parsedPort = Integer.parseInt(configPort);
			}

			if (parsedPort < 1025 || 65536 < parsedPort) {
				parsedPort = 3000;
			}
		} catch (NumberFormatException ignored) {
		}
		return parsedPort;
	}

	private static File getWebRootDir(String configWebRoot, File working) {
		File tempFile;
		try {
			if (configWebRoot == null) {
				tempFile = working;
			} else {
				Path path = Paths.get(configWebRoot);
				tempFile = new File(path.toUri());
			}
		} catch (Exception e) {
			tempFile = working;
		}
		return tempFile;
	}

	private static File getMaintenanceDir(String configMaintenance, File working) {
		File tempFile;
		try {
			if (configMaintenance == null) {
				tempFile = working;
			} else {
				Path path = Paths.get(configMaintenance);
				tempFile = new File(path.toUri());
			}
		} catch (Exception e) {
			tempFile = working;
		}
		return tempFile;
	}

	public static boolean runSilently() {
		return silent;
	}

	public static int defaultPort() {
		return port;
	}

	public static String defaultAddress() {
		return address;
	}

	public static File defaultWebRootDir() {
		return webRoot;
	}

	public static File defaultMaintenanceDir() {
		return maintenance;
	}

	private Configuration() {
		throw new UnsupportedOperationException();
	}
}
