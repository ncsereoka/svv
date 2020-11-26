package upt.cti.svv.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import upt.cti.svv.server.HttpConnection;
import upt.cti.svv.server.ServerConfiguration;
import upt.cti.svv.server.exception.ConfigurationException;
import upt.cti.svv.util.FileLoader;
import upt.cti.svv.util.PortValidator;
import upt.cti.svv.util.ValidatedResult;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.function.Supplier;

public final class ConfigurationLoader {
	private static final Logger log = LoggerFactory.getLogger(HttpConnection.class);

	public static ServerConfiguration fromFile(String configFilePath) {
		log.info("Reading configuration from '{}'...", configFilePath);
		return checked(() -> ValidatedResult.of(new File(configFilePath))
				.withCondition(File::isFile)
				.map(ConfigurationLoader::fromFile)
				.onFailThrow(() ->
						new ConfigurationException(String.format("File '%s' does not exist.", configFilePath))));
	}

	public static ServerConfiguration defaultConfiguration() {
		log.info("Using default configuration...");
		return checked(() -> new Configuration(
				new File("config.properties"),
				false,
				3000,
				"127.0.0.1",
				FileLoader.loadDirectory("www"),
				FileLoader.loadDirectory("maintenance")));
	}

	private static ServerConfiguration checked(Supplier<? extends ServerConfiguration> supplier) {
		try {
			return supplier.get();
		} catch (ConfigurationException e) {
			log.error("Configuration error: {}", e.getMessage());
			System.exit(2);
			return null;
		}
	}

	private static ServerConfiguration fromFile(File configurationFile) {
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

	private ConfigurationLoader() {
		throw new UnsupportedOperationException();
	}
}
