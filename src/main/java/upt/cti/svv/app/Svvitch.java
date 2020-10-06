package upt.cti.svv.app;

import upt.cti.svv.gui.DefaultSvvitchInterface;
import upt.cti.svv.gui.SvvitchInterface;
import upt.cti.svv.server.ServerStatus;
import upt.cti.svv.server.DefaultServerSettings;
import upt.cti.svv.server.HttpWebServer;
import upt.cti.svv.server.ServerSettings;

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
	 *
	 * @param silently run silently (i.e. without GUI)
	 */
	public Svvitch(boolean silently) {
		this(silently, new DefaultServerSettings(silently));
	}

	public Svvitch(boolean silently, ServerSettings settings) {
		this(silently, settings, new HttpWebServer(settings));
	}

	public Svvitch(boolean silently, ServerSettings settings, HttpWebServer server) {
		this(settings, silently ? null : new DefaultSvvitchInterface(server), server);
	}

	public Svvitch(ServerSettings settings, SvvitchInterface ui, HttpWebServer server) {
		this.settings = settings;
		this.gui = ui;
		this.server = server;
	}

	public static void main(String[] args) {
		new Svvitch(Configuration.runSilently()).start();
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