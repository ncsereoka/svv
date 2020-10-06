package upt.cti.svv.gui.listener;

import upt.cti.svv.server.ServerStatus;
import upt.cti.svv.server.ServerSettings;
import upt.cti.svv.gui.SvvitchInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MaintenanceCheckboxListener implements ActionListener {
	private final SvvitchInterface gui;
	private final ServerSettings settings;

	public MaintenanceCheckboxListener(SvvitchInterface gui, ServerSettings settings) {
		this.gui = gui;
		this.settings = settings;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if (settings.getStatus().equals(ServerStatus.MAINTENANCE)) {
			settings.updateToRunning(settings.getPort());
		} else {
			settings.updateToMaintenance(settings.getPort());
		}
		gui.update(settings);
	}
}
