package upt.cti.svv.gui;

import upt.cti.svv.app.ApplicationStatus;
import upt.cti.svv.app.ServerInfo;

import javax.swing.*;

public final class SvvitchInterface {
	private static final String APPLICATION_NAME = "Svvitch";
	private final JFrame frame;
	private final ServerInfo info;

	public SvvitchInterface() {
		this.frame = InterfaceBuilder.newInterface();
		this.info = new ServerInfo();
		updateToStopped();
	}

	public void display() {
		this.frame.setVisible(true);
	}

	public void update(ApplicationStatus state) {
		switch (state) {
			case RUNNING:
				updateToRunning();
				break;
			case MAINTENANCE:
				updateToMaintenance();
				break;
			case STOPPED:
				updateToStopped();
				break;
			default:
				break;
		}
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

	private void updateToMaintenance() {
		updateFrameTitle(ApplicationStatus.MAINTENANCE);
		updateServerInfoStatus("maintenance");
		updateServerInfoAddress(info.getAddress());
		updateServerInfoPort(info.getPortForGui());
		updateConfigurationPort(false);
		updateConfigurationWeb(true);
		updateConfigurationMaintenance(false);
	}

	private void updateToRunning() {
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
		textField.setEnabled(on);
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
