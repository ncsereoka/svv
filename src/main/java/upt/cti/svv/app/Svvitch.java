package upt.cti.svv.app;

import upt.cti.svv.gui.SvvitchInterface;

public final class Svvitch {
	private static final SvvitchInterface gui = new SvvitchInterface();
	private static ApplicationState state = ApplicationState.STOPPED;

	public static void main(String[] args) {
		gui.display();
//		new HttpWebServer().listen();
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
}