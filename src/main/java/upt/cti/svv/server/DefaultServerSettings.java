package upt.cti.svv.server;

import upt.cti.svv.app.Configuration;

import java.io.File;

public class DefaultServerSettings implements ServerSettings {
	private ServerStatus status;
	private final String address;
	private int port;
	private File webRootDir;
	private File maintenanceDir;

	/**
	 * Use default server settings
	 */
	public DefaultServerSettings(boolean silent) {
		this.status = silent ? ServerStatus.RUNNING : ServerStatus.STOPPED;
		this.address = Configuration.defaultAddress();
		this.port = Configuration.defaultPort();
		this.webRootDir = Configuration.defaultWebRootDir();
		this.maintenanceDir = Configuration.defaultMaintenanceDir();
	}

	@Override
	public void updateToStopped() {
		this.status = ServerStatus.STOPPED;
	}

	@Override
	public void updateToMaintenance(int port) {
		this.status = ServerStatus.MAINTENANCE;
		this.port = port;
	}

	@Override
	public void updateToRunning(int port) {
		this.status = ServerStatus.RUNNING;
		this.port = port;
	}

	@Override
	public ServerStatus getStatus() {
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
	public void setPort(int newPort) {
		this.port = newPort;
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

	@Override
	public String getWebRootDirForGui() {
		return webRootDir.getAbsolutePath();
	}

	@Override
	public String getMaintenanceDirForGui() {
		return maintenanceDir.getAbsolutePath();
	}
}
