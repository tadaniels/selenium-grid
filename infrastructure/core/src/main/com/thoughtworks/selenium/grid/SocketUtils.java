package com.thoughtworks.selenium.grid;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketUtils {
	public static int getFreePort() throws IOException {
		ServerSocket socket = new ServerSocket(0);
		int port = socket.getLocalPort();
		socket.close();
		return port;
	}
}
