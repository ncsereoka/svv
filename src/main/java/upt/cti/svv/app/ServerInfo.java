package upt.cti.svv.app;

public final class ServerInfo {
	private ApplicationStatus status;
	private String address;
	private int port;

	public ServerInfo(ApplicationStatus status, String address, int port) {
		this.status = status;
		this.address = address;
		this.port = port;
	}

	/**
	 * Default server settings
	 */
	public ServerInfo() {
		this.status = ApplicationStatus.STOPPED;
		this.address = Configuration.getDefaultAddress();
		this.port = Configuration.defaultPort();
	}

	public void updateToStopped() {
		this.status = ApplicationStatus.STOPPED;
		this.address = "";
		this.port = 0;
	}

	public void updateToMaintenance(String address, int port) {
		this.status = ApplicationStatus.MAINTENANCE;
		this.address = address;
		this.port = port;
	}

	public void updateToRunning(String address, int port) {
		this.status = ApplicationStatus.RUNNING;
		this.address = address;
		this.port = port;
	}

	public ApplicationStatus getStatus() {
		return status;
	}

	public String getStatusForGui() {
		switch (status) {
			case RUNNING:
				return "running...";
			case STOPPED:
				return "not running";
			case MAINTENANCE:
				return "running";
			default:
				return "";
		}
	}

	public String getAddress() {
		return address;
	}

	public String getPortForGui() {
		return String.valueOf(port);
	}

	public int getPort() {
		return port;
	}
}
