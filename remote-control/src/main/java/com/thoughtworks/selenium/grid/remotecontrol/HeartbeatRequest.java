package com.thoughtworks.selenium.grid.remotecontrol;

import com.thoughtworks.selenium.grid.HttpClient;
import com.thoughtworks.selenium.grid.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Heartbeat Request to Selenium Grid Hub.
 */
public class HeartbeatRequest {

    private static final Log LOGGER = LogFactory.getLog(HeartbeatRequest.class);
    private final String heartBeatURL;

    public HeartbeatRequest(RegistrationInfo registrationInfo) {
        heartBeatURL = registrationInfo.hubURL() + "/heartbeat";
    }

    public String heartBeatURL() {
        return heartBeatURL;
    }

    public boolean execute() {
        final Response response;

        try {
            LOGGER.info("Ping Hub at " + heartBeatURL);
            response = httpClient().get(heartBeatURL);
        } catch (Exception e) {
            LOGGER.warn("Hub at " + heartBeatURL + " is unresponsive");
            return false;
        }
        if (response.statusCode() != 200) {
            LOGGER.warn("Hub at " + heartBeatURL + " did not respond correctly");
            return false;
        }

        return true;
    }

    protected HttpClient httpClient() {
        return new HttpClient();
    }


}