package upt.cti.svv;

import org.junit.Test;
import upt.cti.svv.server.HttpRequestParser;
import upt.cti.svv.server.exception.InvalidRequestException;

import java.io.BufferedReader;
import java.io.StringReader;

public class HttpRequestParserTest {
	@Test
	public void valid_request() {
		HttpRequestParser.from(toBufferedReader(validGetToRoot()));
	}

	@Test(expected = InvalidRequestException.class)
	public void unknown_method() {
		HttpRequestParser.from(toBufferedReader(validPostToRoot()));
	}

	@Test(expected = InvalidRequestException.class)
	public void invalid_request_line_missing_url() {
		HttpRequestParser.from(toBufferedReader(invalidGetToRootMissingUrl()));
	}

	@Test(expected = InvalidRequestException.class)
	public void invalid_request_line_invalid_url() {
		HttpRequestParser.from(toBufferedReader(invalidGetToRootInvalidUrl()));
	}

	@Test(expected = InvalidRequestException.class)
	public void malformed_header() {
		HttpRequestParser.from(toBufferedReader(validGetToRootWithMalformedHeader()));
	}

	@Test(expected = InvalidRequestException.class)
	public void invalid_header() {
		HttpRequestParser.from(toBufferedReader(validGetToRootWithInvalidHeaderFormat()));
	}

	@Test(expected = InvalidRequestException.class)
	public void invalid_http_version() {
		HttpRequestParser.from(toBufferedReader(validGetToRootWithInvalidHttpVersion()));
	}

	@Test(expected = InvalidRequestException.class)
	public void incomplete_header() {
		HttpRequestParser.from(toBufferedReader(validGetToRootWithIncompleteHeader()));
	}

	private BufferedReader toBufferedReader(String text) {
		return new BufferedReader(new StringReader(text));
	}

	private String validGetToRoot() {
		return "GET / HTTP/1.1\n" + testHeaders();
	}

	private String validGetToRootWithInvalidHttpVersion() {
		return "GET / HTTP/1.2\n" + testHeaders();
	}

	private String validPostToRoot() {
		return "POST / HTTP/1.1\n" + testHeaders();
	}

	private String invalidGetToRootMissingUrl() {
		return "GET HTTP/1.1\n" + testHeaders();
	}

	private String invalidGetToRootInvalidUrl() {
		return "GET home HTTP/1.1\n" + testHeaders();
	}

	private String validGetToRootWithInvalidHeaderFormat() {
		return "GET / HTTP/1.1\n" + testHeaders() + "Referer localhost:3000\n";
	}

	private String validGetToRootWithIncompleteHeader() {
		return "GET / HTTP/1.1\n" + testHeaders() + "Referer:\n";
	}

	private String validGetToRootWithMalformedHeader() {
		return "GET / HTTP/1.1\n" + testHeaders() + "Referer\n";
	}

	private String testHeaders() {
		return "Host: localhost:3000\n" +
				"User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:81.0) Gecko/20100101 Firefox/81.0\n" +
				"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\n" +
				"Accept-Language: en-US,en;q=0.5\n" +
				"Accept-Encoding: gzip, deflate\n" +
				"DNT: 1\n" +
				"Connection: keep-alive\n" +
				"Upgrade-Insecure-Requests: 1\n" +
				"Cache-Control: max-age=0\n";
	}
}
