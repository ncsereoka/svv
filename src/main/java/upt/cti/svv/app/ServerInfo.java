package upt.cti.svv.app;

public final class ServerInfo {
	private String status;
	private String address;
	private String port;

	public ServerInfo(String status, String address, String port) {
		this.status = status;
		this.address = address;
		this.port = port;
	}

	public void updateToStopped() {
		this.status = "not running";
		this.address = "not running";
		this.port = "not running";
	}

	public void updateToMaintenance(String address, String port) {
		this.status = "maintenance";
		this.address = address;
		this.port = port;
	}

	public void updateToRunning(String address, String port) {
		this.status = "running...";
		this.address = address;
		this.port = port;
	}
}
