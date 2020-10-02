package upt.cti.svv.app;

import upt.cti.svv.gui.DefaultSvvitchInterface;

public final class Svvitch {
	private final DefaultServerInfo info;
	private final DefaultSvvitchInterface gui;

	public static void main(String[] args) {
		new Svvitch();
	}

	private Svvitch() {
		final boolean silently = Configuration.runSilently();
		this.info = new DefaultServerInfo(silently);
		if (silently) {
			this.gui = null;
		} else {
			this.gui = new DefaultSvvitchInterface(this.info);
			this.gui.display();
		}

//		new HttpWebServer().listen();
	}
}