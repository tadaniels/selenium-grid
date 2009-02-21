package com.thoughtworks.selenium.grid.remotecontrol;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * Registration Request to Selenium Grid Hub.
 *
 * @author Philippe Hanrigou
 */
public class RegistrationRequest extends HubRequest {

    private static final Log LOGGER = LogFactory.getLog(RegistrationRequest.class);
    private final String environment;

    public RegistrationRequest(String seleniumHubURL, String host, String port, String environment) {
      super(seleniumHubURL, host, port);
        this.environment =  environment;        
    }


    public int execute() throws IOException {
        final int status;

        LOGGER.info("Registering to " + targetURL());
        status = super.execute();
        if (302 != status) {
            throw new IllegalStateException("Could not register successfuly to " + targetURL()
                    + " with environment '" + environment
                    + "'. Most likely this environment is not defined on the hub.");
        }
        return status;
    }


    public PostMethod postMethod() {
        final PostMethod postMethod = new PostMethod(targetURL());
        postMethod.addParameter("host", host());
        postMethod.addParameter("port", port());
        postMethod.addParameter("environments[]", environment);

        return postMethod;
    }

    private String targetURL() {
        return seleniumHubURL()+ "/remote_controls";
    }

}
