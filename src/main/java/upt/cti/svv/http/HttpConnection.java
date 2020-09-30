package upt.cti.svv.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HttpConnection implements Runnable {
	private final Socket clientSocket;

	public HttpConnection(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		System.out.println("New Communication Thread Started");

		try {
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				System.out.println("Server: " + inputLine);
				out.println(inputLine);

				if (inputLine.trim().equals(""))
					break;
			}

			out.close();
			in.close();
			clientSocket.close();
		} catch (IOException e) {
			System.err.println("Problem with Communication Server");
			System.exit(1);
		}
	}
}
