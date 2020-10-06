package upt.cti.svv.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import upt.cti.svv.server.exception.InternalServerErrorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HttpConnection implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(HttpConnection.class);

	private final Socket clientSocket;

	public HttpConnection(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		log.info("New client connection");

		try {
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			HttpRequest request = HttpRequest.from(in);

			out.println(request);
			out.close();
			in.close();
			clientSocket.close();
		} catch (IOException e) {
			throw new InternalServerErrorException("Error during server connection");
		}
	}
}
