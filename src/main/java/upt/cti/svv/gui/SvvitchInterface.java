package upt.cti.svv.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SvvitchInterface {
	private final JFrame frame;

	public SvvitchInterface() {
		frame = buildFrame();
	}

	private JFrame buildFrame() {
		JFrame frame = new JFrame();
		frame.setSize(800, 600);
		frame.setResizable(false);
		frame.add(buildMainPanel());
		return frame;
	}

	private JPanel buildMainPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		mainPanel.add(buildSuperiorPanel());
		mainPanel.add(buildInferiorPanel());
		return mainPanel;
	}

	private JPanel buildSuperiorPanel() {
		JPanel superiorPanel = new JPanel();
		superiorPanel.setLayout(new BoxLayout(superiorPanel, BoxLayout.X_AXIS));
		superiorPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		superiorPanel.add(buildInfoPanel());
		superiorPanel.add(buildControlPanel());
		return superiorPanel;
	}

	private JPanel buildControlPanel() {
		JPanel controlPanel = new JPanel();
		controlPanel.setBorder(BorderFactory.createTitledBorder("WebServer control"));
		return controlPanel;
	}

	private JPanel buildInferiorPanel() {
		JPanel inferiorPanel = new JPanel();

		inferiorPanel.setLayout(new BoxLayout(inferiorPanel, BoxLayout.X_AXIS));
		inferiorPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel configurationPanel = new JPanel();
		configurationPanel.setBorder(BorderFactory.createTitledBorder("WebServer configuration"));
		inferiorPanel.add(configurationPanel);

		return inferiorPanel;
	}

	private JPanel buildInfoPanel() {
		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(BorderFactory.createTitledBorder("WebServer info"));
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
		infoPanel.add(buildInfoEntry("Server status:", StatefulComponents.SERVER_STATUS));
		infoPanel.add(buildInfoEntry("Server address:", StatefulComponents.SERVER_ADDRESS));
		infoPanel.add(buildInfoEntry("Server port:", StatefulComponents.SERVER_PORT));
		return infoPanel;
	}

	private JPanel buildInfoEntry(String staticLabelText, StatefulComponents component) {
		JPanel entry = new JPanel();
		entry.setLayout(new BoxLayout(entry, BoxLayout.Y_AXIS));
		JLabel staticLabel = new JLabel(staticLabelText);
		JLabel variableLabel = new JLabel();
		ComponentMap.put(component.name(), variableLabel);
		entry.add(staticLabel);
		entry.add(variableLabel);
		return entry;
	}

	public void display() {
		this.frame.setVisible(true);
	}
}
