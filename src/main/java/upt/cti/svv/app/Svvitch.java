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
		this(info, silently ? null : new DefaultSvvitchInterface(info));
	}

	public Svvitch(ServerInfo info, SvvitchInterface ui) {
		this.info = info;
		this.gui = ui;
		this.server = new HttpWebServer();
	}

	public static void main(String[] args) {
		new Svvitch(Configuration.runSilently()).start();
	}

	public void start() {
		Optional
				.ofNullable(gui)
				.ifPresent(SvvitchInterface::display);
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