package upt.cti.svv.app;

import java.io.File;

public interface ServerInfo {
	void updateToStopped();

	void updateToMaintenance(int port);

	void updateToRunning(int port);

	ApplicationStatus getStatus();

	void setPort(int newPort);

	int getPort();

	String getAddress();

	String getPortForGui();

	void setWebRootDir(File selectedDir);

	void setMaintenanceDir(File selectedDir);

	String getWebRootDirForGui();

	String getMaintenanceDirForGui();
}
