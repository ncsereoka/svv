package upt.cti.svv;

import upt.cti.svv.http.HttpWebServer;

import java.io.IOException;

public class Svvitch {

	public static void main(String[] args) throws IOException {
		new HttpWebServer().listen();
	}
}