package com.thoughtworks.selenium.grid.remotecontrol;

import static junit.framework.Assert.assertEquals;
import org.jbehave.classmock.UsingClassMock;
import org.junit.Test;

import java.io.IOException;

/**
 * {@link RegistrationRequest} unit test class.
 */
public class RegistrationRequestTest extends UsingClassMock {

    @Test
    public void postToHubUsingTheURLProvidedInConstructor() throws IOException {
        final HubRequest request;
        final String uri;

        request = new RegistrationRequest("http://thehub.url:4444", "", "", "");
        uri = request.postMethod().getURI().toString();
        assertEquals("http://thehub.url:4444/remote_controls", uri);
    }

    @Test
    public void postToHubSubmittingTheHost() throws IOException {
        final HubRequest request;
        request = new RegistrationRequest("http://thehub.url:4444",
                                          "the.host.com",
                                          "123",
                                          "*safari");
        assertEquals("the.host.com", request.postMethod().getParameter("host").getValue());
    }

    @Test
    public void postToHubSubmittingThePort() throws IOException {
        final HubRequest request;
        request = new RegistrationRequest("http://thehub.url:4444",
                                          "the.host.com",
                                          "123",
                                          "*safari");
        assertEquals("the.host.com", request.postMethod().getParameter("host").getValue());
    }

    @Test
    public void postToHubSubmittingTheEnvironment() throws IOException {
        final HubRequest request;
        request = new RegistrationRequest("http://thehub.url:4444",
                                          "the.host.com",
                                          "123",
                                          "*safari");
        assertEquals("*safari", request.postMethod().getParameter("environments[]").getValue());
    }

}