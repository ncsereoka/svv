package upt.cti.svv;

import org.junit.Assert;
import org.junit.Test;
import upt.cti.svv.util.ValidatedResult;

public class ValidatedResultTest {
	@Test
	public void simple_happy_path() {
		final int result = ValidatedResult.of(5)
				.withCondition(integer -> integer > 4)
				.onFailThrow(() -> new AssertionError("sad"));
		Assert.assertEquals(5, result);
	}

	@Test
	public void complex_happy_path() {
		final int result = ValidatedResult.of(5)
				.withCondition(integer -> integer > 4)
				.withCondition(integer -> integer < 6)
				.onFailThrow(() -> new AssertionError("sad"));
		Assert.assertEquals(5, result);
	}

	@Test(expected = AssertionError.class)
	public void simple_sad_path() {
		ValidatedResult.of(5)
				.withCondition(integer -> integer < 4)
				.onFailThrow(() -> new AssertionError("sad"));
	}

	@Test(expected = AssertionError.class)
	public void complex_sad_path() {
		ValidatedResult.of(5)
				.withCondition(integer -> integer > 4)
				.withCondition(integer -> integer > 6)
				.onFailThrow(() -> new AssertionError("sad"));
	}
}
