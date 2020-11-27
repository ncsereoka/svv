package upt.cti.svv.gui.listener;

import upt.cti.svv.server.ServerStatus;
import upt.cti.svv.server.ServerConfiguration;
import upt.cti.svv.gui.SvvitchInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MaintenanceCheckboxListener implements ActionListener {
	private final SvvitchInterface gui;
	private final ServerConfiguration config;

	public MaintenanceCheckboxListener(SvvitchInterface gui, ServerConfiguration config) {
		this.gui = gui;
		this.config = config;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if (config.getStatus().equals(ServerStatus.MAINTENANCE)) {
			config.updateToRunning(config.getPort());
		} else {
			config.updateToMaintenance(config.getPort());
		}
		gui.update(config);
	}
}
