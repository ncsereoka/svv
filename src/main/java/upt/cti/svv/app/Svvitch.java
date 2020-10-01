package upt.cti.svv.app;

import upt.cti.svv.gui.SvvitchInterface;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Svvitch {
	private static final SvvitchInterface gui = new SvvitchInterface();
	private static ApplicationState state = ApplicationState.STOPPED;

	public static void main(String[] args) {
		if (runSilently()) {
//			new HttpWebServer().listen();
		} else {
			gui.display();
		}
	}

	public static void updateGui() {
		gui.update(state);
	}

	public static void setState(ApplicationState newState) {
		state = newState;
	}

	public static ApplicationState getState() {
		return state;
	}

	private static boolean runSilently() {
		try (InputStream in = Svvitch.class.getClassLoader().getResourceAsStream("application.properties");) {
			Properties prop = new Properties();
			if (in != null) {
				prop.load(in);
			}
			final String guiString = prop.getProperty("silent");
			return guiString != null && !guiString.equals("");
		} catch (IOException e) {
			return false;
		}
	}
}