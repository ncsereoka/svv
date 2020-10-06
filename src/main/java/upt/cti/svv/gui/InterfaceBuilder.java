package upt.cti.svv.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Utility class to separate UI building from everything else
 */
public final class InterfaceBuilder {
	public static JFrame newInterface() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(500, 400);
		frame.setResizable(false);
		frame.add(mainPanel());
		return frame;
	}

	private static JPanel mainPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2, 1));
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		mainPanel.add(upperPanel());
		mainPanel.add(lowerPanel());
		return mainPanel;
	}

	private static JPanel upperPanel() {
		JPanel upperPanel = new JPanel();
		upperPanel.setLayout(new GridLayout(1, 2));
		upperPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		upperPanel.add(infoPanel());
		upperPanel.add(controlPanel());
		return upperPanel;
	}

	private static JPanel controlPanel() {
		JPanel controlPanel = new JPanel();
		controlPanel.setBorder(BorderFactory.createTitledBorder("WebServer control"));
		controlPanel.setLayout(new GridLayout(2, 1));
		controlPanel.add(controlPanelButton());
		controlPanel.add(controlPanelCheckBox());
		return controlPanel;
	}

	private static JCheckBox controlPanelCheckBox() {
		JCheckBox maintenanceCheckBox = new JCheckBox("Switch to maintenance mode");
		maintenanceCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		ComponentMap.put(ComponentMap.Identifier.MAINTENANCE_CHECKBOX, maintenanceCheckBox);
		return maintenanceCheckBox;
	}

	private static JButton controlPanelButton() {
		JButton powerButton = new JButton("Start server");
		powerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		ComponentMap.put(ComponentMap.Identifier.POWER_BUTTON, powerButton);
		return powerButton;
	}

	private static JPanel lowerPanel() {
		JPanel inferiorPanel = new JPanel();
		inferiorPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		inferiorPanel.setLayout(new BoxLayout(inferiorPanel, BoxLayout.Y_AXIS));
		inferiorPanel.add(configurationPanel());
		return inferiorPanel;
	}

	private static JPanel configurationPanel() {
		JPanel configurationPanel = new JPanel();
		configurationPanel.setBorder(BorderFactory.createTitledBorder("WebServer configuration"));
		configurationPanel.setLayout(new GridLayout(3, 1));
		configurationPanel.add(configurationPanelPortSection());
		configurationPanel.add(configurationPanelWebRootSection());
		configurationPanel.add(configurationPanelMaintenanceSection());
		return configurationPanel;
	}

	private static JPanel configurationPanelMaintenanceSection() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		panel.add(new JLabel("Maintenance directory"));
		panel.add(Box.createHorizontalGlue());

		JLabel selectedLabel = new JLabel("selected");
		selectedLabel.setBorder(BorderFactory.createEtchedBorder());
		ComponentMap.put(ComponentMap.Identifier.MAINTENANCE_DIR, selectedLabel);
		panel.add(selectedLabel);

		panel.add(Box.createHorizontalGlue());

		JLabel validLabel = new JLabel("valid");

		JButton chooserButton = new JButton(",,,");
		ComponentMap.put(ComponentMap.Identifier.MAINTENANCE_DIR_BUTTON, chooserButton);
		panel.add(chooserButton);

		panel.add(Box.createHorizontalGlue());

		ComponentMap.put(ComponentMap.Identifier.MAINTENANCE_DIR_VALID, validLabel);
		panel.add(validLabel);

		return panel;
	}

	private static JPanel configurationPanelWebRootSection() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		panel.add(new JLabel("Web root directory"));
		panel.add(Box.createHorizontalGlue());

		JLabel selectedLabel = new JLabel("selected");
		selectedLabel.setBorder(BorderFactory.createEtchedBorder());
		ComponentMap.put(ComponentMap.Identifier.WEBROOT_DIR, selectedLabel);
		panel.add(selectedLabel);

		panel.add(Box.createHorizontalGlue());

		JLabel validLabel = new JLabel("valid");

		JButton chooserButton = new JButton(",,,");
		ComponentMap.put(ComponentMap.Identifier.WEBROOT_DIR_BUTTON, chooserButton);
		panel.add(chooserButton);

		panel.add(Box.createHorizontalGlue());

		ComponentMap.put(ComponentMap.Identifier.WEBROOT_DIR_VALID, validLabel);
		panel.add(validLabel);

		return panel;
	}

	private static JPanel configurationPanelPortSection() {
		JPanel portEntry = new JPanel();
		portEntry.setLayout(new BoxLayout(portEntry, BoxLayout.X_AXIS));
		JLabel portLabel = new JLabel("Server listening on port");
		portEntry.add(portLabel);
		portEntry.add(Box.createHorizontalGlue());

		JTextField portField = new JTextField();
		ComponentMap.put(ComponentMap.Identifier.PORT_FIELD, portField);
		portEntry.add(portField);
		return portEntry;
	}

	private static JPanel infoPanel() {
		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(BorderFactory.createTitledBorder("WebServer info"));
		infoPanel.setLayout(new GridLayout(3, 1));
		infoPanel.add(infoPanelEntry("Server status:", ComponentMap.Identifier.SERVER_STATUS));
		infoPanel.add(infoPanelEntry("Server address:", ComponentMap.Identifier.SERVER_ADDRESS));
		infoPanel.add(infoPanelEntry("Server port:", ComponentMap.Identifier.SERVER_PORT));
		return infoPanel;
	}

	private static JPanel infoPanelEntry(String staticLabelText, ComponentMap.Identifier component) {
		JPanel entry = new JPanel();
		entry.setLayout(new BoxLayout(entry, BoxLayout.X_AXIS));
		entry.add(new JLabel(staticLabelText));
		entry.add(Box.createHorizontalGlue());

		// Save this variable label to the global component map
		JLabel variableLabel = new JLabel("not running");
		ComponentMap.put(component, variableLabel);
		entry.add(variableLabel);

		return entry;
	}

	private InterfaceBuilder() {
		throw new UnsupportedOperationException();
	}
}
