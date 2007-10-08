package com.thoughtworks.selenium.grid.hub.remotecontrol;

import java.io.IOException;

/**
 * Local interface to a real remote control running somewhere in the grid.
 */
public class RemoteControlProxy {

    private final HttpClient httpClient;
    private final String environment;
    private final String host;
    private final int port;

    public RemoteControlProxy(String host, int port, String environment, HttpClient httpClient) {
        if (null == host) {
            throw new IllegalArgumentException("host cannot be null");
        }
        if (null == environment) {
            throw new IllegalArgumentException("environment cannot be null");
        }
        this.host = host;
        this.port = port;
        this.environment = environment;
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

    public String remoteControlURL() {
        return "http://" + host + ":" + port + "/selenium-server/driver/";
    }

    public String commandURL(String queryString) {
        return remoteControlURL() + "?" + queryString;
    }

    public Response forward(String queryString) throws IOException {
        return httpClient.get(commandURL(queryString));
    }

    public String toString() {
        return "[RemoteControlProxy " + host + ":" + port + "]";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final RemoteControlProxy otherRemoteControl = (RemoteControlProxy) o;
        return host.equals(otherRemoteControl.host)
                && port == otherRemoteControl.port;
    }

    public int hashCode() {
        return (host + port).hashCode();
    }

}