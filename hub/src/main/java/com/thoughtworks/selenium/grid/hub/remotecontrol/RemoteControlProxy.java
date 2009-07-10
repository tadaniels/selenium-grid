package com.thoughtworks.selenium.grid.hub.remotecontrol;

import com.thoughtworks.selenium.grid.HttpClient;
import com.thoughtworks.selenium.grid.HttpParameters;
import com.thoughtworks.selenium.grid.Response;
import com.thoughtworks.selenium.grid.hub.HubServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * Local interface to a real remote control running somewhere in the grid.
 */
public class RemoteControlProxy {

    private final static Log LOGGER = LogFactory.getLog(HubServer.class);

    private final int concurrentSessionMax;
    private int concurrentSessionCount;
    private final HttpClient httpClient;
    private final String environment;
    private final String host;
    private final int port;


    public RemoteControlProxy(String host, int port, String environment, int concurrentSessionMax, HttpClient httpClient) {
        if (null == host) {
            throw new IllegalArgumentException("host cannot be null");
        }
        if (null == environment) {
            throw new IllegalArgumentException("environment cannot be null");
        }
        this.host = host;
        this.port = port;
        this.environment = environment;
        this.concurrentSessionMax = concurrentSessionMax;
        this.concurrentSessionCount = 0;
        this.httpClient = httpClient;
    }

    public String host() {
        return host;
    }

    public int port() {
        return port;
    }

    public String environment() {
        return environment;
    }

    public String remoteControlPingURL() {
        return remoteControlURLFor("/core/Blank.html");
    }

    public String remoteControlDriverURL() {
        return remoteControlURLFor("/selenium-server/driver/");
    }

    public String remoteControlURLFor(String path) {
        return "http://" + host + ":" + port + path;
    }

    public Response forward(HttpParameters parameters) throws IOException {
        return httpClient.post(remoteControlDriverURL(), parameters);
    }

    public String toString() {
        return "[RemoteControlProxy " + host + ":" + port + " "
                                      + concurrentSessionCount  + "/" + concurrentSessionMax + "]";
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        final RemoteControlProxy otherRemoteControl = (RemoteControlProxy) other;
        return host.equals(otherRemoteControl.host)
                && port == otherRemoteControl.port;
    }

    public int hashCode() {
        return (host + port).hashCode();
    }

    public int concurrentSessionsMax() {
        return concurrentSessionMax;
    }

    public int concurrentSesssionCount() {
        return concurrentSessionCount;
    }

    public void registerNewSession() {
        if (concurrentSessionCount == concurrentSessionMax) {
            throw new IllegalStateException("Exceeded concurrent session max for " + toString());
        }
        concurrentSessionCount += 1;
    }

    public void unregisterSession() {
        if (0 == concurrentSessionCount) {
            throw new IllegalStateException("Unregistering session on an idle remote control : " + toString());
        }
        concurrentSessionCount -= 1;
    }

    public boolean canHandleNewSession() {
        return concurrentSessionCount < concurrentSessionMax;
    }

	public boolean unreliable() {
        final Response response;

        try {
            response = httpClient.get(remoteControlPingURL());
        } catch (Exception e) {
            LOGGER.info("Remote Control at " + host + ":" + port + " is unresponsive");
            return true;
        }

        if (response.statusCode() != 200) {
            LOGGER.info("Remote Control at " + host + ":" + port + " did not respond correctly");
            return true;
        }
        return false;
    }

}
