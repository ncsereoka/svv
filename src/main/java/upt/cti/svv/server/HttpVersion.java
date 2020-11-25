package upt.cti.svv.server;

public enum HttpVersion {
	HTTP_1_1("HTTP/1.1");

	public final String name;

	HttpVersion(String name) {
		this.name = name;
	}
}
