package com.thoughtworks.selenium.grid.remotecontrol;

import static junit.framework.Assert.assertEquals;
import org.jbehave.classmock.UsingClassMock;
import org.junit.Test;

import java.io.IOException;

/**
 * {@link com.thoughtworks.selenium.grid.remotecontrol.UnregistrationRequest} unit test class.
 */
public class UnregistrationRequestTest extends UsingClassMock {

    @Test
    public void postToHubUsingTheRemoteControlUID() throws IOException {
        final HubRequest request;
        final String uri;

        request = new UnregistrationRequest("http://thehub.url:4444", "", "");
        uri = request.postMethod().getURI().toString();
        assertEquals("http://thehub.url:4444/remote_controls/05a79f06cf3f67f726dae68d18a2290f6c9a50c9", uri);
    }

    @Test
    public void postToHubSubmittingTheHost() throws IOException {
        final HubRequest request;
        request = new UnregistrationRequest("http://thehub.url:4444",
                                            "the.host.com",
                                            "123");
        assertEquals("the.host.com", request.postMethod().getParameter("host").getValue());
    }

    @Test
    public void postToHubSubmittingThePort() throws IOException {
        final HubRequest request;
        request = new UnregistrationRequest("http://thehub.url:4444",
                                            "the.host.com",
                                            "123");
        assertEquals("the.host.com", request.postMethod().getParameter("host").getValue());
    }

    @Test
    public void postToHubSubmittingTheDeleteMethod() throws IOException {
        final HubRequest request;
        request = new UnregistrationRequest("http://thehub.url:4444",
                                            "the.host.com",
                                            "123");
        assertEquals("delete", request.postMethod().getParameter("_method").getValue());
    }

}