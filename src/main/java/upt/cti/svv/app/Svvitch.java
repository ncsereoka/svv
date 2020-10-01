package upt.cti.svv.app;

import upt.cti.svv.gui.SvvitchInterface;
import upt.cti.svv.http.HttpWebServer;

public final class Svvitch {

	public static void main(String[] args) {
		new SvvitchInterface().display();
//		new HttpWebServer().listen();
	}
}