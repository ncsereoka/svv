package upt.cti.svv.gui.listener;

import upt.cti.svv.app.ServerInfo;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PortListener implements DocumentListener {
	private final ServerInfo info;
	private final JTextField field;

	public PortListener(ServerInfo info, JTextField field) {
		this.info = info;
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
		info.setPort(number);
	}

	private int getValidPort() {
		int number;
		try {
			number = Integer.parseInt(field.getText());
			if (number > 65535) {
				number = 65535;
			} else if (number < 1025) {
				number = 1025;
			}
		} catch (NumberFormatException e) {
			number = 3000;
		}
		return number;
	}
}
