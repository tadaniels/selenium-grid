package com.thoughtworks.selenium.grid.hub.remotecontrol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class DummyWebServer {
	private final List<String> requestsReceived = new ArrayList<String>();

	boolean shouldGive500;
	
	private Socket socket;
	private ServerSocket serverSocket;

	public DummyWebServer(int port) throws Exception {
		serverSocket = new ServerSocket(port);
	}

	public void start() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						socket = serverSocket.accept();
						new WebRequestProcessor(socket).start();
					} catch (Exception e) {
						if (!e.getMessage().toLowerCase().equals("socket closed")
								&& !e.getMessage().toLowerCase().equals("socket is closed")) {
							throw new RuntimeException(e);
						}
					}
				}
			}
		}).start();
	}

	public void stop() {
		try {
			serverSocket.close();
		} catch (IOException e) {
		}
	}

	public List<String> getRequests() {
		return requestsReceived;
	}

	private class WebRequestProcessor extends Thread {
		private final Socket socket;

		public WebRequestProcessor(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			InputStream inputStream = null;
			OutputStream outputStream = null;
			InputStream contentStream = null;
			try {
				outputStream = socket.getOutputStream();
				inputStream = socket.getInputStream();

				StringBuffer requestBuffer = new StringBuffer();

				BufferedReader clientReader = new BufferedReader(new InputStreamReader(inputStream));
				String line;
				while ((line = clientReader.readLine()) != null) {
					requestBuffer.append(line);
					if (line.trim().length() == 0) {
						break;
					}
				}

				requestsReceived.add(requestBuffer.toString());

				PrintWriter out = new PrintWriter(outputStream);
				if (shouldGive500) {
					contentStream = getClass().getResourceAsStream("DummyWebServerBadContent.txt");
				} else {
					contentStream = getClass().getResourceAsStream("DummyWebServerGoodContent.txt");
				}
				BufferedReader contentReader = new BufferedReader(new InputStreamReader(
						contentStream));
				while ((line = contentReader.readLine()) != null) {
					out.println(line);
				}
				out.flush();
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				try {
					contentStream.close();
					outputStream.close();
					inputStream.close();
					socket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
