package upt.cti.svv.server;

import org.junit.Test;
import upt.cti.svv.server.HttpRequestParser;
import upt.cti.svv.server.exception.InvalidRequestException;

import java.io.BufferedReader;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class HttpRequestParserTest {
	private static final String VALID_GET_TO_ROOT = "GET / HTTP/1.1\n";
	private static final String VALID_TEST_HEADERS = "Host: localhost:3000\n" +
			"User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:81.0) Gecko/20100101 Firefox/81.0\n" +
			"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\n" +
			"Accept-Language: en-US,en;q=0.5\n" +
			"Accept-Encoding: gzip, deflate\n" +
			"DNT: 1\n" +
			"Connection: keep-alive\n" +
			"Upgrade-Insecure-Requests: 1\n" +
			"Cache-Control: max-age=0\n";

	@Test
	public void valid_request() {
		parse(VALID_GET_TO_ROOT + VALID_TEST_HEADERS);
	}

	@Test(expected = InvalidRequestException.class)
	public void unknown_method() {
		parse("POST / HTTP/1.1\n" + VALID_TEST_HEADERS);
	}

	@Test(expected = InvalidRequestException.class)
	public void missing_url() {
		parse("GET HTTP/1.1\n" + VALID_TEST_HEADERS);
	}

	@Test(expected = InvalidRequestException.class)
	public void invalid_url() {
		parse("GET home HTTP/1.1\n" + VALID_TEST_HEADERS);
	}

	@Test(expected = InvalidRequestException.class)
	public void malformed_header() {
		parse(VALID_GET_TO_ROOT + VALID_TEST_HEADERS + "Referer\n");
	}

	@Test(expected = InvalidRequestException.class)
	public void invalid_header() {
		parse(VALID_GET_TO_ROOT + VALID_TEST_HEADERS + "Referer localhost:3000\n");
	}

	@Test(expected = InvalidRequestException.class)
	public void invalid_http_version() {
		parse("GET / HTTP/1.2\n" + VALID_TEST_HEADERS);
	}

	@Test(expected = InvalidRequestException.class)
	public void incomplete_header() {
		parse(VALID_GET_TO_ROOT + VALID_TEST_HEADERS + "Referer:\n");
	}

	@Test(expected = InvocationTargetException.class)
	public void instantiation_fails() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
		Constructor<HttpRequestParser> c = HttpRequestParser.class.getDeclaredConstructor();
		c.setAccessible(true);
		c.newInstance();
	}

	private void parse(String text) {
		HttpRequestParser.from(new BufferedReader(new StringReader(text)));
	}
}
