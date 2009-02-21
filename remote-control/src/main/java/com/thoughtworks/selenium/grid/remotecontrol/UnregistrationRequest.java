package com.thoughtworks.selenium.grid.remotecontrol;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/*
 * Registration Request to Selenium Grid Hub.
 */
public class UnregistrationRequest extends HubRequest {

    private static final Log logger = LogFactory.getLog(RegistrationRequest.class);

    public UnregistrationRequest(String seleniumHubURL, String host, String port) {
        super(seleniumHubURL, host, port);
    }

    public PostMethod postMethod() {
        final PostMethod postMethod = new PostMethod(targetURL());
        postMethod.addParameter("host", host());
        postMethod.addParameter("port", port());
        postMethod.addParameter("_method", "delete");

        return postMethod;
    }

    private String targetURL() {
        final String uid;

        uid = DigestUtils.shaHex(host() + ":" + port());
        return seleniumHubURL() + "/remote_controls/" + uid;
    }

    public int execute() throws IOException {
        logger.info("Unregistering from " + targetURL());
        return super.execute();
    }

}