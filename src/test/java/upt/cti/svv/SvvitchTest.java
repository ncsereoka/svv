package upt.cti.svv;

import org.junit.Assert;
import org.junit.Test;
import upt.cti.svv.app.ApplicationStatus;
import upt.cti.svv.app.Svvitch;

public class SvvitchTest {
	@Test
	public void silent_start_in_running_mode_without_gui() {
		final Svvitch silentSvvitch = new Svvitch(true);
		final ApplicationStatus svvitchStatus = silentSvvitch.getInfo().getStatus();
		Assert.assertEquals("Svvitch is in RUNNING state.", ApplicationStatus.RUNNING, svvitchStatus);
		Assert.assertNull("GUI is not loaded..", silentSvvitch.getGui());
	}

	@Test
	public void loud_start_in_stopped_mode_with_gui() {
		final Svvitch loudSvvitch = new Svvitch(false);
		final ApplicationStatus svvitchStatus = loudSvvitch.getInfo().getStatus();
		Assert.assertEquals("Svvitch is in STOPPED state.", ApplicationStatus.STOPPED, svvitchStatus);
		Assert.assertNotNull("GUI is not loaded..", loudSvvitch.getGui());
	}
}