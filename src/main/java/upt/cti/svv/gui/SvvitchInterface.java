package upt.cti.svv.gui;

import upt.cti.svv.server.ServerConfiguration;

/**
 * Abstraction of an interface
 * Extracted as interface in order to avoid cyclic dependencies
 */
public interface SvvitchInterface {
	void update(ServerConfiguration settings);

	void display();
}
