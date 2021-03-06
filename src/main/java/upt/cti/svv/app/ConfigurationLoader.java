package upt.cti.svv.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import upt.cti.svv.server.HttpConnection;
import upt.cti.svv.server.ServerConfiguration;
import upt.cti.svv.server.exception.NonexistingConfigurationException;
import upt.cti.svv.util.FileLoader;
import upt.cti.svv.util.PortValidator;
import upt.cti.svv.util.ValidatedResult;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public final class ConfigurationLoader {
	private static final Logger log = LoggerFactory.getLogger(HttpConnection.class);

	private static final File DEFAULT_CONFIG_FILE = new File("config.properties");

	public static ServerConfiguration fromFile(String configFilePath) {
		log.info("Reading configuration from '{}'...", configFilePath);
		return ValidatedResult.of(new File(configFilePath))
				.withCondition(File::isFile)
				.map(ConfigurationLoader::fromFile)
				.onFailThrow(() ->
						new NonexistingConfigurationException(String.format("File '%s' does not exist.", configFilePath)));
	}

	public static ServerConfiguration defaultConfiguration() {
		if (DEFAULT_CONFIG_FILE.exists()) {
			log.info("Using default configuration file...");
			return fromFile(DEFAULT_CONFIG_FILE);
		} else {
			log.info("Default configuration file not found. Using internal default configuration...");
			return new Configuration(
					DEFAULT_CONFIG_FILE,
					false,
					3000,
					"127.0.0.1",
					FileLoader.loadDirectory("www"),
					FileLoader.loadDirectory("maintenance"));
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
		try (FileInputStream in = new FileInputStream(configurationFile)) {
			Properties prop = new Properties();
			prop.load(in);
			return prop;
		} catch (Exception e) {
			throw new NonexistingConfigurationException("Error loading configuration from file.");
		}
	}

	private ConfigurationLoader() {
		throw new UnsupportedOperationException();
	}
}
