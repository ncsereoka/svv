package upt.cti.svv.gui.listener;

import upt.cti.svv.app.ServerInfo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MaintenanceDirectoryListener implements ActionListener, ValidDirectoryChooser {
	private final JLabel folderLabel;
	private final JLabel validLabel;
	private final ServerInfo info;

	public MaintenanceDirectoryListener(JLabel folderLabel, JLabel validLabel, ServerInfo info) {
		this.folderLabel = folderLabel;
		this.validLabel = validLabel;
		this.info = info;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			final File selectedFile = fileChooser.getSelectedFile();
			folderLabel.setText(selectedFile.getAbsolutePath());
			info.setMaintenanceDir(selectedFile);
		}
		validLabel.setText(validityString());
	}


	@Override
	public String validityString() {
		return "invalid";
	}
}
