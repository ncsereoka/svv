package upt.cti.svv.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import upt.cti.svv.gui.DefaultSvvitchInterface;
import upt.cti.svv.gui.SvvitchInterface;
import upt.cti.svv.server.HttpWebServer;
import upt.cti.svv.server.ServerConfiguration;
import upt.cti.svv.server.ServerStatus;
import upt.cti.svv.server.exception.NonexistingConfigurationException;

import java.util.Optional;

/**
 * Main application class - contains the server configuration (or settings), the GUI and the server itself
 */
public final class Svvitch {
	private static final Logger log = LoggerFactory.getLogger(Svvitch.class);

	private final ServerConfiguration settings;
	private final SvvitchInterface gui;
	private final HttpWebServer server;

	/**
	 * Main constructor
	 */
	public Svvitch(ServerConfiguration configuration) {
		this(configuration, new HttpWebServer(configuration));
	}

	public Svvitch(ServerConfiguration settings, HttpWebServer server) {
		this(settings, settings.isSilent() ? null : new DefaultSvvitchInterface(server), server);
	}

	public Svvitch(ServerConfiguration settings, SvvitchInterface ui, HttpWebServer server) {
		this.settings = settings;
		this.gui = ui;
		this.server = server;
	}

	public static void main(String[] args) {
		if (args.length > 1) {
			System.err.println("Usage: java -jar <jar_file> [configuration file]");
			System.exit(1);
		}
		new Svvitch(config(args)).start();
	}

	public void start() {
		Optional.ofNullable(gui).ifPresent(SvvitchInterface::display);
		if (this.settings.getStatus().equals(ServerStatus.RUNNING)) {
			this.server.start();
		}
	}

	private static ServerConfiguration config(String[] configFilePath) {
		try {
			final boolean useDefaultConfiguration = configFilePath.length == 0;
			return useDefaultConfiguration ? ConfigurationLoader.defaultConfiguration() :
					ConfigurationLoader.fromFile(configFilePath[0]);
		} catch (NonexistingConfigurationException e) {
			log.error("Configuration error: {}", e.getMessage());
			System.exit(2);
			return null;
		}
	}

	public ServerConfiguration getSettings() {
		return settings;
	}

	public SvvitchInterface getGui() {
		return gui;
	}

	public HttpWebServer getServer() {
		return server;
	}
}