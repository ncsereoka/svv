package upt.cti.svv.app;

import upt.cti.svv.gui.DefaultSvvitchInterface;
import upt.cti.svv.gui.SvvitchInterface;
import upt.cti.svv.http.HttpWebServer;

import java.util.Optional;

public final class Svvitch {
	private final ServerInfo info;
	private final SvvitchInterface gui;
	private final HttpWebServer server;

	public static void main(String[] args) {
		new Svvitch().start();
	}

	private Svvitch() {
		final boolean silently = Configuration.runSilently();
		this.info = new DefaultServerInfo(silently);
		this.gui = silently ? null : new DefaultSvvitchInterface(this.info);
		this.server = new HttpWebServer();
	}

	private void start() {
		Optional
				.ofNullable(gui)
				.ifPresent(SvvitchInterface::display);
	}
}