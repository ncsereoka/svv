package upt.cti.svv.http;

public final class ServerErrorException extends RuntimeException {
	public ServerErrorException(String msg) {
		super(msg);
	}
}
