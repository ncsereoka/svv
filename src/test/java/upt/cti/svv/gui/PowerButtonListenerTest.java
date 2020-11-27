package upt.cti.svv.gui;

import org.junit.Test;
import upt.cti.svv.app.Configuration;
import upt.cti.svv.gui.listener.PowerButtonListener;
import upt.cti.svv.server.ResourceUtil;
import upt.cti.svv.server.ServerConfiguration;
import upt.cti.svv.server.ServerStatus;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class PowerButtonListenerTest {
	@Test
	public void test() throws URISyntaxException {
		final ServerConfiguration config = new Configuration(
				null,
				false,
				3000,
				"",
				ResourceUtil.loadResourceFile(getClass(), "www"),
				ResourceUtil.loadResourceFile(getClass(), "maintenance"));

		final PowerButtonListener listener = new PowerButtonListener(
				mock(SvvitchInterface.class),
				config,
				mock(JFrame.class));

		assertEquals(ServerStatus.STOPPED, config.getStatus());
		listener.actionPerformed(mock(ActionEvent.class));
		assertEquals(ServerStatus.RUNNING, config.getStatus());

		assertEquals(ServerStatus.RUNNING, config.getStatus());
		listener.actionPerformed(mock(ActionEvent.class));
		assertEquals(ServerStatus.STOPPED, config.getStatus());

		config.updateToMaintenance(3000);
		assertEquals(ServerStatus.MAINTENANCE, config.getStatus());
		listener.actionPerformed(mock(ActionEvent.class));
		assertEquals(ServerStatus.STOPPED, config.getStatus());
	}
}
