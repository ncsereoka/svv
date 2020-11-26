package upt.cti.svv.util;

import upt.cti.svv.server.exception.ConfigurationException;

import java.util.Optional;

public final class PortValidator {
	public static int read(String configPort) {
		final int port = Optional.ofNullable(configPort)
				.map(Integer::parseInt)
				.orElseThrow(() -> new ConfigurationException("Configuration port not specified."));

		return ValidatedResult.of(port)
				.withCondition(PortValidator::isValidPort)
				.onFailThrow(() -> new ConfigurationException(String.format("Specified port '%d' is invalid. Please use a value between 1025 and 65535", port)));
	}

	public static boolean isValidPort(Integer p) {
		return 1025 <= p && p <= 65535;
	}

	private PortValidator() {
		throw new UnsupportedOperationException();
	}
}
