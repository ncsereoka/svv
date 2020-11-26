package upt.cti.svv.gui;

import upt.cti.svv.gui.listener.*;
import upt.cti.svv.server.HttpWebServer;
import upt.cti.svv.server.ServerConfiguration;
import upt.cti.svv.server.ServerStatus;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DefaultSvvitchInterface implements SvvitchInterface {
	private static final String APPLICATION_NAME = "Svvitch";
	private final JFrame frame;
	private final HttpWebServer server;

	public DefaultSvvitchInterface(HttpWebServer server) {
		this.server = server;
		this.frame = InterfaceBuilder.newInterface();
		setUpListeners(server.getSettings());
		updateToStopped();
	}

	@Override
	public void display() {
		this.frame.setVisible(true);
	}

	@Override
	public void update(ServerConfiguration settings) {
		switch (settings.getStatus()) {
			case RUNNING:
				updateToRunning(settings);
				break;
			case MAINTENANCE:
				updateToMaintenance(settings);
				break;
			case STOPPED:
				updateToStopped();
				break;
			default:
				break;
		}
	}

	private void setUpListeners(ServerConfiguration settings) {
		((JButton) ComponentMap.get(ComponentMap.Identifier.POWER_BUTTON))
				.addActionListener(new PowerButtonListener(this, settings, frame));
		((JCheckBox) ComponentMap.get(ComponentMap.Identifier.MAINTENANCE_CHECKBOX))
				.addActionListener(new MaintenanceCheckboxListener(this, settings));

		setUpPortRelated(settings);
		setUpWebRootRelated(settings);
		setUpMaintenanceRelated(settings);
		setUpWindowListener();
	}

	private void setUpWindowListener() {
		this.frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				updateToStopped();
				server.getSettings().store();
			}
		});
	}

	private void setUpPortRelated(ServerConfiguration settings) {
		final JTextField portField = (JTextField) ComponentMap.get(ComponentMap.Identifier.PORT_FIELD);
		portField.setText(settings.getPortForGui());
		portField.getDocument().addDocumentListener(new PortListener(settings, portField));
		((PlainDocument) portField.getDocument()).setDocumentFilter(new PortNumberFilter());
	}

	private void setUpMaintenanceRelated(ServerConfiguration settings) {
		final JLabel selected = ((JLabel) ComponentMap.get(ComponentMap.Identifier.MAINTENANCE_DIR));
		selected.setText(settings.getMaintenanceDirForGui());
		final JLabel valid = ((JLabel) ComponentMap.get(ComponentMap.Identifier.MAINTENANCE_DIR_VALID));
		((JButton) ComponentMap.get(ComponentMap.Identifier.MAINTENANCE_DIR_BUTTON))
				.addActionListener(new MaintenanceDirectoryListener(selected, valid, settings));
	}

	private void setUpWebRootRelated(ServerConfiguration settings) {
		final JLabel selected = ((JLabel) ComponentMap.get(ComponentMap.Identifier.WEBROOT_DIR));
		selected.setText(settings.getWebRootDirForGui());
		final JLabel valid = ((JLabel) ComponentMap.get(ComponentMap.Identifier.WEBROOT_DIR_VALID));
		((JButton) ComponentMap.get(ComponentMap.Identifier.WEBROOT_DIR_BUTTON))
				.addActionListener(new WebRootDirectoryListener(selected, valid, settings));
	}

	private void updateToStopped() {
		updateFrameTitle(ServerStatus.STOPPED);
		updatePowerButtonText("Start server");
		updateMaintenanceCheckbox(false);
		updateServerInfoStatus("not running");
		updateServerInfoAddress("not running");
		updateServerInfoPort("not running");
		updateConfigurationPort(true);
		updateConfigurationWeb(true);
		updateConfigurationMaintenance(true);
		this.server.stop();
	}

	private void updateToMaintenance(ServerConfiguration settings) {
		updateFrameTitle(ServerStatus.MAINTENANCE);
		updateServerInfoStatus("maintenance");
		updateServerInfoAddress(settings.address());
		updateServerInfoPort(settings.getPortForGui());
		updateConfigurationPort(false);
		updateConfigurationWeb(true);
		updateConfigurationMaintenance(false);
		this.server.restart();
	}

	private void updateToRunning(ServerConfiguration settings) {
		updateFrameTitle(ServerStatus.RUNNING);
		updatePowerButtonText("Stop server");
		updateMaintenanceCheckbox(true);
		updateServerInfoStatus("running");
		updateServerInfoAddress(settings.address());
		updateServerInfoPort(settings.getPortForGui());
		updateConfigurationPort(false);
		updateConfigurationWeb(false);
		updateConfigurationMaintenance(true);
		this.server.restart();
	}

	private void updateConfigurationMaintenance(boolean on) {
		JButton button = (JButton) ComponentMap.get(ComponentMap.Identifier.MAINTENANCE_DIR_BUTTON);
		button.setEnabled(on);
	}

	private void updateConfigurationWeb(boolean on) {
		JButton button = (JButton) ComponentMap.get(ComponentMap.Identifier.WEBROOT_DIR_BUTTON);
		button.setEnabled(on);
	}

	private void updateConfigurationPort(boolean on) {
		JTextField textField = (JTextField) ComponentMap.get(ComponentMap.Identifier.PORT_FIELD);
		textField.setEditable(on);
	}

	private void updateServerInfoPort(String text) {
		final JLabel jLabel = (JLabel) ComponentMap.get(ComponentMap.Identifier.SERVER_PORT);
		jLabel.setText(text);
	}

	private void updateServerInfoStatus(String text) {
		final JLabel jLabel = (JLabel) ComponentMap.get(ComponentMap.Identifier.SERVER_STATUS);
		jLabel.setText(text);
	}

	private void updateServerInfoAddress(String address) {
		final JLabel jLabel = (JLabel) ComponentMap.get(ComponentMap.Identifier.SERVER_ADDRESS);
		jLabel.setText(address);
	}

	private void updateMaintenanceCheckbox(boolean on) {
		final JCheckBox checkBox = (JCheckBox) ComponentMap.get(ComponentMap.Identifier.MAINTENANCE_CHECKBOX);
		checkBox.setEnabled(on);
		if (!on) {
			checkBox.setSelected(false);
		}
	}

	private void updateFrameTitle(ServerStatus status) {
		final String title = String.format("%s - [%s]", APPLICATION_NAME, status.name());
		this.frame.setTitle(title);
	}

	private void updatePowerButtonText(String text) {
		final JButton button = (JButton) ComponentMap.get(ComponentMap.Identifier.POWER_BUTTON);
		button.setText(text);
	}
}
