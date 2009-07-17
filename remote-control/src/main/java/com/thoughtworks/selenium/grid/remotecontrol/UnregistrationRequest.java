package com.thoughtworks.selenium.grid.remotecontrol;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/*
 * Registration Request to Selenium Grid Hub.
 */
public class UnregistrationRequest extends HubRequest {

    private static final Log logger = LogFactory.getLog(RegistrationRequest.class);

    public UnregistrationRequest(RegistrationInfo registrationInfo) {
        super(registrationInfo.hubURL() + "/registration-manager/unregister",
              registrationInfo.host(),
              registrationInfo.port(),
              registrationInfo.environment());
    }


    public int execute() throws IOException {
        logger.info("Unregistering from " + targetURL());
        return super.execute();
    }

}