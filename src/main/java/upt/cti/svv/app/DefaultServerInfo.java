package upt.cti.svv.app;

import java.io.File;

public class DefaultServerInfo implements ServerInfo {
	private ApplicationStatus status;
	private final String address;
	private int port;
	private File webRootDir;
	private File maintenanceDir;

	/**
	 * Use default server settings
	 */
	public DefaultServerInfo(boolean silent) {
		this.status = silent ? ApplicationStatus.RUNNING : ApplicationStatus.STOPPED;
		this.address = Configuration.defaultAddress();
		this.port = Configuration.defaultPort();
		this.webRootDir = Configuration.defaultWebRootDir();
		this.maintenanceDir = Configuration.defaultMaintenanceDir();
	}

	@Override
	public void updateToStopped() {
		this.status = ApplicationStatus.STOPPED;
	}

	@Override
	public void updateToMaintenance(int port) {
		this.status = ApplicationStatus.MAINTENANCE;
		this.port = port;
	}

	@Override
	public void updateToRunning(int port) {
		this.status = ApplicationStatus.RUNNING;
		this.port = port;
	}

	@Override
	public ApplicationStatus getStatus() {
		return status;
	}

	@Override
	public String getAddress() {
		return address;
	}

	@Override
	public String getPortForGui() {
		return String.valueOf(port);
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public void setWebRootDir(File selectedDir) {
		this.webRootDir = selectedDir;
	}

	@Override
	public void setMaintenanceDir(File selectedDir) {
		this.maintenanceDir = selectedDir;
	}
}
