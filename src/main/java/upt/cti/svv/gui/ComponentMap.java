package upt.cti.svv.gui;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public final class ComponentMap {
	private static Map<String, JComponent> map = new HashMap<>();

	public static void put(String name, JComponent component) {
		map.put(name, component);
	}

	public static JComponent get(String name) {
		return map.get(name);
	}
}
