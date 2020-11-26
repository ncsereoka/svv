package upt.cti.svv.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import upt.cti.svv.server.exception.InternalServerErrorException;

import java.io.IOException;
import java.net.ServerSocket;

public class HttpWebServer {
	private static final Logger log = LoggerFactory.getLogger(HttpWebServer.class);

	private ServerSocket serverSocket;
	private final ServerConfiguration settings;
	private Thread thread;

	public HttpWebServer(ServerConfiguration settings) {
		this.settings = settings;
	}

	public ServerConfiguration getSettings() {
		return settings;
	}

	public void stop() {
		if (serverSocket != null) {
			try {
				log.info("Stopping server...");
				serverSocket.close();
			} catch (IOException e) {
				log.info("Error stopping server");
			}
		}

		if (thread != null) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				log.info("Error stopping server");
			}
		}
	}

	public void start() {
		bindToPort(this.settings.getPort());
		thread = new Thread(new HttpConnectionHandler(serverSocket, settings));
		thread.start();
	}

	public void restart() {
		stop();
		start();
	}

	private void bindToPort(int port) {
		try {
			this.serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			throw new InternalServerErrorException("Could not listen on port " + port);
		}
		log.info("Server listening on port {}...", port);
	}

}
