package upt.cti.svv;

import org.junit.Assert;
import org.junit.Test;
import upt.cti.svv.app.Configuration;
import upt.cti.svv.app.Svvitch;
import upt.cti.svv.gui.SvvitchInterface;
import upt.cti.svv.server.DefaultServerSettings;
import upt.cti.svv.server.HttpWebServer;
import upt.cti.svv.server.ServerSettings;
import upt.cti.svv.server.ServerStatus;

import static org.mockito.Mockito.*;

public class SvvitchTest {
	@Test
	public void silent_start_in_running_mode_without_gui() {
		final Configuration configuration = new Configuration(null, true, 0, null, null, null);
		final ServerSettings settings = new DefaultServerSettings(configuration);
		final Svvitch silentSvvitch = new Svvitch(settings, null, new HttpWebServer(settings));
		final ServerStatus svvitchStatus = silentSvvitch.getSettings().getStatus();
		Assert.assertEquals("Svvitch is in RUNNING state.", ServerStatus.RUNNING, svvitchStatus);
		Assert.assertNull("GUI is not loaded..", silentSvvitch.getGui());
	}

	@Test
	public void loud_start_in_stopped_mode_with_gui() {
		final Configuration configuration = new Configuration(null, false, 0, null, null, null);
		final ServerSettings settings = new DefaultServerSettings(configuration);
		final SvvitchInterface svvitchInterface = mock(SvvitchInterface.class);
		doNothing().when(svvitchInterface).display();
		final Svvitch loudSvvitch = new Svvitch(settings, svvitchInterface, new HttpWebServer(settings));
		final ServerStatus svvitchStatus = loudSvvitch.getSettings().getStatus();
		Assert.assertEquals("Svvitch is in STOPPED state.", ServerStatus.STOPPED, svvitchStatus);
		Assert.assertNotNull("GUI is not loaded..", loudSvvitch.getGui());
	}
}
