package upt.cti.svv.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import upt.cti.svv.server.HttpConnection;
import upt.cti.svv.server.exception.ConfigurationException;
import upt.cti.svv.util.FileLoader;
import upt.cti.svv.util.PortValidator;
import upt.cti.svv.util.ValidatedResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.function.Supplier;

public final class Configuration {
	private static final Logger log = LoggerFactory.getLogger(HttpConnection.class);

	private final File configurationFile;
	private final boolean silent;
	private final int port;
	private final String address;
	private final File webRoot;
	private final File maintenance;

	public Configuration(
			File configurationFile,
			boolean silent,
			int port,
			String address,
			File webRoot,
			File maintenance
	) {
		this.configurationFile = configurationFile;
		this.silent = silent;
		this.port = port;
		this.address = address;
		this.webRoot = webRoot;
		this.maintenance = maintenance;
	}

	public boolean runSilently() {
		return silent;
	}

	public int defaultPort() {
		return port;
	}

	public String defaultAddress() {
		return address;
	}

	public File defaultWebRootDir() {
		return webRoot;
	}

	public File defaultMaintenanceDir() {
		return maintenance;
	}

	public void save(Properties properties) {
		try (FileOutputStream out = new FileOutputStream(configurationFile)) {
			properties.store(out, null);
		} catch (IOException e) {
			throw new ConfigurationException("Error saving configuration.");
		}
	}

	public static Configuration fromFile(String configFilePath) {
		log.info("Reading configuration from '{}'...", configFilePath);
		return checked(() -> ValidatedResult.of(new File(configFilePath))
				.withCondition(File::isFile)
				.map(Configuration::fromFile)
				.onFailThrow(() ->
						new ConfigurationException(String.format("File '%s' does not exist.", configFilePath))));
	}

	public static Configuration defaultConfiguration() {
		log.info("Using default configuration...");
		return checked(() -> new Configuration(
				new File("config.properties"),
				true,
				3000,
				"127.0.0.1",
				FileLoader.loadDirectory("webroot"),
				FileLoader.loadDirectory("maintenance")));
	}

	private static Configuration checked(Supplier<? extends Configuration> supplier) {
		try {
			return supplier.get();
		} catch (ConfigurationException e) {
			log.error("Configuration error: {}", e.getMessage());
			System.exit(2);
			return null;
		}
	}

	private static Configuration fromFile(File configurationFile) {
		Properties prop = loadProperties(configurationFile);
		return new Configuration(
				configurationFile,
				Boolean.parseBoolean(prop.getProperty("silent")),
				PortValidator.read(prop.getProperty("port")),
				"127.0.0.1",
				FileLoader.loadDirectory(prop.getProperty("webroot")),
				FileLoader.loadDirectory(prop.getProperty("maintenance")));
	}

	private static Properties loadProperties(File configurationFile) {
		Properties prop = new Properties();

		try (FileInputStream in = new FileInputStream(configurationFile)) {
			prop.load(in);
		} catch (Exception e) {
			throw new ConfigurationException("Error loading configuration from file.");
		}
		return prop;
	}
}
