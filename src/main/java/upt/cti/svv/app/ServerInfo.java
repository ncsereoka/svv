package upt.cti.svv.app;

public interface ServerInfo {
	void updateToStopped();

	void updateToMaintenance(int port);

	void updateToRunning(int port);

	ApplicationStatus getStatus();

	int getPort();

	String getAddress();

	String getPortForGui();
}
