package upt.cti.svv.http;

public class ServerErrorException extends RuntimeException {
	ServerErrorException(String msg) {
		super(msg);
	}
}
