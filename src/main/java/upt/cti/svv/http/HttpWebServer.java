package upt.cti.svv.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import upt.cti.svv.util.ExceptionWrapper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
			ExceptionWrapper.close(serverSocket, "Could not close server socket");
		}
	}

	private void waitForConnections() {
		try {
			while (true) {
				log.info("Waiting for new connections...");
				handleNewConnection();
			}
		} catch (IOException e) {
			throw new ServerErrorException("Failed to accept new connection");
		}
	}

	private void handleNewConnection() throws IOException {
		Socket newClientConnection = serverSocket.accept();
		final HttpConnection newHttpConnection = new HttpConnection(newClientConnection);
		new Thread(newHttpConnection).start();
	}

	private void bindToPort() {
		try {
			this.serverSocket = new ServerSocket(this.port);
		} catch (IOException e) {
			throw new ServerErrorException("Could not listen on port " + this.port);
		}
		log.info("Server listening on port {}...", this.port);
	}
}
