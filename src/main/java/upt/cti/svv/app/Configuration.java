package upt.cti.svv.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import upt.cti.svv.server.ServerConfiguration;
import upt.cti.svv.server.ServerStatus;
import upt.cti.svv.server.exception.ConfigurationException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public final class Configuration implements ServerConfiguration {
	private static final Logger log = LoggerFactory.getLogger(Configuration.class);

	private final File configurationFile;
	private final boolean silent;
	private int port;
	private final String address;
	private File webRoot;
	private File maintenance;
	private ServerStatus status;

	public Configuration(
			File configurationFile,
			boolean silent,
			int port,
			String address,
			File webRoot,
			File maintenance
	) {
		this.configurationFile = configurationFile;
		this.silent = silent;
		this.port = port;
		this.address = address;
		this.webRoot = webRoot;
		this.maintenance = maintenance;
		this.status = this.silent ? ServerStatus.RUNNING : ServerStatus.STOPPED;
	}

	@Override
	public String address() {
		return address;
	}

	@Override
	public void updateToStopped() {
		this.status = ServerStatus.STOPPED;
	}

	@Override
	public void updateToMaintenance(int port) {
		log.info("Server state changed to MAINTENANCE");
		this.status = ServerStatus.MAINTENANCE;
		this.port = port;
	}

	@Override
	public void updateToRunning(int port) {
		log.info("Server state changed to RUNNING");
		this.status = ServerStatus.RUNNING;
		this.port = port;
	}

	@Override
	public ServerStatus getStatus() {
		return status;
	}

	@Override
	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public String getPortForGui() {
		return String.valueOf(port);
	}

	@Override
	public boolean isSilent() {
		return silent;
	}

	@Override
	public void setWebRootDir(File selectedDir) {
		this.webRoot = selectedDir;
	}

	@Override
	public void setMaintenanceDir(File selectedDir) {
		this.maintenance = selectedDir;
	}

	@Override
	public String getWebRootDirForGui() {
		return webRoot.getAbsolutePath();
	}

	@Override
	public String getMaintenanceDirForGui() {
		return maintenance.getAbsolutePath();
	}

	@Override
	public void store() {
		try (FileOutputStream out = new FileOutputStream(configurationFile)) {
			toProperties().store(out, null);
		} catch (IOException e) {
			throw new ConfigurationException("Error saving configuration.");
		}
		log.info("New configuration saved.");
	}

	private Properties toProperties() {
		final Properties props = new Properties();
		props.setProperty("silent", String.valueOf(silent));
		props.setProperty("port", String.valueOf(port));
		props.setProperty("webroot", webRoot.getAbsolutePath());
		props.setProperty("maintenance", maintenance.getAbsolutePath());
		return props;
	}
}
