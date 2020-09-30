package upt.cti.svv.util;

public final class ImmutablePair<T, U> {
	private final T t;
	private final U u;

	public static <T, U> ImmutablePair<T, U> of(T t, U u) {
		return new ImmutablePair<>(t, u);
	}

	private ImmutablePair(T t, U u) {
		this.t = t;
		this.u = u;
	}

	public T getKey() {
		return t;
	}

	public U getValue() {
		return u;
	}
}
