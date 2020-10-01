package upt.cti.svv.gui;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

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
		MAINTENANCE_DIR;
	}
}
