package upt.cti.svv;

import org.junit.Test;
import upt.cti.svv.server.exception.InvalidRequestException;

import static org.junit.Assert.fail;

public class HttpRequestParserTest {
	@Test
	public void valid_request() {
		fail("More to come");
	}

	@Test(expected = InvalidRequestException.class)
	public void unknown_method() {
		fail("More to come");
	}

	@Test(expected = InvalidRequestException.class)
	public void invalid_request_line() {
		fail("More to come");
	}

	@Test(expected = InvalidRequestException.class)
	public void malformed_headers() {
		fail("More to come");
	}
}
