package upt.cti.svv.server;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

final class ResourceUtil {
	static byte[] readResourceFile(Class<?> clazz, String url) throws URISyntaxException, IOException {
		return Files.readAllBytes(Paths.get(Objects.requireNonNull(clazz.getClassLoader().getResource(url)).toURI()));
	}

	static File loadResourceFile(Class<?> clazz, String url) throws URISyntaxException {
		return Paths.get(Objects.requireNonNull(clazz.getClassLoader().getResource(url)).toURI()).toFile();
	}

	private ResourceUtil() {
		throw new UnsupportedOperationException();
	}
}
