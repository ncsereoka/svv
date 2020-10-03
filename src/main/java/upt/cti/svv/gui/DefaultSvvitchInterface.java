package upt.cti.svv.gui;

import upt.cti.svv.app.ApplicationStatus;
import upt.cti.svv.app.ServerInfo;
import upt.cti.svv.gui.listener.*;

import javax.swing.*;
import javax.swing.text.PlainDocument;

public class DefaultSvvitchInterface implements SvvitchInterface {
	private static final String APPLICATION_NAME = "Svvitch";
	private final JFrame frame;

	public DefaultSvvitchInterface(ServerInfo info) {
		this.frame = InterfaceBuilder.newInterface();
		setUpListeners(info);
		updateToStopped();
	}

	@Override
	public void display() {
		this.frame.setVisible(true);
	}

	@Override
	public void update(ServerInfo info) {
		switch (info.getStatus()) {
			case RUNNING:
				updateToRunning(info);
				break;
			case MAINTENANCE:
				updateToMaintenance(info);
				break;
			case STOPPED:
				updateToStopped();
				break;
			default:
				break;
		}
	}

	private void setUpListeners(ServerInfo info) {
		((JButton) ComponentMap.get(ComponentMap.Identifier.POWER_BUTTON))
				.addActionListener(new PowerButtonListener(this, info));
		((JCheckBox) ComponentMap.get(ComponentMap.Identifier.MAINTENANCE_CHECKBOX))
				.addActionListener(new MaintenanceCheckboxListener(this, info));

		setUpPortRelated(info);
		setUpWebRootRelated(info);
		setUpMaintenanceRelated(info);
	}

	private void setUpPortRelated(ServerInfo info) {
		final JTextField portField = (JTextField) ComponentMap.get(ComponentMap.Identifier.PORT_FIELD);
		portField.setText(info.getPortForGui());
		portField.getDocument().addDocumentListener(new PortListener(info, portField));
		((PlainDocument) portField.getDocument()).setDocumentFilter(new PortNumberFilter());
	}

	private void setUpMaintenanceRelated(ServerInfo info) {
		final JLabel selected = ((JLabel) ComponentMap.get(ComponentMap.Identifier.MAINTENANCE_DIR));
		selected.setText(info.getMaintenanceDirForGui());
		final JLabel valid = ((JLabel) ComponentMap.get(ComponentMap.Identifier.MAINTENANCE_DIR_VALID));
		((JButton) ComponentMap.get(ComponentMap.Identifier.MAINTENANCE_DIR_BUTTON))
				.addActionListener(new MaintenanceDirectoryListener(selected, valid, info));
	}

	private void setUpWebRootRelated(ServerInfo info) {
		final JLabel selected = ((JLabel) ComponentMap.get(ComponentMap.Identifier.WEBROOT_DIR));
		selected.setText(info.getWebRootDirForGui());
		final JLabel valid = ((JLabel) ComponentMap.get(ComponentMap.Identifier.WEBROOT_DIR_VALID));
		((JButton) ComponentMap.get(ComponentMap.Identifier.WEBROOT_DIR_BUTTON))
				.addActionListener(new WebRootDirectoryListener(selected, valid, info));
	}

	private void updateToStopped() {
		updateFrameTitle(ApplicationStatus.STOPPED);
		updatePowerButtonText("Start server");
		updateMaintenanceCheckbox(false);
		updateServerInfoStatus("not running");
		updateServerInfoAddress("not running");
		updateServerInfoPort("not running");
		updateConfigurationPort(true);
		updateConfigurationWeb(true);
		updateConfigurationMaintenance(true);
	}

	private void updateToMaintenance(ServerInfo info) {
		updateFrameTitle(ApplicationStatus.MAINTENANCE);
		updateServerInfoStatus("maintenance");
		updateServerInfoAddress(info.getAddress());
		updateServerInfoPort(info.getPortForGui());
		updateConfigurationPort(false);
		updateConfigurationWeb(true);
		updateConfigurationMaintenance(false);
	}

	private void updateToRunning(ServerInfo info) {
		updateFrameTitle(ApplicationStatus.RUNNING);
		updatePowerButtonText("Stop server");
		updateMaintenanceCheckbox(true);
		updateServerInfoStatus("running");
		updateServerInfoAddress(info.getAddress());
		updateServerInfoPort(info.getPortForGui());
		updateConfigurationPort(false);
		updateConfigurationWeb(false);
		updateConfigurationMaintenance(true);
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

	private void updateFrameTitle(ApplicationStatus state) {
		final String title = String.format("%s - [%s]", APPLICATION_NAME, state.name());
		this.frame.setTitle(title);
	}

	private void updatePowerButtonText(String text) {
		final JButton button = (JButton) ComponentMap.get(ComponentMap.Identifier.POWER_BUTTON);
		button.setText(text);
	}
}
