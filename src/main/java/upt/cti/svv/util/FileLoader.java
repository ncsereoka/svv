package upt.cti.svv.util;

import upt.cti.svv.server.exception.ConfigurationException;

import java.io.File;
import java.util.Optional;

public final class FileLoader {
	public static File loadDirectory(String folderPath) {
		final File file = Optional.ofNullable(folderPath)
				.map(File::new)
				.orElseThrow(() -> new ConfigurationException("Configuration folder not specified."));

		return ValidatedResult.of(file)
				.withCondition(File::isDirectory)
				.onFailThrow(() ->
						new ConfigurationException(String.format("Specified folder '%s' does not exist.", folderPath)));
	}

	private FileLoader() {
		throw new UnsupportedOperationException();
	}
}
