package com.thoughtworks.selenium.grid.hub.remotecontrol;

import com.thoughtworks.selenium.grid.HttpClient;
import com.thoughtworks.selenium.grid.SocketUtils;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.jbehave.classmock.UsingClassMock;
import org.junit.Test;


public class RemoteControlProxyFunctionalTest extends UsingClassMock {

    @Test
    public void pingingAnRcMakesAGetRequestAndEnsuresA200ResponseCode() throws Exception {
        int port = SocketUtils.getFreePort();
        DummyWebServer dummyWebServer = new DummyWebServer(port);
        dummyWebServer.start();
        RemoteControlProxy rc = new RemoteControlProxy("localhost", port, "environment", 1, new HttpClient());

        rc.unreliable();
        rc.unreliable();
        rc.unreliable();

        dummyWebServer.stop();

        assertEquals(3, dummyWebServer.getRequests().size());
        assertTrue(dummyWebServer.getRequests().get(0).trim().startsWith("GET /core/Blank.html HTTP/1.1"));
        assertTrue(dummyWebServer.getRequests().get(1).trim().startsWith("GET /core/Blank.html HTTP/1.1"));
        assertTrue(dummyWebServer.getRequests().get(2).trim().startsWith("GET /core/Blank.html HTTP/1.1"));
    }

    @Test
    public void ifTheRCGivesANon200ResponseCodeThePingThrowsAnIOException() throws Exception {
        int port = SocketUtils.getFreePort();
        DummyWebServer dummyWebServer = new DummyWebServer(port);
        dummyWebServer.shouldGive500 = true;
        String host = "localhost";
        RemoteControlProxy rc;

        try {
            dummyWebServer.start();
            rc = new RemoteControlProxy(host, port, "environment", 1, new HttpClient());
            assertTrue(rc.unreliable());
        } finally {
            dummyWebServer.stop();
        }
    }

    @Test
    public void ifTheRCIsntThereThePingThrowsAnIOException() throws Exception {
        int port = SocketUtils.getFreePort();
        String host = "localhost";
        RemoteControlProxy rc = new RemoteControlProxy(host, port, "environment", 1, new HttpClient());
        assertTrue(rc.unreliable());
    }
}
