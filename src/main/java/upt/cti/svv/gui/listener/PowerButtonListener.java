package upt.cti.svv.gui.listener;

import upt.cti.svv.app.ApplicationStatus;
import upt.cti.svv.app.Svvitch;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class PowerButtonListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if (Svvitch.getState().equals(ApplicationStatus.STOPPED)) {
			Svvitch.setState(ApplicationStatus.RUNNING);
		} else {
			Svvitch.setState(ApplicationStatus.STOPPED);
		}
		Svvitch.updateGui();
	}
}
