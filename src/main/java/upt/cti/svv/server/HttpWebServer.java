package upt.cti.svv.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import upt.cti.svv.server.exception.InternalServerErrorException;

import java.io.IOException;
import java.net.ServerSocket;

public class HttpWebServer {
	private static final Logger log = LoggerFactory.getLogger(HttpWebServer.class);

	private ServerSocket serverSocket;
	private final ServerConfiguration config;
	private Thread thread;

	public HttpWebServer(ServerConfiguration config) {
		this.config = config;
	}

	public ServerConfiguration getConfig() {
		return config;
	}

	public void stop() {
		closeServerSocket();
		joinThread();
	}

	public void start() {
		serverSocket = bindToPort(this.getConfig().getPort());
		log.info("Server listening on port {}...", getConfig().getPort());
		thread = new Thread(new HttpConnectionHandler(serverSocket, this.getConfig()));
		thread.start();
	}

	public void restart() {
		stop();
		start();
	}

	ServerSocket bindToPort(int port) {
		try {
			return new ServerSocket(port);
		} catch (IOException e) {
			throw new InternalServerErrorException("Could not listen on port " + port);
		}
	}

	private void closeServerSocket() {
		if (serverSocket != null) {
			try {
				log.info("Stopping server...");
				serverSocket.close();
			} catch (IOException e) {
				log.info("Error stopping server");
			}
		}
	}

	private void joinThread() {
		if (thread != null) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				log.info("Error stopping server");
			}
		}
	}

}
