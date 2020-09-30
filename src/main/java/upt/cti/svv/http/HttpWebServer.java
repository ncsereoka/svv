package upt.cti.svv.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;

public class HttpWebServer {
	private static final Logger log = LoggerFactory.getLogger(HttpWebServer.class);

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
		bindToPort();
		try {
			waitForConnections();
		} finally {
			closeServerSocket();
		}
	}

	private void waitForConnections() {
		try {
			while (true) {
				log.info("Waiting for new connection...");
				new HttpConnection(serverSocket.accept());
			}
		} catch (IOException e) {
			log.error("Failed to accept new connection");
			System.exit(1);
		}
	}

	private void closeServerSocket() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			log.error("Could not close port {}", this.port);
			System.exit(1);
		}
	}

	private void bindToPort() {
		try {
			this.serverSocket = new ServerSocket(this.port);
		} catch (IOException e) {
			log.error("Could not listen on port: {}", this.port);
			System.exit(1);
		}
		log.info("Server listening on port {}...", this.port);
	}
}
