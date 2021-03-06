package com.thoughtworks.selenium.grid.remotecontrol;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * Registration Request to Selenium Grid Hub.
 */
public class RegistrationRequest extends HubRequest {

    private static final Log LOGGER = LogFactory.getLog(RegistrationRequest.class);

    public RegistrationRequest(RegistrationInfo registrationInfo) {
      super(registrationInfo.hubURL() + "/registration-manager/register",
            registrationInfo.host(),
            registrationInfo.port(),
            registrationInfo.environment());
    }


    public int execute() throws IOException {
        final int status;

        LOGGER.info("Registering to " + targetURL());
        status = super.execute();
        if (200 != status) {
            throw new IllegalStateException("Could not register successfuly to " + targetURL()
                    + " with environment '" + environment()
                    + "'. Most likely this environment is not defined on the hub.");
        }
        return status;
    }

    
}
