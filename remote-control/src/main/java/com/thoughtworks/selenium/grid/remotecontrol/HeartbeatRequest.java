package com.thoughtworks.selenium.grid.remotecontrol;

import com.thoughtworks.selenium.grid.HttpClient;
import com.thoughtworks.selenium.grid.HttpParameters;
import com.thoughtworks.selenium.grid.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Heartbeat Request to Selenium Grid Hub.
 */
public class HeartbeatRequest {

    private static final Log LOGGER = LogFactory.getLog(HeartbeatRequest.class);
    public static enum Status { DOWN, UNREGISTERED, OK }
    private final String heartBeatURL;

    public HeartbeatRequest(RegistrationInfo registrationInfo) {
        heartBeatURL = registrationInfo.hubURL() + "/heartbeat?host=" + registrationInfo.host()
                                                 + "&port=" + registrationInfo.port();
    }

    public String heartBeatURL() {
        return heartBeatURL;
    }

    public Status execute() {
        final Response response;

        try {
            LOGGER.info("Ping Hub at " + heartBeatURL);
            response = httpClient().get(heartBeatURL);
        } catch (Exception e) {
            LOGGER.warn("Hub at " + heartBeatURL + " is unresponsive");
            return Status.DOWN;
        }
        if (response.statusCode() != 200) {
            LOGGER.warn("Hub at " + heartBeatURL + " did not respond correctly");
            return Status.DOWN;
        }
        if (!response.body().equals("Hub : OK")) {
            LOGGER.warn("Hub at " + heartBeatURL + " does not have us as registered");
            return Status.UNREGISTERED;
        }

        return Status.OK;
    }

    protected HttpClient httpClient() {
        return new HttpClient();
    }


}