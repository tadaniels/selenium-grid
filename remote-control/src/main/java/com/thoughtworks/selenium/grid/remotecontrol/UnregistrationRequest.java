package com.thoughtworks.selenium.grid.remotecontrol;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/*
 * Registration Request to Selenium Grid Hub.
 *
 * @author Philippe Hanrigou
 */
public class UnregistrationRequest extends HubRequest {

    private static final Log logger = LogFactory.getLog(RegistrationRequest.class);

    public UnregistrationRequest(String seleniumHubURL, String host, String port, String environment) {
        super(seleniumHubURL, host, port, environment);
    }

    public PostMethod postMethod() {
        final String uid;

        uid = DigestUtils.shaHex(host + ":" + port);
        final PostMethod postMethod = new PostMethod(targetURL() + "/remote_controls/" + uid);
        postMethod.addParameter("host", host);
        postMethod.addParameter("port", port);
        postMethod.addParameter("environment", environment());
        postMethod.addParameter("_method", "delete");

        return postMethod;
    }

    public int execute() throws IOException {
        logger.info("Unregistering from " + targetURL());
        return super.execute();
    }

}