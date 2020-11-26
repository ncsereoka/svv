package upt.cti.svv.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import upt.cti.svv.server.exception.InternalServerErrorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnection implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(HttpConnection.class);

	private final Socket clientSocket;
	private final HttpRequestHandler requestHandler;

	public HttpConnection(Socket clientSocket, HttpRequestHandler requestHandler) {
		this.clientSocket = clientSocket;
		this.requestHandler = requestHandler;

	}

	@Override
	public void run() {
		try {
			final OutputStream out = clientSocket.getOutputStream();
			final BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			final HttpRequest request = HttpRequestParser.from(in);
			log.info("{} '{}'", request.getMethod(), request.getUrl());

			out.write(requestHandler.handle(request));
			out.flush();

			out.close();
			in.close();
			clientSocket.close();
		} catch (IOException e) {
			throw new InternalServerErrorException("Error during server connection");
		}
	}
}
