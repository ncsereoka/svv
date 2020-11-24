package upt.cti.svv.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Extracted as Runnable so it doesn't block the GUI when being connected to the listeners
 */
public class HttpConnectionHandler implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(HttpConnectionHandler.class);

	private final ServerSocket serverSocket;
	private final ServerConfiguration settings;

	public HttpConnectionHandler(ServerSocket serverSocket, ServerConfiguration settings) {
		this.serverSocket = serverSocket;
		this.settings = settings;
	}

	@Override
	public void run() {
		try {
			while (!this.settings.getStatus().equals(ServerStatus.STOPPED)) {
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
