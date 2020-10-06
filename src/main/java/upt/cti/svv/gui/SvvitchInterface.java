package upt.cti.svv.gui;

import upt.cti.svv.server.ServerSettings;

/**
 * Abstraction of an interface
 * Extracted as interface in order to avoid cyclic dependencies
 */
public interface SvvitchInterface {
	void update(ServerSettings settings);

	void display();
}
