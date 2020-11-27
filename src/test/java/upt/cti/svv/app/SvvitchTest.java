package upt.cti.svv.app;

import org.junit.Assert;
import org.junit.Test;
import upt.cti.svv.gui.SvvitchInterface;
import upt.cti.svv.server.HttpWebServer;
import upt.cti.svv.server.ResourceUtil;
import upt.cti.svv.server.ServerStatus;

import java.net.URISyntaxException;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

public class SvvitchTest {
	@Test
	public void silent_start_in_running_mode_without_gui() {
		final Configuration configuration = new Configuration(null, true, 0, null, null, null);
		final Svvitch silentSvvitch = new Svvitch(configuration);
		final ServerStatus svvitchStatus = silentSvvitch.getSettings().getStatus();
		Assert.assertEquals("Svvitch is in RUNNING state.", ServerStatus.RUNNING, svvitchStatus);
		Assert.assertNull("GUI is not loaded..", silentSvvitch.getGui());
	}

	@Test
	public void loud_start_in_stopped_mode_with_gui() {
		final Configuration configuration = new Configuration(null, false, 0, null, null, null);
		final SvvitchInterface svvitchInterface = mock(SvvitchInterface.class);
		doNothing().when(svvitchInterface).display();
		final Svvitch loudSvvitch = new Svvitch(configuration, svvitchInterface, new HttpWebServer(configuration));
		final ServerStatus svvitchStatus = loudSvvitch.getSettings().getStatus();
		Assert.assertEquals("Svvitch is in STOPPED state.", ServerStatus.STOPPED, svvitchStatus);
		Assert.assertNotNull("GUI is not loaded..", loudSvvitch.getGui());
	}

	@Test
	public void default_config_works() {
		Svvitch.main(new String[]{});
	}

	@Test
	public void custom_config_works() throws URISyntaxException {
		final String configFile = ResourceUtil.loadResourceFile(getClass(), "config.properties").getAbsolutePath();
		Svvitch.main(new String[]{configFile});
	}
}
