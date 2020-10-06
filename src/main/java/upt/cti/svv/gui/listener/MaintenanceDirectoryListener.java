package upt.cti.svv.gui.listener;

import upt.cti.svv.server.ServerSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MaintenanceDirectoryListener implements ActionListener, ValidDirectoryChooser {
	private final JLabel folderLabel;
	private final JLabel validLabel;
	private final ServerSettings settings;

	public MaintenanceDirectoryListener(JLabel folderLabel, JLabel validLabel, ServerSettings settings) {
		this.folderLabel = folderLabel;
		this.validLabel = validLabel;
		this.settings = settings;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			final File selectedFile = fileChooser.getSelectedFile();
			folderLabel.setText(selectedFile.getAbsolutePath());
			settings.setMaintenanceDir(selectedFile);
		}
		validLabel.setText(validityString());
	}


	@Override
	public String validityString() {
		return "invalid";
	}
}
