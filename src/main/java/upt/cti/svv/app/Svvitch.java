package upt.cti.svv.app;

import upt.cti.svv.gui.DefaultSvvitchInterface;
import upt.cti.svv.gui.SvvitchInterface;
import upt.cti.svv.server.DefaultServerSettings;
import upt.cti.svv.server.HttpWebServer;
import upt.cti.svv.server.ServerSettings;
import upt.cti.svv.server.ServerStatus;

import java.util.Optional;

/**
 * Main application class - contains the server configuration (or settings), the GUI and the server itself
 */
public final class Svvitch {
	private final ServerSettings settings;
	private final SvvitchInterface gui;
	private final HttpWebServer server;

	/**
	 * Main constructor
	 */
	public Svvitch(Configuration configuration) {
		this(new DefaultServerSettings(configuration));
	}

	public Svvitch(ServerSettings settings) {
		this(settings, new HttpWebServer(settings));
	}

	public Svvitch(ServerSettings settings, HttpWebServer server) {
		this(settings, settings.isSilent() ? null : new DefaultSvvitchInterface(server), server);
	}

	public Svvitch(ServerSettings settings, SvvitchInterface ui, HttpWebServer server) {
		this.settings = settings;
		this.gui = ui;
		this.server = server;
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			new Svvitch(Configuration.defaultConfiguration()).start();
		} else if (args.length == 1) {
			new Svvitch(Configuration.fromFile(args[0])).start();
		} else {
			System.err.println("Usage: java -jar <jar_file> [configuration file]");
			System.exit(1);
		}
	}

	public void start() {
		Optional.ofNullable(gui)
				.ifPresent(SvvitchInterface::display);
		if (this.settings.getStatus().equals(ServerStatus.RUNNING)) {
			this.server.start();
		}
	}

	public ServerSettings getSettings() {
		return settings;
	}

	public SvvitchInterface getGui() {
		return gui;
	}

	public HttpWebServer getServer() {
		return server;
	}
}