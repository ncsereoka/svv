package upt.cti.svv.gui;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public final class ComponentMap {
	private static final Map<StatefulComponent, JComponent> map = new HashMap<>();

	public static void put(StatefulComponent statefulComponent, JComponent component) {
		map.put(statefulComponent, component);
	}

	public static JComponent get(StatefulComponent components) {
		return map.get(components);
	}
}
