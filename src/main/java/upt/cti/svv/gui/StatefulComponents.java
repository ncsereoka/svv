package upt.cti.svv.gui;

public enum StatefulComponents {
	SERVER_STATUS("ServerStatus"),
	SERVER_ADDRESS("ServerAddress"),
	SERVER_PORT("ServerPort"),
	POWER_BUTTON("PowerButton"),
	MAINTENANCE_CHECKBOX("MaintenanceCheckbox");

	private final String name;

	StatefulComponents(String name) {
		this.name = name;
	}
}
