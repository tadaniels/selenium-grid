package com.thoughtworks.selenium.grid.remotecontrol;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

/**
 * Selenium Remote Control Request to Grid Hub.
 *
 * @author Philippe Hanrigou
 */
public abstract class HubRequest {

    private final String seleniumHubURL;
    private final String host;
    private final String port;

    public HubRequest(String seleniumHubURL, String host, String port) {
        this.seleniumHubURL = seleniumHubURL;
        this.host = host;
        this.port = port;
    }

    public int execute() throws IOException {
        return new HttpClient().executeMethod(postMethod());
    }

    public abstract PostMethod postMethod();

    public String seleniumHubURL() {
        return seleniumHubURL;
    }

    public String host() {
        return host;
    }

    public String port() {
        return port;
    }
}
