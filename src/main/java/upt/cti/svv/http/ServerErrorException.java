package upt.cti.svv.http;

public class ServerErrorException extends RuntimeException {
	public ServerErrorException(String msg) {
		super(msg);
	}
}
