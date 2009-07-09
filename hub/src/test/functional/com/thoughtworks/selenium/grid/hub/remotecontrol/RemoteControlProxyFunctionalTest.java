package com.thoughtworks.selenium.grid.hub.remotecontrol;

import com.thoughtworks.selenium.grid.HttpClient;
import com.thoughtworks.selenium.grid.SocketUtils;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.jbehave.classmock.UsingClassMock;
import org.junit.Test;

import java.io.IOException;


public class RemoteControlProxyFunctionalTest extends UsingClassMock {

    @Test
    public void pingingAnRcMakesAGetRequestAndEnsuresA200ResponseCode() throws Exception {
    	int port = SocketUtils.getFreePort();
    	DummyWebServer dummyWebServer = new DummyWebServer(port);
    	dummyWebServer.start();
    	RemoteControlProxy rc = new RemoteControlProxy("localhost", port, "environment", 1, new HttpClient());

    	rc.ping();
    	rc.ping();
    	rc.ping();

    	dummyWebServer.stop();

    	assertEquals(3, dummyWebServer.getRequests().size());
    	assertTrue(dummyWebServer.getRequests().get(0).trim().startsWith("GET /selenium-server/ HTTP/1.1"));
    	assertTrue(dummyWebServer.getRequests().get(1).trim().startsWith("GET /selenium-server/ HTTP/1.1"));
    	assertTrue(dummyWebServer.getRequests().get(2).trim().startsWith("GET /selenium-server/ HTTP/1.1"));
    }

    @Test
    public void ifTheRCGivesANon200ResponseCodeThePingThrowsAnIOException() throws Exception {
    	int port = SocketUtils.getFreePort();
    	DummyWebServer dummyWebServer = new DummyWebServer(port);
    	dummyWebServer.shouldGive500 = true;
    	dummyWebServer.start();
    	String host = "localhost";
		RemoteControlProxy rc = new RemoteControlProxy(host, port, "environment", 1, new HttpClient());

    	try {
			rc.ping();
			fail("Should have thrown an IOException");
		} catch (IOException e) {
			assertEquals("Remote Control at " + host + ":" + port + " did not respond correctly", e.getMessage());
		} finally {
			dummyWebServer.stop();
		}
    }

    @Test
    public void ifTheRCIsntThereThePingThrowsAnIOException() throws Exception {
    	int port = SocketUtils.getFreePort();
    	String host = "localhost";
		RemoteControlProxy rc = new RemoteControlProxy(host, port, "environment", 1, new HttpClient());

    	try {
			rc.ping();
			fail("Should have thrown an IOException");
		} catch (IOException e) {
			assertEquals("Remote Control at " + host + ":" + port + " is unresponsive", e.getMessage());
		}
    }
}
