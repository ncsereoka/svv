package upt.cti.svv.gui;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to hold 'interesting' UI components - these will need to be looked up
 */
public final class ComponentMap {
	private static final Map<Identifier, JComponent> map = new HashMap<>();

	public static void put(Identifier identifier, JComponent component) {
		map.put(identifier, component);
	}

	public static JComponent get(Identifier components) {
		return map.get(components);
	}

	public enum Identifier {
		SERVER_STATUS,
		SERVER_ADDRESS,
		SERVER_PORT,
		POWER_BUTTON,
		MAINTENANCE_CHECKBOX,
		PORT_FIELD,
		WEBROOT_DIR,
		WEBROOT_DIR_BUTTON,
		WEBROOT_DIR_VALID,
		MAINTENANCE_DIR,
		MAINTENANCE_DIR_BUTTON,
		MAINTENANCE_DIR_VALID;
	}
}
