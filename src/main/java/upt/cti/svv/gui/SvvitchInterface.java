package upt.cti.svv.gui;

import upt.cti.svv.app.ApplicationState;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.NumberFormat;

public final class SvvitchInterface {
	private static final String APPLICATION_NAME = "Switch";
	private final JFrame frame;

	public SvvitchInterface() {
		this.frame = frame();
		setTitle(ApplicationState.STOPPED);
	}

	public void display() {
		this.frame.setVisible(true);
	}

	public void setTitle(ApplicationState state) {
		final String title = String.format("%s - [%s]", APPLICATION_NAME, state.name());
		this.frame.setTitle(title);
	}

	private JFrame frame() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(500, 400);
		frame.setResizable(false);
		frame.add(mainPanel());
		return frame;
	}

	private JPanel mainPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		mainPanel.add(upperPanel());
		mainPanel.add(lowerPanel());
		return mainPanel;
	}

	private JPanel upperPanel() {
		JPanel upperPanel = new JPanel();
		upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.X_AXIS));
		upperPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		upperPanel.add(infoPanel());
		upperPanel.add(controlPanel());
		return upperPanel;
	}

	private JPanel controlPanel() {
		JPanel controlPanel = new JPanel();
		controlPanel.setBorder(BorderFactory.createTitledBorder("WebServer control"));
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

		JButton powerButton = new JButton("Start server");
		powerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		ComponentMap.put(StatefulComponent.POWER_BUTTON, powerButton);
		controlPanel.add(powerButton);

		JCheckBox maintenanceCheckBox = new JCheckBox("Switch to maintenance mode");
		maintenanceCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		ComponentMap.put(StatefulComponent.MAINTENANCE_CHECKBOX, maintenanceCheckBox);
		controlPanel.add(maintenanceCheckBox);
		return controlPanel;
	}

	private JPanel lowerPanel() {
		JPanel inferiorPanel = new JPanel();
		inferiorPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		inferiorPanel.setLayout(new BoxLayout(inferiorPanel, BoxLayout.Y_AXIS));
		inferiorPanel.add(configurationPanel());
		return inferiorPanel;
	}

	private JPanel configurationPanel() {
		JPanel configurationPanel = new JPanel();
		configurationPanel.setBorder(BorderFactory.createTitledBorder("WebServer configuration"));
		configurationPanel.setLayout(new BoxLayout(configurationPanel, BoxLayout.Y_AXIS));

		configurationPanel.add(configurationPanelPortSection());
		configurationPanel.add(configurationPanelWebRootSection());
		configurationPanel.add(configurationPanelMaintenanceSection());

		return configurationPanel;
	}

	private JPanel configurationPanelMaintenanceSection() {
		JPanel panel = new JPanel();

		panel.add(new JLabel("Maintenance directory"));

		JLabel selectedLabel = new JLabel("selected");
		selectedLabel.setBorder(BorderFactory.createEtchedBorder());
		ComponentMap.put(StatefulComponent.MAINTENANCE_DIR, selectedLabel);
		panel.add(selectedLabel);

		panel.add(Box.createHorizontalGlue());

		JFileChooser maintenanceFileChooser = new JFileChooser();
		JButton chooserButton = new JButton(",,,");
		panel.add(chooserButton);

		return panel;
	}

	private JPanel configurationPanelWebRootSection() {
		JPanel panel = new JPanel();

		panel.add(new JLabel("Web root directory"));

		JLabel selectedLabel = new JLabel("selected");
		selectedLabel.setBorder(BorderFactory.createEtchedBorder());
		ComponentMap.put(StatefulComponent.WEBROOT_DIR, selectedLabel);
		panel.add(selectedLabel);

		panel.add(Box.createHorizontalGlue());

		JFileChooser webRootFileChooser = new JFileChooser();
		JButton chooserButton = new JButton(",,,");
		panel.add(chooserButton);

		return panel;
	}

	private JPanel configurationPanelPortSection() {
		JPanel portEntry = new JPanel();
		portEntry.setLayout(new BoxLayout(portEntry, BoxLayout.X_AXIS));
		JLabel portLabel = new JLabel("Server listening on port");
		portEntry.add(portLabel);
		portEntry.add(Box.createHorizontalGlue());

		JFormattedTextField portField = new JFormattedTextField(NumberFormat.getCurrencyInstance());
		ComponentMap.put(StatefulComponent.PORT_FIELD, portField);
		portEntry.add(portField);
		return portEntry;
	}

	private JPanel infoPanel() {
		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(BorderFactory.createTitledBorder("WebServer info"));
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.add(infoPanelEntry("Server status:", StatefulComponent.SERVER_STATUS));
		infoPanel.add(infoPanelEntry("Server address:", StatefulComponent.SERVER_ADDRESS));
		infoPanel.add(infoPanelEntry("Server port:", StatefulComponent.SERVER_PORT));
		return infoPanel;
	}

	private JPanel infoPanelEntry(String staticLabelText, StatefulComponent component) {
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
}
