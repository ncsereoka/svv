package upt.cti.svv.util;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * For generics fun and less lines of code.
 *
 * @param <T> result type
 */
public final class ValidatedResult<T> {
	private static final ValidatedResult<?> FAILURE = new ValidatedResult(null);
	private final T value;

	private ValidatedResult(T value) {
		this.value = value;
	}

	public static <T> ValidatedResult<T> failure() {
		return (ValidatedResult<T>) FAILURE;
	}

	public static <T> ValidatedResult<T> of(T value) {
		return new ValidatedResult<>(value);
	}

	public <U> ValidatedResult<U> map(Function<? super T, ? extends U> mapper) {
		Objects.requireNonNull(mapper);
		return this.equals(failure()) ? failure() : of(mapper.apply(this.value));
	}

	/**
	 * Adds a condition to the validation
	 *
	 * @param predicate condition predicate
	 * @return the same instance with condition taken into account
	 */
	public ValidatedResult<T> withCondition(Predicate<? super T> predicate) {
		Objects.requireNonNull(predicate);
		return predicate.test(this.value) ? this : failure();
	}

	/**
	 * Returns the contained value.
	 *
	 * @param exceptionSupplier exception supplier
	 * @param <X>               result type
	 * @return validated result
	 * @throws X in case the validation failed
	 */
	public <X extends Throwable> T onFailThrow(Supplier<? extends X> exceptionSupplier) throws X {
		if (!this.equals(failure())) {
			return this.value;
		} else {
			throw exceptionSupplier.get();
		}
	}
}
