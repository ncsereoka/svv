package upt.cti.svv.http;

import java.io.IOException;
import java.net.ServerSocket;

public class HttpWebServer {
	private ServerSocket serverSocket;
	private int port;

	public HttpWebServer(int port) {
		this.port = port;
	}

	public HttpWebServer() {
		this.port = 3000;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void listen() {
		startListening();
		try {
			try {
				while (true) {
					System.out.println("Waiting for Connection");
					new HttpConnection(serverSocket.accept());
				}
			} catch (IOException e) {
				System.err.println("Accept failed.");
				System.exit(1);
			}
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				System.err.println("Could not close port: 10008.");
				System.exit(1);
			}
		}
	}

	private void startListening() {
		try {
			this.serverSocket = new ServerSocket(this.port);
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + port);
			System.exit(1);
		}
		System.out.println("Server listening on port " + port);
	}
}
