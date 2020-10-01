package upt.cti.svv.gui;

import upt.cti.svv.app.ApplicationStatus;

import javax.swing.*;

public final class SvvitchInterface {
	private static final String APPLICATION_NAME = "Svvitch";
	private final JFrame frame;

	public SvvitchInterface() {
		this.frame = new InterfaceBuilder().frame();
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
	}

	private void updateMaintenanceCheckbox(boolean on) {
		final JCheckBox checkBox = (JCheckBox) ComponentMap.get(ComponentMap.Identifier.MAINTENANCE_CHECKBOX);
		checkBox.setEnabled(on);
		if (!on) {
			checkBox.setSelected(false);
		}
	}

	private void updateToMaintenance() {
		updateFrameTitle(ApplicationStatus.MAINTENANCE);
	}

	private void updateToRunning() {
		updateFrameTitle(ApplicationStatus.RUNNING);
		updatePowerButtonText("Stop server");
		updateMaintenanceCheckbox(true);
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
