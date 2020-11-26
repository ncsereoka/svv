package upt.cti.svv.gui.listener;

import upt.cti.svv.server.ServerConfiguration;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PortListener implements DocumentListener {
	private final ServerConfiguration settings;
	private final JTextField field;

	public PortListener(ServerConfiguration settings, JTextField field) {
		this.settings = settings;
		this.field = field;
	}

	@Override
	public void insertUpdate(DocumentEvent documentEvent) {
		change();
	}

	@Override
	public void removeUpdate(DocumentEvent documentEvent) {
		change();
	}

	@Override
	public void changedUpdate(DocumentEvent documentEvent) {
		change();
	}

	private void change() {
		int number = getValidPort();
		settings.setPort(number);
	}

	private int getValidPort() {
		try {
			return Integer.parseInt(field.getText());
		} catch (NumberFormatException e) {
			return 3000;
		}
	}
}
