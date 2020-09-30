package upt.cti.svv.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SvvitchInterface {
	private final JFrame frame;

	public SvvitchInterface() {
		this.frame = new JFrame();

		this.frame.setSize(800, 600);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.frame.add(mainPanel);

		JPanel superiorPanel = new JPanel();
		superiorPanel.setLayout(new BoxLayout(superiorPanel, BoxLayout.X_AXIS));
		superiorPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(BorderFactory.createTitledBorder("WebServer info"));
		superiorPanel.add(infoPanel);

		JPanel controlPanel = new JPanel();
		controlPanel.setBorder(BorderFactory.createTitledBorder("WebServer control"));
		superiorPanel.add(controlPanel);

		JPanel inferiorPanel = new JPanel();
		inferiorPanel.setLayout(new BoxLayout(inferiorPanel, BoxLayout.X_AXIS));
		inferiorPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel configurationPanel = new JPanel();
		configurationPanel.setBorder(BorderFactory.createTitledBorder("WebServer configuration"));
		inferiorPanel.add(configurationPanel);

		mainPanel.add(superiorPanel);
		mainPanel.add(inferiorPanel);
	}

	public void display() {
		this.frame.setVisible(true);
	}
}
