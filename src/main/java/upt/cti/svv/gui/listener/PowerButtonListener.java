package upt.cti.svv.gui.listener;

import upt.cti.svv.app.ApplicationState;
import upt.cti.svv.app.Svvitch;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class PowerButtonListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if (Svvitch.getState().equals(ApplicationState.STOPPED)) {
			Svvitch.setState(ApplicationState.RUNNING);
		} else {
			Svvitch.setState(ApplicationState.STOPPED);
		}
		Svvitch.updateGui();
	}
}
