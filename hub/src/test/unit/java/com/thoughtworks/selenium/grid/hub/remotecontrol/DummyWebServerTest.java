package com.thoughtworks.selenium.grid.hub.remotecontrol;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import org.junit.Test;

import com.thoughtworks.selenium.grid.SocketUtils;

public class DummyWebServerTest {
	@Test
	public void makeSureTheTestWebServerWorks() throws Exception {
		int port = SocketUtils.getFreePort();
		DummyWebServer testWebServer = new DummyWebServer(port);
		testWebServer.start();

		Socket socket = new Socket("localhost", port);

		OutputStream outputStream = socket.getOutputStream();
		InputStream inputStream = socket.getInputStream();

		PrintWriter out = new PrintWriter(outputStream);
		out.println("GET /");
		out.println();
		out.flush();

		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

		String line;
		StringBuffer buffer = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		outputStream.close();
		inputStream.close();
		socket.close();

		testWebServer.stop();

		assertEquals(6988, buffer.toString().length());
	}
}
