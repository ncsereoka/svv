package upt.cti.svv.app;

public final class DefaultServerInfo implements ServerInfo {
	private ApplicationStatus status;
	private final String address;
	private int port;

	/**
	 * Use default server settings
	 */
	public DefaultServerInfo(boolean silent) {
		this.status = silent ? ApplicationStatus.RUNNING : ApplicationStatus.STOPPED;
		this.address = Configuration.getDefaultAddress();
		this.port = Configuration.defaultPort();
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

	@Override
	public int getPort() {
		return port;
	}
}
