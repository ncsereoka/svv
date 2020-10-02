package upt.cti.svv.gui.listener;

import upt.cti.svv.app.ApplicationStatus;
import upt.cti.svv.app.ServerInfo;
import upt.cti.svv.gui.SvvitchInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MaintenanceCheckboxListener implements ActionListener {
	private final SvvitchInterface gui;
	private final ServerInfo info;

	public MaintenanceCheckboxListener(SvvitchInterface gui, ServerInfo info) {
		this.gui = gui;
		this.info = info;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if (info.getStatus().equals(ApplicationStatus.MAINTENANCE)) {
			info.updateToRunning(info.getPort());
		} else {
			info.updateToMaintenance(info.getPort());
		}
		gui.update(info);
	}
}
