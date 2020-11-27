package upt.cti.svv.util;

import org.junit.Test;
import upt.cti.svv.server.exception.ConfigurationException;
import upt.cti.svv.util.FileLoader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;

public class FileLoaderTest {
	@Test
	public void loading_directory_works() throws URISyntaxException {
		final URL url = Objects.requireNonNull(getClass().getClassLoader().getResource("www"));
		final String path = Paths.get(url.toURI()).toString();
		FileLoader.loadDirectory(path);
	}

	@Test(expected = ConfigurationException.class)
	public void loading_file_fails() throws URISyntaxException {
		final URL url = Objects.requireNonNull(getClass().getClassLoader().getResource("www/index.html"));
		final String path = Paths.get(url.toURI()).toString();
		FileLoader.loadDirectory(path);
	}

	@Test(expected = ConfigurationException.class)
	public void loading_nonexistent_file_fails() throws URISyntaxException {
		final URL url = Objects.requireNonNull(getClass().getClassLoader().getResource("www"));
		final String path = Paths.get(url.toURI()).resolve("blueberry").toString();
		FileLoader.loadDirectory(path);
	}

	@Test(expected = InvocationTargetException.class)
	public void instantiation_fails() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
		Constructor<FileLoader> c = FileLoader.class.getDeclaredConstructor();
		c.setAccessible(true);
		c.newInstance();
	}
}
