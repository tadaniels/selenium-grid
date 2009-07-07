package com.thoughtworks.selenium.grid.hub.remotecontrol;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.thoughtworks.selenium.grid.HttpClient;
import com.thoughtworks.selenium.grid.HttpParameters;
import com.thoughtworks.selenium.grid.Response;

/**
 * TODO
 * 
 * @author: Philippe Hanrigou
 */
public class SeleniumRCProxyIntegrationTest {

	private static final int VALID_PORT = 5555;

	@Test
	public void forwardGetNewBrowserSessionToRemoteControl() throws IOException {
		HttpClient client = new HttpClient();
		RemoteControlProxy proxy = new RemoteControlProxy("localhost", VALID_PORT, "env", 1, client);
		HttpParameters parameters = new HttpParameters();
		parameters.put("cmd", "getNewBrowserSession");
		parameters.put("1", "*firefox");
		parameters.put("2", "http://www.google.com");
		Response response = proxy.forward(parameters);
		assertEquals(200, response.statusCode());
		assertTrue(response.body().startsWith("OK,"));
	}

}