package upt.cti.svv.server;

import upt.cti.svv.app.Configuration;

import java.io.File;
import java.util.Properties;

public class DefaultServerSettings implements ServerSettings {
	private ServerStatus status;
	private final String address;
	private int port;
	private File webRootDir;
	private File maintenanceDir;
	private final boolean silent;

	/**
	 * Use settings from configuration
	 */
	public DefaultServerSettings(Configuration configuration) {
		this.silent = configuration.runSilently();
		this.status = silent ? ServerStatus.RUNNING : ServerStatus.STOPPED;
		this.address = configuration.defaultAddress();
		this.port = configuration.defaultPort();
		this.webRootDir = configuration.defaultWebRootDir();
		this.maintenanceDir = configuration.defaultMaintenanceDir();
	}

	/**
	 * Use custom settings
	 */
	public DefaultServerSettings(
			boolean silent,
			ServerStatus status,
			String address,
			int port,
			File webRootDir,
			File maintenanceDir
	) {
		this.silent = silent;
		this.status = status;
		this.address = address;
		this.port = port;
		this.webRootDir = webRootDir;
		this.maintenanceDir = maintenanceDir;
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
	public boolean isSilent() {
		return silent;
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

	@Override
	public Properties toProperties() {
		final Properties props = new Properties();
		props.setProperty("silent", String.valueOf(silent));
		props.setProperty("port", String.valueOf(port));
		props.setProperty("webroot", webRootDir.getAbsolutePath());
		props.setProperty("maintenance", maintenanceDir.getAbsolutePath());
		return props;
	}
}
