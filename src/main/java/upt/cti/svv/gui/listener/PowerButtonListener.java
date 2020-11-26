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
	private final ServerConfiguration settings;
	private final JFrame frame;

	public PowerButtonListener(SvvitchInterface gui, ServerConfiguration settings, JFrame frame) {
		this.gui = gui;
		this.settings = settings;
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if (PortValidator.isValidPort(settings.getPort())) {
			if (settings.getStatus().equals(ServerStatus.STOPPED)) {
				settings.updateToRunning(settings.getPort());
			} else {
				settings.updateToStopped();
			}
			gui.update(settings);
		} else {
			JOptionPane.showMessageDialog(
					frame,
					"Invalid port. Please specify one between 1025 and 65535.",
					"ERROR",
					JOptionPane.WARNING_MESSAGE);
		}
	}
}