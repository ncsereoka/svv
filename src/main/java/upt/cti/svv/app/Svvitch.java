package upt.cti.svv.app;

import upt.cti.svv.gui.SvvitchInterface;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Svvitch {
	private static final SvvitchInterface gui = new SvvitchInterface();
	private static ApplicationStatus state = ApplicationStatus.STOPPED;

	public static void main(String[] args) {
		if (Configuration.runSilently()) {
			ServerInfo info = new ServerInfo(ApplicationStatus.RUNNING, Configuration.getDefaultAddress(), Configuration.defaultPort());
//			new HttpWebServer().listen();
		} else {
			ServerInfo info = new ServerInfo(ApplicationStatus.STOPPED, Configuration.getDefaultAddress(), Configuration.defaultPort());
			gui.display();
		}
	}

	public static void updateGui() {
		gui.update(state);
	}

	public static void setState(ApplicationStatus newState) {
		state = newState;
	}

	public static ApplicationStatus getState() {
		return state;
	}


}