package upt.cti.svv.gui;

import org.junit.Before;
import org.junit.Test;
import upt.cti.svv.app.Configuration;
import upt.cti.svv.gui.listener.MaintenanceCheckboxListener;
import upt.cti.svv.gui.listener.PortListener;
import upt.cti.svv.gui.listener.PowerButtonListener;
import upt.cti.svv.server.ResourceUtil;
import upt.cti.svv.server.ServerConfiguration;
import upt.cti.svv.server.ServerStatus;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.event.ActionEvent;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListenerTest {
	private ServerConfiguration config;

	@Before
	public void setConfig() throws URISyntaxException {
		config = new Configuration(
				null,
				false,
				3000,
				"",
				ResourceUtil.loadResourceFile(getClass(), "www"),
				ResourceUtil.loadResourceFile(getClass(), "maintenance"));
	}

	@Test
	public void power_button_listener() {
		assertEquals(ServerStatus.STOPPED, config.getStatus());

		final PowerButtonListener listener = new PowerButtonListener(
				mock(SvvitchInterface.class),
				config,
				mock(JFrame.class));

		listener.actionPerformed(mock(ActionEvent.class));
		assertEquals(ServerStatus.RUNNING, config.getStatus());

		listener.actionPerformed(mock(ActionEvent.class));
		assertEquals(ServerStatus.STOPPED, config.getStatus());

		config.updateToMaintenance(3000);
		assertEquals(ServerStatus.MAINTENANCE, config.getStatus());
		listener.actionPerformed(mock(ActionEvent.class));
		assertEquals(ServerStatus.STOPPED, config.getStatus());
	}

	@Test
	public void maintenance_checkbox_listener() {
		config.updateToRunning(3000);
		assertEquals(ServerStatus.RUNNING, config.getStatus());

		final MaintenanceCheckboxListener listener = new MaintenanceCheckboxListener(mock(SvvitchInterface.class), config);

		listener.actionPerformed(mock(ActionEvent.class));
		assertEquals(ServerStatus.MAINTENANCE, config.getStatus());
		listener.actionPerformed(mock(ActionEvent.class));
		assertEquals(ServerStatus.RUNNING, config.getStatus());
	}

	@Test
	public void port_listener() {
		final JTextField textField = mock(JTextField.class);
		when(textField.getText()).thenReturn("3001").thenReturn("3001a");
		final PortListener listener = new PortListener(config,textField);

		assertEquals(3000, config.getPort());
		listener.changedUpdate(mock(DocumentEvent.class));
		assertEquals(3001, config.getPort());
		listener.insertUpdate(mock(DocumentEvent.class));
		assertEquals(3000, config.getPort());
	}
}
