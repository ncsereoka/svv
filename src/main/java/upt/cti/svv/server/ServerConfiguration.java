package upt.cti.svv.server;

import java.io.File;
import java.util.Properties;

/**
 * Abstraction of a server's settings (address, port, state, etc)
 * Extracted as an interface to avoid cyclic dependencies
 */
public interface ServerConfiguration {
	void updateToStopped();

	void updateToMaintenance(int port);

	void updateToRunning(int port);

	ServerStatus getStatus();

	void setPort(int port);

	int getPort();

	String address();

	String getPortForGui();

	boolean isSilent();

	void setWebRootDir(File selectedDir);

	void setMaintenanceDir(File selectedDir);

	String getWebRootDirForGui();

	String getMaintenanceDirForGui();

	void store();
}
