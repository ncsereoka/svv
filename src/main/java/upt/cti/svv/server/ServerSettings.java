package upt.cti.svv.server;

import java.io.File;

public interface ServerSettings {
	void updateToStopped();

	void updateToMaintenance(int port);

	void updateToRunning(int port);

	ServerStatus getStatus();

	void setPort(int newPort);

	int getPort();

	String getAddress();

	String getPortForGui();

	void setWebRootDir(File selectedDir);

	void setMaintenanceDir(File selectedDir);

	String getWebRootDirForGui();

	String getMaintenanceDirForGui();
}
