package upt.cti.svv.gui.listener;

import upt.cti.svv.gui.SvvitchInterface;
import upt.cti.svv.server.ServerConfiguration;
import upt.cti.svv.server.ServerStatus;
import upt.cti.svv.util.PortValidator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PowerButtonListener implements ActionListener {
	private final SvvitchInterface gui;
	private final ServerConfiguration config;
	private final JFrame frame;

	public PowerButtonListener(SvvitchInterface gui, ServerConfiguration config, JFrame frame) {
		this.gui = gui;
		this.config = config;
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if (PortValidator.isValidPort(config.getPort())) {
			if (config.getStatus().equals(ServerStatus.STOPPED)) {
				config.updateToRunning(config.getPort());
			} else {
				config.updateToStopped();
			}
			gui.update(config);
		} else {
			JOptionPane.showMessageDialog(
					frame,
					"Invalid port. Please specify one between 1025 and 65535.",
					"ERROR",
					JOptionPane.WARNING_MESSAGE);
		}
	}
}