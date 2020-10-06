package upt.cti.svv.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import upt.cti.svv.app.ApplicationStatus;
import upt.cti.svv.app.ServerInfo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpConnectionHandler implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(HttpConnectionHandler.class);

	private final ServerSocket serverSocket;
	private final ServerInfo info;

	public HttpConnectionHandler(ServerSocket serverSocket, ServerInfo info) {
		this.serverSocket = serverSocket;
		this.info = info;
	}

	@Override
	public void run() {
		try {
			while (!this.info.getStatus().equals(ApplicationStatus.STOPPED)) {
				log.info("Waiting for new connections...");
				handleNewConnection();
			}
		} catch (IOException e) {
			log.info("Handler interrupted");
		}
	}

	private void handleNewConnection() throws IOException {
		Socket newClientConnection = serverSocket.accept();
		final HttpConnection newHttpConnection = new HttpConnection(newClientConnection);
		new Thread(newHttpConnection).start();
	}
}
