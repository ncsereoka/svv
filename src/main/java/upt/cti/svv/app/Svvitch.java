package upt.cti.svv.app;

import upt.cti.svv.gui.SvvitchInterface;

public final class Svvitch {
	private final ServerInfo info;
	private final SvvitchInterface gui;

	public static void main(String[] args) {
		new Svvitch();
	}

	private Svvitch() {
		final boolean silently = Configuration.runSilently();
		this.info = new ServerInfo(silently);
		if (silently) {
			this.gui = null;
		} else {
			this.gui = new SvvitchInterface(this.info);
			this.gui.display();
		}

//		new HttpWebServer().listen();
	}
}