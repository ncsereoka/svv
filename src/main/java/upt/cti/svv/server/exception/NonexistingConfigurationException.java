package upt.cti.svv.server.exception;

public class NonexistingConfigurationException extends RuntimeException {
	public NonexistingConfigurationException(String msg) {
		super(msg);
	}
}