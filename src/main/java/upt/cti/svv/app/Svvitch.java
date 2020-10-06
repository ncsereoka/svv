package upt.cti.svv.app;

import upt.cti.svv.gui.DefaultSvvitchInterface;
import upt.cti.svv.gui.SvvitchInterface;
import upt.cti.svv.http.HttpWebServer;

import java.util.Optional;

/**
 * Main application class - contains the server configuration (or info), the GUI and the server itself
 */
public final class Svvitch {
	private final ServerInfo info;
	private final SvvitchInterface gui;
	private final HttpWebServer server;

	/**
	 * Main constructor
	 *
	 * @param silently run silently (i.e. without GUI)
	 */
	public Svvitch(boolean silently) {
		this(silently, new DefaultServerInfo(silently));
	}

	public Svvitch(boolean silently, ServerInfo info) {
		this(silently, info, new HttpWebServer(info));
	}

	public Svvitch(boolean silently, ServerInfo info, HttpWebServer server) {
		this(info, silently ? null : new DefaultSvvitchInterface(server), server);
	}

	public Svvitch(ServerInfo info, SvvitchInterface ui, HttpWebServer server) {
		this.info = info;
		this.gui = ui;
		this.server = server;
	}

	public static void main(String[] args) {
		new Svvitch(Configuration.runSilently()).start();
	}

	public void start() {
		Optional.ofNullable(gui)
				.ifPresent(SvvitchInterface::display);
		if (this.info.getStatus().equals(ApplicationStatus.RUNNING)) {
			this.server.start();
		}
	}

	public ServerInfo getInfo() {
		return info;
	}

	public SvvitchInterface getGui() {
		return gui;
	}

	public HttpWebServer getServer() {
		return server;
	}
}