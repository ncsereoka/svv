package upt.cti.svv.gui.listener;

import upt.cti.svv.app.ApplicationStatus;
import upt.cti.svv.app.Svvitch;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MaintenanceCheckboxListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if (Svvitch.getState().equals(ApplicationStatus.RUNNING)) {
			Svvitch.setState(ApplicationStatus.MAINTENANCE);
		} else {
			Svvitch.setState(ApplicationStatus.RUNNING);
		}
		Svvitch.updateGui();
	}
}
